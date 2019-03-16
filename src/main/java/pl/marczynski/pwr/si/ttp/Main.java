package pl.marczynski.pwr.si.ttp;

import pl.marczynski.pwr.si.ttp.genetic.HiperparametersExploration;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileRepository;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileType;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;

import java.util.Set;

public class Main {


    public static void main(String[] args) {
        Set<ProblemDescription> descriptions = DescriptionFileRepository.getAllHardDescriptions();
        for (ProblemDescription description : descriptions) {
            new HiperparametersExploration(description).explore();
        }
//        ProblemDescription trivial = DescriptionFileRepository.getRandomDescriptionForType(DescriptionFileType.TRIVIAL);
//        new HiperparametersExploration(trivial).explore();
    }

}
