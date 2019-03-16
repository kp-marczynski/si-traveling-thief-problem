package pl.marczynski.pwr.si.ttp.genetic.generation;

import java.util.Arrays;
import java.util.List;

public class GenerationResult {
    private final int orderNumber;
    private final double best;
    private final double average;
    private final double worst;

    public GenerationResult(int orderNumber, double best, double average, double worst) {
        this.orderNumber = orderNumber;
        this.best = best;
        this.average = average;
        this.worst = worst;
    }

    public double getBest() {
        return best;
    }

    public double getAverage() {
        return average;
    }

    public double getWorst() {
        return worst;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public static List<String> getCsvHeader() {
        return Arrays.asList("Generation number", "Best result", "Average result", "Worst result");
    }

    public List<String> getResultAsStringList() {
        return Arrays.asList(String.valueOf(orderNumber), String.valueOf(best), String.valueOf(average), String.valueOf(worst));
    }
}
