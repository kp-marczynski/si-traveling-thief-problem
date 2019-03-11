package pl.marczynski.pwr.si.ttp;

import java.util.List;
import java.util.Map;

public class TravelingThiefProblem {
    private final String problemName;
    private final String knapsackDataType;
    private final int knapsackCapacity;
    private final double minSpeed;
    private final double maxSpeed;
    private final double rentingRatio;
    private final String edgeWeightType;
    private final List<City> cities;
    private final Map<City, Map<City, Double>> distancesBetweenCities;

    public TravelingThiefProblem(String problemName, String knapsackDataType, int knapsackCapacity, double minSpeed, double maxSpeed, double rentingRatio, String edgeWeightType, List<City> cities, Map<City, Map<City, Double>> distancesBetweenCities) {
        this.problemName = problemName;
        this.knapsackDataType = knapsackDataType;
        this.knapsackCapacity = knapsackCapacity;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.rentingRatio = rentingRatio;
        this.edgeWeightType = edgeWeightType;
        this.cities = cities;
        this.distancesBetweenCities = distancesBetweenCities;
    }

    @Override
    public String toString() {
        return "TravelingThiefProblem{" +
                "problemName='" + problemName + '\'' +
                ", knapsackDataType='" + knapsackDataType + '\'' +
                ", knapsackCapacity=" + knapsackCapacity +
                ", minSpeed=" + minSpeed +
                ", maxSpeed=" + maxSpeed +
                ", rentingRatio=" + rentingRatio +
                ", edgeWeightType='" + edgeWeightType + '\'' +
                ", cities=" + cities +
                ", distancesBetweenCities=" + distancesBetweenCities +
                '}';
    }

    public String getProblemName() {
        return problemName;
    }

    public String getKnapsackDataType() {
        return knapsackDataType;
    }

    public int getKnapsackCapacity() {
        return knapsackCapacity;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getRentingRatio() {
        return rentingRatio;
    }

    public String getEdgeWeightType() {
        return edgeWeightType;
    }

    public List<City> getCities() {
        return cities;
    }

    public Map<City, Map<City, Double>> getDistancesBetweenCities() {
        return distancesBetweenCities;
    }
}
