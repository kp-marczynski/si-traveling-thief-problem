package pl.marczynski.pwr.si.ttp;

import java.io.*;
import java.util.List;

public class ResultSaverService {
    private static final String RESULTS_PATH = "./out/results/";
    private static final String JSON_FIRST_PART = "{\n" +
            "    \"size\": {\n" +
            "        \"height\": 600,\n" +
            "        \"width\": 1200\n" +
            "    },\n" +
            "    \"series\": {\n" +
            "        \"x\": \"Generation number\",";

    private static final String JSON_SECOND_PART = "},\n" +
            "    \"data\": {\n" +
            "        \"x\": \"x\",\n" +
            "        \"type\": \"line\"\n" +
            "    },\n" +
            "    \"axis\": {\n" +
            "        \"x\": {\n" +
            "            \"type\": \"indexed\",\n" +
            "            \"label\": {\n" +
            "                \"text\": \"Generation number\",\n" +
            "                \"position\": \"middle\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"y\": {\n" +
            "            \"label\": {\n" +
            "                \"text\": \"Value\",\n" +
            "                \"position\": \"outer-middle\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    \"grid\": {\n" +
            "        \"x\": {\n" +
            "            \"show\": true\n" +
            "        },\n" +
            "        \"y\": {\n" +
            "            \"show\": true\n" +
            "        }\n" +
            "    },\n" +
            "    \"point\": {\n" +
            "        \"show\": false\n" +
            "    }\n" +
            "}";

    public static void saveToFile(String problemName, String baseName, List<String> header, List<List<String>> linesToSave) {
        getDirectory(problemName);

        String fileName = getResultPath(problemName) + "/" + baseName;

        createJsonDefinition(fileName, header);
        createCsvFile(fileName, header, linesToSave);
        generateImage(fileName);
    }

    private static void createCsvFile(String fileName, List<String> header, List<List<String>> linesToSave) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".csv"))) {
            bufferedWriter.write(getCsvLine(header));
            for (List<String> line : linesToSave) {
                bufferedWriter.newLine();
                bufferedWriter.write(getCsvLine(line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createJsonDefinition(String fileName, List<String> header) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".json"))) {
            bufferedWriter.write(JSON_FIRST_PART);
            for (int i = 1; i < header.size(); i++) {
                String headerPart = "\"" + header.get(i) + "\"";
                bufferedWriter.write(headerPart + ": " + headerPart);
                if (i < header.size() - 1) {
                    bufferedWriter.write(",");
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.write(JSON_SECOND_PART);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void generateImage(String fileName) {
        try {
            String command = "c3-chart-maker " + fileName + ".csv --chart=" + fileName + ".json --out=" + fileName + ".png";

            if (isWindows()) {
                command = "cmd /C " + command;
            }
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    private static String getCsvLine(List<String> line) {
        if (line.size() == 0) {
            throw new IllegalStateException("Cannot write empty csv line");
        }
        StringBuilder builder = new StringBuilder().append(line.get(0));
        for (int i = 1; i < line.size(); i++) {
            builder.append(",").append(line.get(i));
        }
        return builder.toString();
    }

    private static String getResultPath(String problemName) {
        return RESULTS_PATH + problemName;
    }

    public static File getDirectory(String problemName) {
        String resultPath = getResultPath(problemName);
        File directory = new File(resultPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        return directory;
    }
}
