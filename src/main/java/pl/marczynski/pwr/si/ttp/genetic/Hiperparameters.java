package pl.marczynski.pwr.si.ttp.genetic;

public class Hiperparameters {
    private final int numberOfGenerations;
    private final int populationsSize;
    private final double crossProbability;
    private final double mutationProbability;
    private final int tournamentSize;
    private final int edenSize;

    public Hiperparameters(int numberOfGenerations, int populationsSize, double crossProbability, double mutationProbability, int tournamentSize, int edenSize) {
        this.numberOfGenerations = numberOfGenerations;
        this.populationsSize = populationsSize;
        this.crossProbability = crossProbability;
        this.mutationProbability = mutationProbability;
        this.tournamentSize = tournamentSize;
        this.edenSize = edenSize;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public int getPopulationsSize() {
        return populationsSize;
    }

    public double getCrossProbability() {
        return crossProbability;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public int getEdenSize() {
        return edenSize;
    }

    @Override
    public String toString() {
        return "Hiperparameters{" +
                "numberOfGenerations=" + numberOfGenerations +
                ", populationsSize=" + populationsSize +
                ", crossProbability=" + crossProbability +
                ", mutationProbability=" + mutationProbability +
                ", tournamentSize=" + tournamentSize +
                ", edenSize=" + edenSize +
                '}';
    }
}
