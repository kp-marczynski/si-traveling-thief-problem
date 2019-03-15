package pl.marczynski.pwr.si.ttp.genetic.generation.genotype;

import java.util.Objects;

public class Item {
    private int itemIndex;
    private int profit;
    private int weight;
    private int cityIndex;

    public int getItemIndex() {
        return itemIndex;
    }

    public int getProfit() {
        return profit;
    }

    public int getWeight() {
        return weight;
    }

    public int getCityIndex() {
        return cityIndex;
    }

    public Item(int itemIndex, int profit, int weight, int cityIndex) {
        this.itemIndex = itemIndex;
        this.profit = profit;
        this.weight = weight;
        this.cityIndex = cityIndex;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemIndex=" + itemIndex +
                ", profit=" + profit +
                ", weight=" + weight +
                ", cityIndex=" + cityIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemIndex == item.itemIndex &&
                cityIndex == item.cityIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemIndex, cityIndex);
    }
}
