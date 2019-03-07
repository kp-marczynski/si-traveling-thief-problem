package pl.marczynski.pwr.si;

import java.util.List;
import java.util.Objects;

public class City {
    private int cityIndex;
    private double xCoordinate;
    private double yCoordinate;
    private List<Item> items;

    public City(int cityIndex, double xCoordinate, double yCoordinate) {
        this.cityIndex = cityIndex;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
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
        this.items = items;
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
