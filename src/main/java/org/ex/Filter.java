package org.ex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class Filter {
    private Filter() {
    }

    /**
     * Big data file can be processed.
     * cash reduce write on the disk operation
     * CASH_SIZE is line count to write
     */
    private static Map<String, List<String>> lineCash = new HashMap<>();

    private static final int CASH_SIZE = 5;

    public static String filter() {
        System.out.println("Фильтрация данных");
        return "";
    }

    public static void generate(final List<String> filePaths) {
        filePaths.forEach(x -> readAndProcessData(x));
    }

    public static String readAndProcessData(final String filePath) {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            lineCash.put("int", new ArrayList<>());
            lineCash.put("real", new ArrayList<>());
            lineCash.put("string", new ArrayList<>());

            String line;
            String lineType;

            while ((line = reader.readLine()) != null) {
                lineType = getLineType(line);
                lineCash.get(lineType).add(line);
            }
            writeProcessedData();
        } catch (IOException e) {
            //TODO перехватить и обработать ошибки, записать имеющиеся данные
            e.printStackTrace();
        }
        return "File has been read";
    }

    private static void writeProcessedData() throws IOException {
        String writeDir = System.getProperty("user.dir");
        String fileName = "strings.txt";
        String filePath = writeDir + System.getProperty("file.separator") + fileName;
        boolean append = true;
        lineCash.forEach((type, line) -> {
            String data = line.stream()
                    .collect(Collectors.joining(System.lineSeparator()));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
                writer.write(data);
            } catch (IOException e) {
                System.out.println("Ошибка при записи файла: " + e.getMessage());
            }
        });
    }

    private static String getLineType(String line) {
        String type = isInt(line) ? "int"
                :
                (isRealNum(line) ? "real"
                        :
                        "string");
        return type;
    }

    private static boolean isRealNum(String line) {
        try {
            new BigDecimal(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isInt(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
