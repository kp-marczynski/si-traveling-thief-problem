package pl.marczynski.pwr.si.ttp;

import org.junit.Test;
import pl.marczynski.pwr.si.ttp.genetic.HiperparametersExploration;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileRepository;
import pl.marczynski.pwr.si.ttp.genetic.description.DescriptionFileType;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void generateTrivial() {
        //given
        ProblemDescription trivial = DescriptionFileRepository.getRandomDescriptionForType(DescriptionFileType.TRIVIAL);
        File directory = ResultSaverService.getDirectory(trivial.getFileName());
        int numberOfFiles = directory.listFiles().length;

        //when
        new HiperparametersExploration(trivial).explore();

        //then
        assertEquals(numberOfFiles + 30, directory.listFiles().length);
    }

}
