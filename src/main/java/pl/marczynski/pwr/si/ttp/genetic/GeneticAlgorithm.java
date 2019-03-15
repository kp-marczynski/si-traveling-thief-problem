package pl.marczynski.pwr.si.ttp.genetic;

import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;
import pl.marczynski.pwr.si.ttp.genetic.generation.Generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private static final String RESULTS_PATH = "./src/main/resources/results/";

    private final int numberOfGenerations;
    private final int populationsSize;
    private final double crossProbability;
    private final double mutationProbability;
    private final int tournamentSize;
    private final ProblemDescription problemDescription;
    private final List<Generation> generations;

    private GeneticAlgorithm(int numberOfGenerations, int populationsSize, double crossProbability, double mutationProbability, int tournamentSize, ProblemDescription problemDescription) {
        this.numberOfGenerations = numberOfGenerations;
        this.populationsSize = populationsSize;
        this.crossProbability = crossProbability;
        this.mutationProbability = mutationProbability;
        this.tournamentSize = tournamentSize;
        this.problemDescription = problemDescription;
        this.generations = new ArrayList<>();
    }

    public static GeneticAlgorithm initialize(String sourceFileName, int numberOfGenerations, int populationsSize, double crossProbability, double mutationProbability, int tournamentSize) {
        ProblemDescription ttp = ProblemDescription.getDescriptionFromFile(sourceFileName);
        return new GeneticAlgorithm(numberOfGenerations, populationsSize, crossProbability, mutationProbability, tournamentSize, ttp);
    }

    public void run() {
        Generation firstGeneration = Generation.createFirstGeneration(populationsSize, crossProbability, mutationProbability, tournamentSize, problemDescription);
        generations.add(firstGeneration);
        for (int i = 0; i < numberOfGenerations - 1; i++) {
            Generation nextGeneration = Generation.createNextGeneration(generations.get(i));
            generations.add(nextGeneration);
            System.out.println(generations.get(i));
        }
        System.out.println(generations.get(generations.size() - 1));
    }

    public void saveToFile() {
        String resultPath = RESULTS_PATH + problemDescription.getFileName();
        File directory = new File(resultPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        int numberOfFilesInDirectory = directory.listFiles().length;

        String fileName = new StringBuilder().append(resultPath)
                .append("/").append(problemDescription.getFileName())
                .append("-genSize_").append(numberOfGenerations)
                .append("-popSize_").append(populationsSize)
                .append("-px_").append(crossProbability)
                .append("-pm_").append(mutationProbability)
                .append("-tour_").append(tournamentSize)
                .append("-v_").append(numberOfFilesInDirectory).append(".csv").toString();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(Generation.getCsvHeader());
            for (Generation generation : generations) {
                bufferedWriter.newLine();
                bufferedWriter.write(generation.getInCsvFormat());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
