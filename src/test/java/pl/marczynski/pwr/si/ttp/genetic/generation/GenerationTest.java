package pl.marczynski.pwr.si.ttp.genetic.generation;

import org.junit.Test;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;

import static org.junit.Assert.assertTrue;

public class GenerationTest {

    private final ProblemDescription problemDescription = ProblemDescription.getDescriptionFromFile("trivial_0");

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
