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

    private final Hiperparameters hiperparameters;
    private final ProblemDescription problemDescription;
    private final List<Generation> generations;

    private GeneticAlgorithm(ProblemDescription problemDescription, Hiperparameters hiperparameters) {
        this.hiperparameters = hiperparameters;
        this.problemDescription = problemDescription;
        this.generations = new ArrayList<>();
    }

    public static GeneticAlgorithm initialize(ProblemDescription problemDescription, Hiperparameters hiperparameters) {
        return new GeneticAlgorithm(problemDescription, hiperparameters);
    }

    public double run() {
        Generation firstGeneration = Generation.createFirstGeneration(problemDescription, hiperparameters);
        generations.add(firstGeneration);
        for (int i = 0; i < hiperparameters.getNumberOfGenerations() - 1; i++) {
            Generation nextGeneration = Generation.createNextGeneration(generations.get(i));
            generations.add(nextGeneration);
            System.out.println(generations.get(i));
        }
        System.out.println(generations.get(generations.size() - 1));
        saveToFile();
        return generations.get(generations.size() - 1).getBestResult();
    }

    public void saveToFile() {
        String baseName = new StringBuilder()
                .append(problemDescription.getFileName())
                .append("-genSize_").append(hiperparameters.getNumberOfGenerations())
                .append("-popSize_").append(hiperparameters.getPopulationsSize())
                .append("-px_").append(hiperparameters.getCrossProbability())
                .append("-pm_").append(hiperparameters.getMutationProbability())
                .append("-tour_").append(hiperparameters.getTournamentSize())
                .toString();

        String resultPath = RESULTS_PATH + problemDescription.getFileName() + "/" + baseName;
        File directory = new File(resultPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        int numberOfFilesInDirectory = directory.listFiles().length;

        String fileName = new StringBuilder()
                .append(resultPath).append("/")
                .append(baseName)
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
