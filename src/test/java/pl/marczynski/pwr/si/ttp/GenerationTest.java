package pl.marczynski.pwr.si.ttp;

import org.junit.Test;
import pl.marczynski.pwr.si.ttp.parser.TtpFileParser;

import static org.junit.Assert.*;

public class GenerationTest {

    private final ProblemDescription problemDescription = TtpFileParser.parseFile("trivial_0.ttp");

    @Test
    public void shouldSortGenotypesInGeneration() {
        //given
        Generation firstGeneration = Generation.createFirstGeneration(10, 0, 0, 0, problemDescription);
        GenotypeEvaluator evaluator = firstGeneration.getEvaluator();
        //when
        firstGeneration.sortPopulationDescending();

        //then
        for (int i = 0; i < firstGeneration.population.size() - 1; i++) {
            assertTrue(evaluator.evaluate(firstGeneration.population.get(i)) >= evaluator.evaluate(firstGeneration.population.get(i + 1)));
        }
    }

}
