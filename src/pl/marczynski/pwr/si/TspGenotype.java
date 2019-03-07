package pl.marczynski.pwr.si;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TspGenotype {
    List<City> cities;

    public TspGenotype(List<City> cities) {
        this.cities = new LinkedList<>(cities);
    }

    public static TspGenotype createShuffledGenotype(List<City> cities){
        TspGenotype result = new TspGenotype(cities);
        Collections.shuffle(result.cities);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TspGenotype{cities=");
        for (City city : cities) {
            builder.append(city.getCityIndex()).append(";");
        }
        builder.append("}");
        return builder.toString();
    }
}
