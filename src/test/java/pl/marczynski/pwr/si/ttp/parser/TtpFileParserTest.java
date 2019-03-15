package pl.marczynski.pwr.si.ttp.parser;

import org.junit.Test;
import pl.marczynski.pwr.si.ttp.City;
import pl.marczynski.pwr.si.ttp.ProblemDescription;

import static org.junit.Assert.*;

public class TtpFileParserTest {

    @Test
    public void shouldImportTrivial() {
        //given
        String ttpFileName = "trivial_0.ttp";
        String problemName = "berlin52-TTP_trivialized";
        String knapsackDataType = "bounded strongly corr";
        int dimensions = 10;
        int numberOfItems = 9;
        int knapsackCapacity = 2246;
        double minSpeed = 0.1;
        double maxSpeed = 1;
        double rentingRatio = 0.31;
        String edgeWeightType = "CEIL_2D";

        //when
        ProblemDescription problemDescription = TtpFileParser.parseFile(ttpFileName);

        //then
        assertEquals(problemDescription.getProblemName(), problemName);
        assertEquals(problemDescription.getKnapsackDataType(), knapsackDataType);
        assertEquals(problemDescription.getCities().size(), dimensions);
        assertEquals(problemDescription.getKnapsackCapacity(), knapsackCapacity);
        assertEquals(problemDescription.getMinSpeed(), minSpeed, 10e-5);
        assertEquals(problemDescription.getMaxSpeed(), maxSpeed, 10e-5);
        assertEquals(problemDescription.getRentingRatio(), rentingRatio, 10e-5);
        assertEquals(problemDescription.getEdgeWeightType(), edgeWeightType);
        int actualNumberOfItems = 0;
        for (City city : problemDescription.getCities()) {
            actualNumberOfItems += city.getItems().size();
        }
        assertEquals(actualNumberOfItems, numberOfItems);
    }
}
