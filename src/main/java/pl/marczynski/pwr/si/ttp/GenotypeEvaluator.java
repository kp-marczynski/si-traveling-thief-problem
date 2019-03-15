package pl.marczynski.pwr.si.ttp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GenotypeEvaluator {

    private final Map<City, Map<City, Double>> distancesBetweenCities;
    private final int knapsackCapacity;
    private final double minSpeed;
    private final double maxSpeed;
    private final Map<String, Double> cachedResults;

    public GenotypeEvaluator(ProblemDescription problemDescription) {
        this.distancesBetweenCities = problemDescription.getDistancesBetweenCities();
        this.knapsackCapacity = problemDescription.getKnapsackCapacity();
        this.minSpeed = problemDescription.getMinSpeed();
        this.maxSpeed = problemDescription.getMaxSpeed();
        this.cachedResults = new HashMap<>();
    }


    public double evaluate(Genotype genotype) {
//        String genotypeString = genotype.toString();
//        if (cachedResults.containsKey(genotypeString)) {
//            return cachedResults.get(genotypeString);
//        }
        if (genotype.hasCalculatedValue()) {
            return genotype.getValue();
        }
        double roadTime = 0;
        double currentWeight = 0;
        double currentValue = 0;
        for (int i = 0; i < genotype.getSize() - 1; i++) {
            Optional<Item> item = genotype.getCity(i).selectItem(knapsackCapacity - currentWeight);
            if (item.isPresent()) {
                currentValue += item.get().getProfit();
                currentWeight += item.get().getWeight();
            }
            roadTime += calculateSpeed(currentWeight) * distancesBetweenCities.get(genotype.getCity(i)).get(genotype.getCity(i + 1));
        }
        roadTime += distancesBetweenCities.get(genotype.getCity(genotype.getSize() - 1)).get(genotype.getCity(0));
        double result = currentValue - roadTime;
        genotype.setValue(result);
        return result;
    }

    private double calculateSpeed(double currentWeight) {
        return maxSpeed - currentWeight * (maxSpeed - minSpeed) / knapsackCapacity;
    }
}
