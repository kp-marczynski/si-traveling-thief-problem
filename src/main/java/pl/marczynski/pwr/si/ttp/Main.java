package pl.marczynski.pwr.si.ttp;

import pl.marczynski.pwr.si.ttp.genetic.GeneticAlgorithm;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileRepository;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileType;

public class Main {

    public static void main(String[] args) {
        String randomFile = DescriptionFileRepository.getRandomFileForType(DescriptionFileType.EASY);
        GeneticAlgorithm ga = GeneticAlgorithm.initialize(randomFile, 100, 100, 0.5, 0.01, 10);
        ga.run();
        ga.saveToFile();
    }
}
