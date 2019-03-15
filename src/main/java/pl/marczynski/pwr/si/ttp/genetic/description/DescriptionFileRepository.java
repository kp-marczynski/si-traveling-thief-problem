package pl.marczynski.pwr.si.ttp.genetic.description;

import java.util.HashMap;
import java.util.Random;

public class DescriptionFileRepository {
    private static HashMap<DescriptionFileType, Integer> numberOfFiles = new HashMap<DescriptionFileType, Integer>() {{
        put(DescriptionFileType.TRIVIAL, 2);
        put(DescriptionFileType.EASY, 5);
        put(DescriptionFileType.MEDIUM, 5);
        put(DescriptionFileType.HARD, 5);
    }};

    public static String getRandomFileForType(DescriptionFileType type) {
        Integer numberOfFiles = DescriptionFileRepository.numberOfFiles.get(type);
        return type.getNamePrefix() + new Random().nextInt(numberOfFiles);
    }
}
