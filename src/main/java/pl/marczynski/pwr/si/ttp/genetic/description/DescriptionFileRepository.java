package pl.marczynski.pwr.si.ttp.genetic.description;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DescriptionFileRepository {
    private static HashMap<DescriptionFileType, Integer> numberOfFiles = new HashMap<DescriptionFileType, Integer>() {{
        put(DescriptionFileType.TRIVIAL, 2);
        put(DescriptionFileType.EASY, 5);
        put(DescriptionFileType.MEDIUM, 5);
        put(DescriptionFileType.HARD, 5);
    }};

    public static ProblemDescription getRandomDescriptionForType(DescriptionFileType type) {
        Integer numberOfFiles = DescriptionFileRepository.numberOfFiles.get(type);
        return ProblemDescription.getDescriptionFromFile(type.getNamePrefix() + new Random().nextInt(numberOfFiles));
    }

    public static Set<ProblemDescription> getAllHardDescriptions() {
        Set<ProblemDescription> result = new HashSet<>();
        DescriptionFileType hard = DescriptionFileType.HARD;
        for (Integer i = 0; i < numberOfFiles.get(hard); i++) {
            result.add(ProblemDescription.getDescriptionFromFile(hard.getNamePrefix() + i));
        }
        return result;
    }
}
