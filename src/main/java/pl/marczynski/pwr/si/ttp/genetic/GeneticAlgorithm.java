package pl.marczynski.pwr.si.ttp.genetic;

import pl.marczynski.pwr.si.ttp.ResultSaverService;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;
import pl.marczynski.pwr.si.ttp.genetic.generation.Generation;
import pl.marczynski.pwr.si.ttp.genetic.generation.GenerationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneticAlgorithm {

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

    public List<GenerationResult> run() {
        Generation firstGeneration = Generation.createFirstGeneration(problemDescription, hiperparameters);
        generations.add(firstGeneration);
        for (int i = 0; i < hiperparameters.getNumberOfGenerations() - 1; i++) {
            Generation nextGeneration = Generation.createNextGeneration(generations.get(i));
            generations.add(nextGeneration);
            System.out.println(generations.get(i));
        }
        System.out.println(generations.get(generations.size() - 1));
//        saveToFile();
        return generations.stream().map(Generation::getGenerationResult).collect(Collectors.toList());
    }

    private void saveToFile() {
        List<List<String>> results = new ArrayList<>();
        for (Generation generation : generations) {
            List<String> result = new ArrayList<>();
            result.add(String.valueOf(generation.getGenerationResult().getOrderNumber()));
            result.add(String.valueOf(generation.getGenerationResult().getBest()));
            result.add(String.valueOf(generation.getGenerationResult().getAverage()));
            result.add(String.valueOf(generation.getGenerationResult().getWorst()));
            results.add(result);
        }
        ResultSaverService.saveToFile(problemDescription.getFileName(), getBaseName(), GenerationResult.getCsvHeader(), results);
    }

    private String getBaseName() {
        return new StringBuilder()
                .append(problemDescription.getFileName())
                .append("-genSize_").append(hiperparameters.getNumberOfGenerations())
                .append("-popSize_").append(hiperparameters.getPopulationsSize())
                .append("-px_").append(hiperparameters.getCrossProbability())
                .append("-pm_").append(hiperparameters.getMutationProbability())
                .append("-tour_").append(hiperparameters.getTournamentSize())
                .toString();
    }


}
