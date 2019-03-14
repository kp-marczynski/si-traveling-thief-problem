package pl.marczynski.pwr.si.ttp;

import pl.marczynski.pwr.si.ttp.parser.TtpFileParser;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        TravelingThiefProblem ttp = TtpFileParser.parseFile("trivial_0.ttp");
        GeneticAlgorithm ga = GeneticAlgorithm.initialize("easy_0.ttp", 100, 10, 0.7, 0.01, 5);
        ga.run();

    }
}
