package pl.marczynski.pwr.si;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Genotype {
    private final List<City> cities;

    private Genotype(List<City> cities) {
        this.cities = new ArrayList<>(cities);
    }

    public static Genotype createShuffledGenotype(List<City> cities) {
        Genotype result = new Genotype(cities);
        Collections.shuffle(result.cities);
        return result;
    }

    public static Genotype createMuttated(Genotype genotype) {
        Genotype result = new Genotype(genotype.getCities());
        int first = (int) (new Random().nextFloat() * result.getSize() - 1);
        int second = (int) (new Random().nextFloat() * result.getSize() - 1);
        Collections.swap(result.cities, first, second);
        return result;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Genotype{cities=");
        for (City city : cities) {
            builder.append(city.getCityIndex()).append(";");
        }
        builder.append("}");
        return builder.toString();
    }
}
