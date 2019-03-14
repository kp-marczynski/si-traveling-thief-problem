package pl.marczynski.pwr.si.ttp;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Genotype {
    private final List<City> cities;
    private Double value = null;

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
        Random random = new Random();
        int first = (int) (random.nextFloat() * result.size() - 1);
        int second = (int) (random.nextFloat() * result.size() - 1);
        if (first == second) {
            second = ++second % (result.size() - 1);
        }
        Collections.swap(result, first, second);
        return getGenotype(result);
    }

    public static Pair<Genotype, Genotype> createCrossed(Genotype firstParent, Genotype secondParent) {
        if (firstParent.getSize() != secondParent.getSize()) {
            throw new IllegalStateException("Crossed parents don't have equals numbers of genes");
        }
        int genotypeSize = firstParent.getSize();
        int index = 1 + (int) (new Random().nextFloat() * (genotypeSize - 3));
        List<City> firstChildCities = Stream.concat(
                Stream.concat(
                        firstParent.getCities().subList(0, index).stream(),
                        secondParent.getCities().subList(index + 1, genotypeSize - 1).stream()
                ).distinct(),
                firstParent.getCities().stream()
        ).distinct().collect(Collectors.toList());

        List<City> secondChildCities = Stream.concat(
                Stream.concat(
                        secondParent.getCities().subList(0, index).stream(),
                        firstParent.getCities().subList(index + 1, genotypeSize - 1).stream()
                ).distinct(),
                secondParent.getCities().stream()
        ).distinct().collect(Collectors.toList());

//        LinkedHashSet<City> firstChildCitiesSet = new LinkedHashSet<>(firstParent.getCities().subList(0, index));
//        firstChildCitiesSet.addAll(secondParent.getCities().subList(index + 1, genotypeSize - 1));
//        firstChildCitiesSet.addAll(firstParent.getCities());
//
//        LinkedHashSet<City> secondChildCitiesSet = new LinkedHashSet<>(secondParent.getCities().subList(0, index));
//        secondChildCitiesSet.addAll(firstParent.getCities().subList(index + 1, genotypeSize - 1));
//        secondChildCitiesSet.addAll(secondParent.getCities());

        Genotype firstChild = new Genotype(firstChildCities);
        Genotype secondChild = new Genotype(secondChildCities);

        return new Pair<>(firstChild, secondChild);
    }

    public List<City> getCities() {
        return cities;
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
