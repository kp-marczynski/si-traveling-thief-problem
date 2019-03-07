package pl.marczynski.pwr.si;

public enum TtpFileKey {
    PROBLEM_NAME("PROBLEM NAME"),
    KNAPSACK_DATA_TYPE("KNAPSACK DATA TYPE"),
    NUMBER_OF_CITIES("DIMENSION"),
    NUMBER_OF_ITEMS("NUMBER OF ITEMS"),
    CAPACITY_OF_KNAPSACK("CAPACITY OF KNAPSACK"),
    MIN_SPEED("MIN SPEED"),
    MAX_SPEED("MAX SPEED"),
    RENTING_RATIO("RENTING RATIO"),
    EDGE_WEIGHT_TYPE("EDGE_WEIGHT_TYPE"),
    CITIES("NODE_COORD_SECTION\t(INDEX, X, Y)"),
    ITEMS("ITEMS SECTION\t(INDEX, PROFIT, WEIGHT, ASSIGNED NODE NUMBER)");

    private final String keyValue;

    TtpFileKey(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getValue() {
        return keyValue;
    }
}
