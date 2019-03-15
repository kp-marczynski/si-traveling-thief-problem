package pl.marczynski.pwr.si.ttp.genetic.generation;

import org.junit.Test;
import pl.marczynski.pwr.si.ttp.genetic.description.ProblemDescription;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void bestGenotypeShouldBeEqualToBestInStream() {
        //given
        Generation firstGeneration = Generation.createFirstGeneration(100, 0.7, 0.01, 5, problemDescription);
        Generation secondGeneration = Generation.createNextGeneration(firstGeneration);
        GenotypeEvaluator evaluator = secondGeneration.getEvaluator();
        double expectedMax = secondGeneration.population.stream().mapToDouble(evaluator::evaluate).max().getAsDouble();

        //when
        double bestResult = secondGeneration.getBestResult();

        //then
        assertEquals(expectedMax, bestResult, 0.001);

    }
}
