package pl.marczynski.pwr.si.ttp;

import javafx.util.Pair;
import pl.marczynski.pwr.si.ttp.parser.TtpFileParser;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        TravelingThiefProblem ttp = TtpFileParser.parseFile("trivial_0.ttp");
//        Genotype genotype = Genotype.createShuffledGenotype(ttp.getCities());
//        GenotypeEvaluator evaluator = new GenotypeEvaluator(ttp);
//        System.out.println(genotype);
//        System.out.println(evaluator.evaluate(genotype));
//        System.out.println(ttp);

        Genotype genotype1 = Genotype.createShuffledGenotype(ttp.getCities());
        Genotype genotype2 = Genotype.createShuffledGenotype(ttp.getCities());

        Pair<Genotype, Genotype> crossed = Genotype.createCrossed(genotype1, genotype2);
        System.out.println(genotype1);
        System.out.println(genotype2);
        System.out.println(crossed.getKey());
        System.out.println(crossed.getValue());
        System.out.println(Genotype.createMutated(crossed.getValue()));
    }
}
