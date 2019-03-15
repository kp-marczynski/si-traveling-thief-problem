package pl.marczynski.pwr.si.ttp;

import pl.marczynski.pwr.si.ttp.genetic.GeneticAlgorithm;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        GeneticAlgorithm ga = GeneticAlgorithm.initialize("easy_0", 100, 100, 0.5, 0.01, 10);
        ga.run();
        ga.saveToFile();
    }
}
