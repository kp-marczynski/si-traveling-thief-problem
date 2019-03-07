package pl.marczynski.pwr.si.parser;

import pl.marczynski.pwr.si.City;
import pl.marczynski.pwr.si.Item;
import pl.marczynski.pwr.si.TravelingThiefProblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class TtpFileParser {
    private static final String DATA_PATH = "src/resources/data/";

    public static TravelingThiefProblem parseFile(String fileName) {
        Map<String, String> basicData = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(DATA_PATH + fileName))) {
            List<City> cities = null;
            List<Item> items = null;
            String line = fileReader.readLine();
            while (line != null) {
                String[] splittedLine = line.split(":");
                if (splittedLine.length == 2) {
                    String currentKey = splittedLine[0].trim();
                    basicData.put(currentKey, splittedLine[1].trim());
                    if (currentKey.equals(TtpFileKey.CITIES.getValue())) {
                        int numberOfCities = Integer.parseInt(basicData.get(TtpFileKey.NUMBER_OF_CITIES.getValue()));
                        cities = parseCities(fileReader, numberOfCities);
                    } else if (currentKey.equals(TtpFileKey.ITEMS.getValue())) {
                        int numberOfItems = Integer.parseInt(basicData.get(TtpFileKey.NUMBER_OF_ITEMS.getValue()));
                        items = parseItems(fileReader, numberOfItems);
                    }
                }
                line = fileReader.readLine();
            }
            return finishParsing(basicData, Objects.requireNonNull(cities), items);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TravelingThiefProblem finishParsing(Map<String, String> basicData, List<City> cities, List<Item> items) {
        Map<City, Map<City, Double>> distancesBetweenCities = new HashMap<>();
        for (City city : cities) {
            city.setItems(items.stream().filter(item -> item.getCityIndex() == city.getCityIndex()).collect(Collectors.toList()));
            Map<City, Double> innerMap = new HashMap<>();
            distancesBetweenCities.put(city, innerMap);

            for (City innerCity : cities) {
                innerMap.put(innerCity, calculateDistance(city, innerCity));
            }
        }

        String problemName = basicData.get(TtpFileKey.PROBLEM_NAME.getValue());
        String knapsackDataType = basicData.get(TtpFileKey.KNAPSACK_DATA_TYPE.getValue());
        int knapsackCapacity = Integer.parseInt(basicData.get(TtpFileKey.CAPACITY_OF_KNAPSACK.getValue()));
        double minSpeed = Double.parseDouble(basicData.get(TtpFileKey.MIN_SPEED.getValue()));
        double maxSpeed = Double.parseDouble(basicData.get(TtpFileKey.MAX_SPEED.getValue()));
        double rentingRatio = Double.parseDouble(basicData.get(TtpFileKey.RENTING_RATIO.getValue()));
        String edgeWeightType = basicData.get(TtpFileKey.EDGE_WEIGHT_TYPE.getValue());


        return new TravelingThiefProblem(problemName, knapsackDataType, knapsackCapacity, minSpeed, maxSpeed, rentingRatio, edgeWeightType, cities, distancesBetweenCities);
    }

    private static Double calculateDistance(City city, City innerCity) {
        double x = city.getxCoordinate() - innerCity.getxCoordinate();
        double y = city.getyCoordinate() - innerCity.getyCoordinate();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    private static List<City> parseCities(BufferedReader fileReader, int numberOfCities) throws IOException {
        List<City> cities = new LinkedList<>();
        while (cities.size() < numberOfCities) {
            String line = fileReader.readLine();
            if (line == null) {
                throw new IllegalStateException("File ended without providing required number of city definitions");
            }
            String[] splitedLine = line.split("\t");

            int cityIndex = Integer.parseInt(splitedLine[0]);
            double xCoor = Double.parseDouble(splitedLine[1]);
            double yCoor = Double.parseDouble(splitedLine[2]);

            cities.add(new City(cityIndex, xCoor, yCoor, new LinkedList<>()));
        }
        return cities;
    }

    private static List<Item> parseItems(BufferedReader fileReader, int numberOfItems) throws IOException {
        List<Item> items = new LinkedList<>();
        while (items.size() < numberOfItems) {
            String line = fileReader.readLine();
            if (line == null) {
                throw new IllegalStateException("File ended without providing required number of items definitions");
            }
            String[] splitedLine = line.split("\t");
            int itemIndex = Integer.parseInt(splitedLine[0]);
            int profit = Integer.parseInt(splitedLine[1]);
            int weight = Integer.parseInt(splitedLine[2]);
            int cityIndex = Integer.parseInt(splitedLine[3]);

            items.add(new Item(itemIndex, profit, weight, cityIndex));
        }
        return items;
    }
}
