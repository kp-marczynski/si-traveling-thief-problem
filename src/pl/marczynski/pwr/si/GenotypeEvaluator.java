package pl.marczynski.pwr.si;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GenotypeEvaluator {

    private final Map<City, Map<City, Double>> distancesBetweenCities;
    private final int knapsackCapacity;
    private final double minSpeed;
    private final double maxSpeed;
    private final Map<String, Double> cachedResults;

    public GenotypeEvaluator(TravelingThiefProblem ttp) {
        this.distancesBetweenCities = ttp.getDistancesBetweenCities();
        this.knapsackCapacity = ttp.getKnapsackCapacity();
        this.minSpeed = ttp.getMinSpeed();
        this.maxSpeed = ttp.getMaxSpeed();
        this.cachedResults = new HashMap<>();
    }


    public double evaluate(Genotype genotype) {
        String genotypeString = genotype.toString();
        if (cachedResults.containsKey(genotypeString)) {
            return cachedResults.get(genotypeString);
        }
        double roadTime = 0;
        double currentWeight = 0;
        double currentValue = 0;
        for (int i = 0; i < genotype.getSize() - 1; i++) {
            Optional<Item> item = genotype.getCities().get(i).selectItem(knapsackCapacity - currentWeight);
            if (item.isPresent()) {
                currentValue += item.get().getProfit();
                currentWeight += item.get().getWeight();
            }
            roadTime += calculateSpeed(currentWeight) * distancesBetweenCities.get(genotype.getCities().get(i)).get(genotype.getCities().get(i + 1));
        }
        roadTime += distancesBetweenCities.get(genotype.getCities().get(genotype.getSize() - 1)).get(genotype.getCities().get(0));
        double result = currentValue - roadTime;
        cachedResults.put(genotypeString, result);
        return result;
    }

    private double calculateSpeed(double currentWeight) {
        return maxSpeed - currentWeight * (maxSpeed - minSpeed) / knapsackCapacity;
    }
}
