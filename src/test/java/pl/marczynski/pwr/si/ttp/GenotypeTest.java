package pl.marczynski.pwr.si.ttp;

import javafx.util.Pair;
import org.junit.Test;
import pl.marczynski.pwr.si.ttp.parser.TtpFileParser;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GenotypeTest {

    private final ProblemDescription problemDescription = TtpFileParser.parseFile("trivial_0.ttp");

    @Test
    public void shouldCreateShuffledGenotype() {
        //when
        Genotype genotype = Genotype.createShuffledGenotype(problemDescription.getCities());

        //then
        assertEquals(problemDescription.getCities().size(), genotype.getSize());
        for (City city : problemDescription.getCities()) {
            assertTrue(genotype.getCities().contains(city));
        }
    }

    @Test
    public void shouldCreateMutatedGenotype() {
        //given
        Genotype genotype = Genotype.createShuffledGenotype(problemDescription.getCities());

        //when
        Genotype mutated = Genotype.createMutated(genotype);

        //then
        assertNotEquals(genotype, mutated);
        assertEquals(genotype.getSize(), mutated.getSize());
        List<Integer> diff = new LinkedList<>();
        for (int i = 0; i < genotype.getCities().size(); i++) {
            if (genotype.getCities().get(i) != mutated.getCities().get(i)) {
                diff.add(i);
            }
        }
        assertEquals(diff.size(), 2);
        assertEquals(genotype.getCities().get(diff.get(0)), mutated.getCities().get(diff.get(1)));
        assertEquals(genotype.getCities().get(diff.get(1)), mutated.getCities().get(diff.get(0)));
    }

    @Test
    public void shouldCreateCrossedGenotype() {
        //given
        Genotype firstParent = Genotype.createShuffledGenotype(problemDescription.getCities());
        Genotype secondParent = Genotype.createShuffledGenotype(problemDescription.getCities());

        //when
        Pair<Genotype, Genotype> crossed = Genotype.createCrossed(firstParent, secondParent);

        //then
        System.out.println(firstParent);
        System.out.println(secondParent);
        System.out.println(crossed.getKey());
        System.out.println(crossed.getValue());
        for (City city : firstParent.getCities()) {
            assertTrue(crossed.getKey().getCities().contains(city));
            assertTrue(crossed.getValue().getCities().contains(city));
        }
        for (City city : secondParent.getCities()) {
            assertTrue(crossed.getKey().getCities().contains(city));
            assertTrue(crossed.getValue().getCities().contains(city));
        }

        assertEquals(firstParent.getCities().size(), crossed.getKey().getCities().size());
        assertEquals(firstParent.getCities().size(), crossed.getValue().getCities().size());
    }
}
