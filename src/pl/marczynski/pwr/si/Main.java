package pl.marczynski.pwr.si;

import pl.marczynski.pwr.si.parser.TtpFileParser;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        TravelingThiefProblem ttp = TtpFileParser.parseFile("easy_0.ttp");
        TspGenotype tspGenotype = TspGenotype.createShuffledGenotype(ttp.getCities());
        TspGenotypeEvaluator evaluator = new TspGenotypeEvaluator(ttp.getDistancesBetweenCities());
        System.out.println(tspGenotype);
        System.out.println(evaluator.evaluate(tspGenotype));

    }
}
