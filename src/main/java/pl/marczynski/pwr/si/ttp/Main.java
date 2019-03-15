package pl.marczynski.pwr.si.ttp;

import pl.marczynski.pwr.si.ttp.parser.TtpFileParser;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        ProblemDescription ttp = TtpFileParser.parseFile("trivial_0.ttp");
        GeneticAlgorithm ga = GeneticAlgorithm.initialize("hard_0.ttp", 1000, 100, 0.5, 0.01, 5);
        ga.run();

    }
}
