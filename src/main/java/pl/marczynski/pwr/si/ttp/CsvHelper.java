package pl.marczynski.pwr.si.ttp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class CsvHelper {
    private static final String RESULTS_PATH = "./out/results/";

    public static void saveToFile(String problemName, String baseName, List<String> header, List<List<String>> linesToSave) {
        File directory = getDirectory(problemName, baseName);

        int numberOfFilesInDirectory = directory.listFiles().length;

        String fileName = new StringBuilder()
                .append(getResultPath(problemName, baseName)).append("/")
                .append(baseName)
//                .append("-v_").append(numberOfFilesInDirectory)
                .append(".csv").toString();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(getCsvLine(header));
            for (List<String> line : linesToSave) {
                bufferedWriter.newLine();
                bufferedWriter.write(getCsvLine(line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private static String getResultPath(String problemName, String baseName) {
        return RESULTS_PATH + problemName;// + "/" + baseName;
    }

    private static File getDirectory(String problemName, String baseName) {
        String resultPath = getResultPath(problemName, baseName);
        File directory = new File(resultPath);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        return directory;
    }
}
