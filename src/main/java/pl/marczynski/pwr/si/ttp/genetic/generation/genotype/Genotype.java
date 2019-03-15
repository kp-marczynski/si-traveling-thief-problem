package pl.marczynski.pwr.si.ttp.genetic.generation.genotype;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Genotype {
    private final List<City> cities;
    private Double value = null;
    private final static Random random = new Random();

    private static final HashMap<Integer, Genotype> cachedGenotypes = new HashMap<>();

    private Genotype(List<City> cities) {
        this.cities = new ArrayList<>(cities);
    }

    private static Genotype getGenotype(List<City> cities) {
        Genotype result = null;
        if (cachedGenotypes.containsKey(cities.hashCode())) {
            Genotype found = cachedGenotypes.get(cities.hashCode());
            if (found.cities.equals(cities)) {
                result = found;
            }
        }
        if (result == null) {
            Genotype genotype = new Genotype(cities);
            cachedGenotypes.put(cities.hashCode(), genotype);
            result = genotype;
        }
        return result;
    }

    private static String citiesAsString(List<City> cities) {
        StringBuilder builder = new StringBuilder();
        builder.append("Genotype{cities=");
        for (City city : cities) {
            builder.append(city.getCityIndex()).append(";");
        }
        builder.append("}");
        return builder.toString();
    }

    public static Genotype createShuffledGenotype(List<City> cities) {
        List<City> result = new ArrayList<>(cities);
        Collections.shuffle(result);
        return getGenotype(result);
    }

    public static Genotype createMutated(Genotype genotype) {
        List<City> result = new ArrayList<>(genotype.cities);
        int firstIndex = random.nextInt(result.size());
        int secondIndex = random.nextInt(result.size());
        if (firstIndex == secondIndex) {
            secondIndex = ++secondIndex % (result.size() - 1);
        }
        Collections.swap(result, firstIndex, secondIndex);
        return getGenotype(result);
    }

    public static Pair<Genotype, Genotype> createCrossed(Genotype firstParent, Genotype secondParent) {
        if (firstParent.getSize() != secondParent.getSize()) {
            throw new IllegalStateException("Crossed parents don't have equals numbers of genes");
        }
        int genotypeSize = firstParent.getSize();
        int index = 1 + random.nextInt((genotypeSize - 3));
        List<City> firstChildCities = Stream.concat(
                Stream.concat(
                        firstParent.cities.subList(0, index).stream(),
                        secondParent.cities.subList(index + 1, genotypeSize - 1).stream()
                ).distinct(),
                firstParent.cities.stream()
        ).distinct().collect(Collectors.toList());

        List<City> secondChildCities = Stream.concat(
                Stream.concat(
                        secondParent.cities.subList(0, index).stream(),
                        firstParent.cities.subList(index + 1, genotypeSize - 1).stream()
                ).distinct(),
                secondParent.cities.stream()
        ).distinct().collect(Collectors.toList());

        Genotype firstChild = new Genotype(firstChildCities);
        Genotype secondChild = new Genotype(secondChildCities);

        return new Pair<>(firstChild, secondChild);
    }

    protected List<City> getCities() {
        return cities;
    }

    public City getCity(int index) {
        if (index < 0 || index >= cities.size()) {
            throw new IndexOutOfBoundsException("Requested index is out of cities list bounds");
        } else {
            return cities.get(index);
        }
    }

    public int getSize() {
        return cities.size();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = Double.valueOf(value);
    }

    public boolean hasCalculatedValue() {
        return value != null;
    }


    @Override
    public String toString() {
        return citiesAsString(cities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return cities.equals(genotype.cities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cities);
    }
}
