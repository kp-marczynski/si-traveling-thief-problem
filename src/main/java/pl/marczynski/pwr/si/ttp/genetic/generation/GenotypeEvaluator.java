package pl.marczynski.pwr.si.ttp.genetic.generation;

import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;
import pl.marczynski.pwr.si.ttp.genetic.generation.genotype.City;
import pl.marczynski.pwr.si.ttp.genetic.generation.genotype.Genotype;
import pl.marczynski.pwr.si.ttp.genetic.generation.genotype.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GenotypeEvaluator {

    private final Map<City, Map<City, Double>> distancesBetweenCities;
    private final int knapsackCapacity;
    private final double minSpeed;
    private final double maxSpeed;

    public GenotypeEvaluator(ProblemDescription problemDescription) {
        this.distancesBetweenCities = problemDescription.getDistancesBetweenCities();
        this.knapsackCapacity = problemDescription.getKnapsackCapacity();
        this.minSpeed = problemDescription.getMinSpeed();
        this.maxSpeed = problemDescription.getMaxSpeed();
    }


    public double evaluate(Genotype genotype) {
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
