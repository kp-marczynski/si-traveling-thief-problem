package pl.marczynski.pwr.si.ttp.genetic;

import javafx.util.Pair;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;

import java.util.ArrayList;
import java.util.List;

public class HiperparametersExploration {

    private static int DEFAULT_NUMBER_OF_GENERATIONS = 100;
    private static int DEFAULT_POPULATION_SIZE = 100;
    private static double DEFAULT_CROSS_PROBABILITY = 0.7;
    private static double DEFAULT_MUTATION_PROBABILITY = 0.01;
    private static int DEFAULT_TOURNAMENT_SIZE = 5;
    private static int DEFAULT_EDEN_SIZE = 0;

    private final ProblemDescription problemDescription;

    public HiperparametersExploration(ProblemDescription problemDescription) {
        this.problemDescription = problemDescription;
    }

    public void explore() {
        int bestTournamentSize = testSelection();
        double bestCrossProbability = testCrossing();
        double bestMutationProbability = testMutation();
        int bestPopulationSize = testPopulationSize();
        int bestNumberOfGenerations = testNumberOfGenerations();
        int bestEdenSize = testExclusivity();

        Hiperparameters bestHiperparameters = new Hiperparameters(bestNumberOfGenerations, bestPopulationSize, bestCrossProbability, bestMutationProbability, bestTournamentSize, bestEdenSize);
        testEdges(bestHiperparameters);
    }

    private int testSelection() {
        int[] tournamentSize = {0, 5, 10, 25};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (int tournament : tournamentSize) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, tournament, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList).getTournamentSize();
    }


    private double testCrossing() {
        double[] crossProbability = {0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (double cross : crossProbability) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, cross, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList).getCrossProbability();
    }

    private double testMutation() {
        double[] mutationProbability = {0.01, 0.05, 0.1, 0.15, 0.2};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (double mutation : mutationProbability) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, mutation, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList).getMutationProbability();
    }

    private int testPopulationSize() {
        int[] populationSize = {100, 500, 1000};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (int population : populationSize) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, population, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList).getPopulationsSize();
    }

    private int testNumberOfGenerations() {
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        int[] numberOfGenerations = {100, 500, 1000};
        for (int generation : numberOfGenerations) {
            hiperparametersList.add(new Hiperparameters(generation, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList).getNumberOfGenerations();
    }

    private int testExclusivity() {
        int[] exclusivity = {0, 1, 10, 20};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (int edenSize : exclusivity) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, edenSize));
        }
        return performTest(hiperparametersList).getEdenSize();
    }

    private void testEdges(Hiperparameters bestHiperparameters) {
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, bestHiperparameters.getCrossProbability(), 0.5, bestHiperparameters.getTournamentSize(), bestHiperparameters.getEdenSize()));
        hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, 0.25, bestHiperparameters.getMutationProbability(), bestHiperparameters.getTournamentSize(), bestHiperparameters.getEdenSize()));
        hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, bestHiperparameters.getCrossProbability(), bestHiperparameters.getMutationProbability(), 1, bestHiperparameters.getEdenSize()));
        hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, bestHiperparameters.getCrossProbability(), bestHiperparameters.getMutationProbability(), DEFAULT_POPULATION_SIZE, bestHiperparameters.getEdenSize()));
        performTest(hiperparametersList);
    }


    private Hiperparameters performTest(List<Hiperparameters> hiperparametersList) {
        List<Pair<Double, Hiperparameters>> results = new ArrayList<>();
        for (Hiperparameters hiperparameters : hiperparametersList) {
            GeneticAlgorithm ga = GeneticAlgorithm.initialize(problemDescription, hiperparameters);
            double result = ga.run();
            results.add(new Pair<>(result, hiperparameters));
        }
        return findBestHiperparameters(results);
    }

    private static Hiperparameters findBestHiperparameters(List<Pair<Double, Hiperparameters>> results) {
        Hiperparameters bestHiperparameters = null;
        Double bestResult = null;
        for (Pair<Double, Hiperparameters> result : results) {
            if (bestResult == null || result.getKey() > bestResult) {
                bestResult = result.getKey();
                bestHiperparameters = result.getValue();
            }
        }
        return bestHiperparameters;
    }
}
