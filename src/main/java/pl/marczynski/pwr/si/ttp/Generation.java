package pl.marczynski.pwr.si.ttp;

import javafx.util.Pair;

import java.util.*;

public class Generation {
    private static Random random = new Random();
    private int orderNumber;
    private int populationSize;
    private double crossProbability;
    private double mutationProbability;
    private int tournamentSize;

    private final GenotypeEvaluator evaluator;
    protected List<Genotype> population;

    public Generation(int orderNumber, int populationSize, double crossProbability, double mutationProbability, int tournamentSize, GenotypeEvaluator evaluator) {
        this.populationSize = populationSize;
        this.crossProbability = crossProbability;
        this.mutationProbability = mutationProbability;
        this.tournamentSize = tournamentSize;
        this.population = new ArrayList<>();
        this.evaluator = evaluator;
        this.orderNumber = orderNumber;
    }

    public static Generation createFirstGeneration(int populationSize, double crossProbability, double mutationProbability, int tournamentSize, ProblemDescription ttp) {
        GenotypeEvaluator evaluator = new GenotypeEvaluator(ttp);
        Generation firstGeneration = new Generation(1, populationSize, crossProbability, mutationProbability, tournamentSize, evaluator);
        firstGeneration.initializePopulation(ttp.getCities());
        firstGeneration.ageGeneration();
        return firstGeneration;
    }

    public static Generation createNextGeneration(Generation parentGeneration) {
        Generation nextGeneration = new Generation(parentGeneration.orderNumber + 1, parentGeneration.populationSize, parentGeneration.crossProbability, parentGeneration.mutationProbability, parentGeneration.tournamentSize, parentGeneration.evaluator);
        nextGeneration.population.addAll(parentGeneration.population);
        nextGeneration.ageGeneration();
        return nextGeneration;
    }

    private void initializePopulation(List<City> cities) {
        for (int i = 0; i < populationSize; i++) {
            population.add(Genotype.createShuffledGenotype(cities));
        }
    }

    private void ageGeneration() {
        performSelection();
        performCrossover();
        performMutation();
//        sortPopulationDescending();
    }

    private void performSelection() {
        if (tournamentSize < 0) {
            throw new IllegalStateException("Tournament size can not be less than 0");
        }
        if (tournamentSize == populationSize) {
            sortPopulationDescending();
            population = population.subList(0, populationSize);
        } else if (tournamentSize == 0) {
            Collections.shuffle(population);
            population = population.subList(0, populationSize);
        } else {
            List<Genotype> source = new ArrayList<>(population);
            List<Genotype> newPopulation = new ArrayList<>();

            while (newPopulation.size() < populationSize) {
                int selectedIndex = random.nextInt(source.size());
                Genotype selected = source.get(selectedIndex);
                for (int i = 0; i < tournamentSize - 1; ++i) {
                    int index = random.nextInt(source.size());
                    if (evaluator.evaluate(source.get(index)) > evaluator.evaluate(selected)) {
                        selected = source.get(index);
                        selectedIndex = index;
                    }
                }
                newPopulation.add(selected);
                source.remove(selectedIndex);
            }
            population = newPopulation;
        }
    }

    private void performCrossover() {
        List<Pair<Genotype, Genotype>> pairsForCrossover = createPairsForCrossover();
        for (Pair<Genotype, Genotype> pair : pairsForCrossover) {
            if (random.nextDouble() <= crossProbability) {
                Pair<Genotype, Genotype> crossed = Genotype.createCrossed(pair.getKey(), pair.getValue());
                population.add(crossed.getKey());
                population.add(crossed.getValue());
            }
        }
    }

    private List<Pair<Genotype, Genotype>> createPairsForCrossover() {
        List<Genotype> source = new ArrayList<>(population);
        Collections.shuffle(source);
        List<Pair<Genotype, Genotype>> result = new ArrayList<>();
        for (int i = 0; i < source.size() / 2; i++) {
            result.add(new Pair<>(source.get(2 * i), source.get(2 * i + 1)));
        }
        return result;
    }

    private void performMutation() {
        List<Genotype> mutated = new ArrayList<>();
        for (Genotype genotype : population) {
            if (random.nextDouble() <= mutationProbability) {
                mutated.add(Genotype.createMutated(genotype));
            }
        }
        population.addAll(mutated);
    }

    protected void sortPopulationDescending() {
        population.sort(getGenotypeComparator().reversed());
    }

    private Comparator<Genotype> getGenotypeComparator() {
        return (a, b) -> (int) (evaluator.evaluate(a) - evaluator.evaluate(b));
    }

    private double getBestResult() {
        return population.stream().mapToDouble(evaluator::evaluate).max().getAsDouble();
    }

    private double getAverageResult() {
        return population.stream().mapToDouble(evaluator::evaluate).average().getAsDouble();
    }

    private double getWorstResult() {
        return population.stream().mapToDouble(evaluator::evaluate).min().getAsDouble();
    }

    @Override
    public String toString() {
        return "Generation{" +
                "orderNumber= " + orderNumber +
                " best= " + getBestResult() +
                " avg= " + getAverageResult() +
                " worst= " + getWorstResult() +
                '}';
    }

    public GenotypeEvaluator getEvaluator() {
        return evaluator;
    }
}