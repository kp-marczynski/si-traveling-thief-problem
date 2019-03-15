package pl.marczynski.pwr.si.ttp.genetic.generation.genotype;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class City {
    private final int cityIndex;
    private final double xCoordinate;
    private final double yCoordinate;
    private final List<Item> items;

    public City(int cityIndex, double xCoordinate, double yCoordinate, List<Item> items) {
        this.cityIndex = cityIndex;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.items = items;
    }

    public int getCityIndex() {
        return cityIndex;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items.addAll(items);
    }

    public Optional<Item> selectItem(double maxWeight) {
        return items.stream().filter(item -> item.getWeight() <= maxWeight).max(Comparator.comparingInt(Item::getProfit));
    }

    @Override
    public String toString() {
        return "City{" +
                "cityIndex=" + cityIndex +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return cityIndex == city.cityIndex &&
                Double.compare(city.xCoordinate, xCoordinate) == 0 &&
                Double.compare(city.yCoordinate, yCoordinate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityIndex, xCoordinate, yCoordinate);
    }
}
