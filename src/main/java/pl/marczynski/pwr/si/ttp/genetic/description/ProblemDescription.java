package pl.marczynski.pwr.si.ttp.genetic.description;

import pl.marczynski.pwr.si.ttp.genetic.generation.genotype.City;

import java.util.List;
import java.util.Map;

public class ProblemDescription {
    private final String fileName;
    private final String problemName;
    private final String knapsackDataType;
    private final int knapsackCapacity;
    private final double minSpeed;
    private final double maxSpeed;
    private final double rentingRatio;
    private final String edgeWeightType;
    private final List<City> cities;
    private final Map<City, Map<City, Double>> distancesBetweenCities;

    public ProblemDescription(String fileName, String problemName, String knapsackDataType, int knapsackCapacity, double minSpeed, double maxSpeed, double rentingRatio, String edgeWeightType, List<City> cities, Map<City, Map<City, Double>> distancesBetweenCities) {
        this.fileName = fileName;
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

    public static ProblemDescription getDescriptionFromFile(String fileName) {
        return TtpFileParser.parseFile(fileName);
    }

    @Override
    public String toString() {
        return "ProblemDescription{" +
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

    public String getFileName() {
        return fileName;
    }

    public Map<City, Map<City, Double>> getDistancesBetweenCities() {
        return distancesBetweenCities;
    }
}
