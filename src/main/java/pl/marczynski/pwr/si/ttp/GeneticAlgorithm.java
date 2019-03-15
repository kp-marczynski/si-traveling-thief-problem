package pl.marczynski.pwr.si.ttp;

import pl.marczynski.pwr.si.ttp.parser.TtpFileParser;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private int numberOfGenerations;
    private int populationsSize;
    private double crossProbability;
    private double mutationProbability;
    private int tournamentSize;
    private ProblemDescription problemDescription;
    private List<Generation> generations;

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
        ProblemDescription ttp = TtpFileParser.parseFile(sourceFileName);
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
}
