package pl.marczynski.pwr.si.ttp;

import org.junit.Test;
import pl.marczynski.pwr.si.ttp.genetic.HiperparametersExploration;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileRepository;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileType;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;
import pl.marczynski.pwr.si.ttp.utils.ResultSaverService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void shouldGenerateCorrectNumberOfFiles() throws IOException {
        //given
        ProblemDescription trivial = DescriptionFileRepository.getRandomDescriptionForType(DescriptionFileType.TRIVIAL);
        File directory = ResultSaverService.getDirectory(trivial.getFileName());
        Files.walk(directory.toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        directory = ResultSaverService.getDirectory(trivial.getFileName());
        int numberOfFiles = directory.listFiles().length;
        assertEquals(0, numberOfFiles);

        //when
        new HiperparametersExploration(trivial).explore();

        //then
        assertEquals(60, directory.listFiles().length);
    }

}
