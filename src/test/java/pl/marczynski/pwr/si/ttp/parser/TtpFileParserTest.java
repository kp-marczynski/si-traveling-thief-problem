package pl.marczynski.pwr.si.ttp.parser;

import org.junit.Test;
import pl.marczynski.pwr.si.ttp.City;
import pl.marczynski.pwr.si.ttp.TravelingThiefProblem;

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
        TravelingThiefProblem ttp = TtpFileParser.parseFile(ttpFileName);

        //then
        assertEquals(ttp.getProblemName(), problemName);
        assertEquals(ttp.getKnapsackDataType(), knapsackDataType);
        assertEquals(ttp.getCities().size(), dimensions);
        assertEquals(ttp.getKnapsackCapacity(), knapsackCapacity);
        assertEquals(ttp.getMinSpeed(), minSpeed, 10e-5);
        assertEquals(ttp.getMaxSpeed(), maxSpeed, 10e-5);
        assertEquals(ttp.getRentingRatio(), rentingRatio, 10e-5);
        assertEquals(ttp.getEdgeWeightType(), edgeWeightType);
        int actualNumberOfItems = 0;
        for (City city : ttp.getCities()) {
            actualNumberOfItems += city.getItems().size();
        }
        assertEquals(actualNumberOfItems, numberOfItems);
    }
}
