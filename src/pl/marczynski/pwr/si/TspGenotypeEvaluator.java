package pl.marczynski.pwr.si;

import java.util.Map;

public class TspGenotypeEvaluator {

    private final Map<City, Map<City, Double>> distancesBetweenCities;

    public TspGenotypeEvaluator(Map<City, Map<City, Double>> distancesBetweenCities) {
        this.distancesBetweenCities = distancesBetweenCities;
    }


    public double evaluate(TspGenotype genotype) {
        double result = 0;
        for (int i = 0; i < genotype.cities.size() - 1; i++) {
            result += distancesBetweenCities.get(genotype.cities.get(i)).get(genotype.cities.get(i + 1));
        }
        result += distancesBetweenCities.get(genotype.cities.get(genotype.cities.size() - 1)).get(genotype.cities.get(0));
        return result;
    }
}
