package pl.marczynski.pwr.si.ttp;

import pl.marczynski.pwr.si.ttp.genetic.GeneticAlgorithm;

public class Main {

    public static void main(String[] args) {
        GeneticAlgorithm ga = GeneticAlgorithm.initialize("hard_0", 1000, 1000, 0.5, 0.01, 10);
        ga.run();
        ga.saveToFile();
    }
}
