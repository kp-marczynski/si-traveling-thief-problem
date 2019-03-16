package pl.marczynski.pwr.si.ttp.genetic.generation;

import javafx.util.Pair;
import pl.marczynski.pwr.si.ttp.genetic.Hiperparameters;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;
import pl.marczynski.pwr.si.ttp.genetic.generation.genotype.City;
import pl.marczynski.pwr.si.ttp.genetic.generation.genotype.Genotype;

import java.util.*;

public class Generation {
    private final static Random random = new Random();
    private final Hiperparameters hiperparameters;
    private final GenotypeEvaluator evaluator;
    private final List<Genotype> eden;
    private final int orderNumber;
    private GenerationResult generationResult = null;

    protected final List<Genotype> population;

    public Generation(int orderNumber, Hiperparameters hiperparameters, GenotypeEvaluator evaluator, List<Genotype> eden) {
        this.orderNumber = orderNumber;
        this.hiperparameters = hiperparameters;
        this.population = new ArrayList<>();
        this.evaluator = evaluator;
        this.eden = (eden == null) ? new ArrayList<>() : new ArrayList<>(eden);
    }

    public static Generation createFirstGeneration(ProblemDescription ttp, Hiperparameters hiperparameters) {
        GenotypeEvaluator evaluator = new GenotypeEvaluator(ttp);
        Generation firstGeneration = new Generation(1, hiperparameters, evaluator, null);
        firstGeneration.initializePopulation(ttp.getCities());
        firstGeneration.ageGeneration();
        return firstGeneration;
    }

    public static Generation createNextGeneration(Generation parentGeneration) {
        Generation nextGeneration = new Generation(parentGeneration.orderNumber + 1, parentGeneration.hiperparameters, parentGeneration.evaluator, parentGeneration.eden);
        nextGeneration.population.addAll(parentGeneration.population);
        nextGeneration.ageGeneration();
        return nextGeneration;
    }

    private void initializePopulation(List<City> cities) {
        for (int i = 0; i < hiperparameters.getPopulationsSize(); i++) {
            Genotype shuffledGenotype = Genotype.createShuffledGenotype(cities);
            population.add(shuffledGenotype);
            updateEden(shuffledGenotype);
        }
    }

    private void updateEden(Genotype candidate) {
        if (hiperparameters.getEdenSize() > 0) {
            eden.add(candidate);
            if (eden.size() > hiperparameters.getEdenSize()) {
                eden.sort(getGenotypeComparator().reversed());
                eden.remove(eden.size() - 1);
            }
        }
    }

    private void ageGeneration() {
        performSelection();
        performCrossover();
        performMutation();
        generationResult = new GenerationResult(orderNumber, getBestResult(), getAverageResult(), getWorstResult());
    }

    private void performSelection() {
        if (hiperparameters.getTournamentSize() < 0) {
            throw new IllegalStateException("Tournament size can not be less than 0");
        } else {
            List<Genotype> newPopulation = new ArrayList<>(eden);
            List<Genotype> source = new ArrayList<>(population);
            if (hiperparameters.getTournamentSize() == 0) {
                source.removeAll(eden);
            }
            while (newPopulation.size() < hiperparameters.getPopulationsSize()) {
                int index = random.nextInt(source.size());
                Genotype selected = source.get(index);
                for (int i = 0; i < hiperparameters.getTournamentSize() - 1; ++i) {
                    index = random.nextInt(source.size());
                    Genotype tournamentGenotype = source.get(index);
                    if (evaluator.evaluate(tournamentGenotype) > evaluator.evaluate(selected)) {
                        selected = tournamentGenotype;
                    }
                }
                newPopulation.add(selected);
                if (hiperparameters.getTournamentSize() == 0) {
                    source.remove(selected);
                }
            }
            population.clear();
            population.addAll(newPopulation);
        }
    }

    private void performCrossover() {
        List<Pair<Genotype, Genotype>> pairsForCrossover = createPairsForCrossover();
        for (Pair<Genotype, Genotype> pair : pairsForCrossover) {
            if (random.nextDouble() <= hiperparameters.getCrossProbability()) {
                Pair<Genotype, Genotype> crossed = Genotype.createCrossed(pair.getKey(), pair.getValue());
                population.add(crossed.getKey());
                population.add(crossed.getValue());

                updateEden(crossed.getKey());
                updateEden(crossed.getValue());
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
            if (random.nextDouble() <= hiperparameters.getMutationProbability()) {
                Genotype mutatedGenotype = Genotype.createMutated(genotype);
                mutated.add(mutatedGenotype);
                updateEden(mutatedGenotype);
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
                " best= " + generationResult.getBest() +
                " avg= " + generationResult.getAverage() +
                " worst= " + generationResult.getWorst() +
                '}';
    }

    public GenotypeEvaluator getEvaluator() {
        return evaluator;
    }

    public GenerationResult getGenerationResult() {
        return generationResult;
    }
}
