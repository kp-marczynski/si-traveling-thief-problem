package pl.marczynski.pwr.si.ttp.genetic;

import javafx.util.Pair;
import pl.marczynski.pwr.si.ttp.ResultSaverService;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;
import pl.marczynski.pwr.si.ttp.genetic.generation.GenerationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        int bestEdenSize = testExclusivity();

        Hiperparameters bestHiperparameters = new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, bestPopulationSize, bestCrossProbability, bestMutationProbability, bestTournamentSize, bestEdenSize);
        testEdges(bestHiperparameters);
    }

    private int testSelection() {
        Integer[] tournamentSize = {0, 5, 10, 25};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (int tournament : tournamentSize) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, tournament, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList, "selection", Arrays.stream(tournamentSize).map(String::valueOf).collect(Collectors.toList())).getTournamentSize();
    }


    private double testCrossing() {
        Double[] crossProbability = {0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (double cross : crossProbability) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, cross, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList, "crossing", Arrays.stream(crossProbability).map(String::valueOf).collect(Collectors.toList())).getCrossProbability();
    }

    private double testMutation() {
        Double[] mutationProbability = {0.01, 0.05, 0.1, 0.15, 0.2};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (double mutation : mutationProbability) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, mutation, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList, "mutation", Arrays.stream(mutationProbability).map(String::valueOf).collect(Collectors.toList())).getMutationProbability();
    }

    private int testPopulationSize() {
        Integer[] populationSize = {100, 500, 1000};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (int population : populationSize) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, population, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE));
        }
        return performTest(hiperparametersList, "population", Arrays.stream(populationSize).map(String::valueOf).collect(Collectors.toList())).getPopulationsSize();
    }

    private int testExclusivity() {
        Integer[] exclusivity = {0, 1, 10, 20};
        List<Hiperparameters> hiperparametersList = new ArrayList<>();
        for (int edenSize : exclusivity) {
            hiperparametersList.add(new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, edenSize));
        }
        return performTest(hiperparametersList, "exclusivity", Arrays.stream(exclusivity).map(String::valueOf).collect(Collectors.toList())).getEdenSize();
    }

    private void testEdges(Hiperparameters bestHiperparameters) {
        testEdgeMutationForBestCross(bestHiperparameters.getCrossProbability());
        testEdgeCrossForBestMutation(bestHiperparameters.getMutationProbability());
        testMinimalTournament();
        testMaximalTournament();
        testBigGeneration();
    }


    private void testEdgeMutationForBestCross(double bestCrossProbability) {
        double edgeMutation = 0.5;
        Hiperparameters hiperparameters = new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, bestCrossProbability, edgeMutation, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE);
        performStandardTest(hiperparameters, "edge-mutation_" + edgeMutation + "-for-cross_" + bestCrossProbability);
    }

    private void testEdgeCrossForBestMutation(double bestMutationProbability) {
        double edgeCross = 0.25;
        Hiperparameters hiperparameters = new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, edgeCross, bestMutationProbability, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE);
        performStandardTest(hiperparameters, "edge-cross_" + edgeCross + "-for-mutation_" + bestMutationProbability);
    }

    private void testMinimalTournament() {
        int minimalTournament = 1;
        Hiperparameters hiperparameters = new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, minimalTournament, DEFAULT_EDEN_SIZE);
        performStandardTest(hiperparameters, "edge-minimal-tournament_" + minimalTournament);
    }

    private void testMaximalTournament() {
        int maximalTournament = DEFAULT_POPULATION_SIZE;
        Hiperparameters hiperparameters = new Hiperparameters(DEFAULT_NUMBER_OF_GENERATIONS, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, maximalTournament, DEFAULT_EDEN_SIZE);
        performStandardTest(hiperparameters, "edge-maximal-tournament_" + maximalTournament);
    }

    private void testBigGeneration() {
        int bigGeneration = 10000;
        Hiperparameters hiperparameters = new Hiperparameters(bigGeneration, DEFAULT_POPULATION_SIZE, DEFAULT_CROSS_PROBABILITY, DEFAULT_MUTATION_PROBABILITY, DEFAULT_TOURNAMENT_SIZE, DEFAULT_EDEN_SIZE);
        performStandardTest(hiperparameters, "edge-big-generation_" + bigGeneration);
    }

    private void performStandardTest(Hiperparameters hiperparameters, String testName) {
        GeneticAlgorithm ga = GeneticAlgorithm.initialize(problemDescription, hiperparameters);
        List<GenerationResult> results = ga.run();
        List<List<String>> mappedResults = results.stream().map(GenerationResult::getResultAsStringList).collect(Collectors.toList());
        ResultSaverService.saveToFile(problemDescription.getFileName(), testName, GenerationResult.getCsvHeader(), mappedResults);
    }

    private Hiperparameters performTest(List<Hiperparameters> hiperparametersList, String testName, List<String> header) {
        List<Pair<Double, Hiperparameters>> results = new ArrayList<>();
        List<List<GenerationResult>> generationsResults = new ArrayList<>();
        for (Hiperparameters hiperparameters : hiperparametersList) {
            GeneticAlgorithm ga = GeneticAlgorithm.initialize(problemDescription, hiperparameters);
            List<GenerationResult> currentResult = ga.run();
            results.add(new Pair<>(currentResult.get(currentResult.size() - 1).getBest(), hiperparameters));
            generationsResults.add(currentResult);
        }
        saveToCsv(testName, header, generationsResults);
        return findBestHiperparameters(results);
    }

    private void saveToCsv(String testName, List<String> header, List<List<GenerationResult>> generationsResults) {
        List<String> newHeader = new ArrayList<>();
        newHeader.add("Generation number");
        newHeader.addAll(header);

        List<Pair<Function<GenerationResult, Double>, String>> separateResults = Arrays.asList(new Pair<>(GenerationResult::getBest, "best"),
                new Pair<>(GenerationResult::getAverage, "average"),
                new Pair<>(GenerationResult::getWorst, "worst"));
        for (Pair<Function<GenerationResult, Double>, String> separateResult : separateResults) {
            List<List<String>> csvResult = new ArrayList<>();
            int min = generationsResults.stream().mapToInt(List::size).min().getAsInt();
            int max = generationsResults.stream().mapToInt(List::size).max().getAsInt();
            if (min != max) {
                throw new IllegalStateException("lists differ in size");
            }
            List<List<String>> generationSeparatedForCsv = new ArrayList<>();
            for (List<GenerationResult> generationsResult : generationsResults) {
                generationSeparatedForCsv.add(generationsResult.stream().map(separateResult.getKey()).map(String::valueOf).collect(Collectors.toList()));
            }
            for (int i = 0; i < min; i++) {
                List<String> partialList = new ArrayList<>();
                partialList.add(String.valueOf(i));
                for (List<String> strings : generationSeparatedForCsv) {
                    partialList.add(strings.get(i));
                }
                csvResult.add(partialList);
            }
//            for (List<GenerationResult> gaResult : generationsResults) {
//                csvResult.add(gaResult.stream().map(separateResult.getKey()).map(String::valueOf).collect(Collectors.toList()));
//            }
            ResultSaverService.saveToFile(problemDescription.getFileName(), testName + "_" + separateResult.getValue(), newHeader, csvResult);
        }
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
