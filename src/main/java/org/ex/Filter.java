package org.ex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.ex.DataType.INT_LINE;
import static org.ex.DataType.FLOAT_LINE;
import static org.ex.DataType.STRING_LINE;

public final class Filter {

    private static Map<DataType, Boolean> appendStatus;

    static {
        appendStatus = new HashMap<>();
        appendStatus.put(INT_LINE, App.isAppendStatus());
        appendStatus.put(FLOAT_LINE, App.isAppendStatus());
        appendStatus.put(STRING_LINE, App.isAppendStatus());
    }

    /**
     * Big data file can be processed.
     * cash reduce write on the disk operation
     * CASH_SIZE is line count to write
     */
    private static Map<DataType, List<String>> lineCash = new HashMap<>();

    private static final int CASH_SIZE = 100;

    private Filter() {
    }

    public static String filter() {
        System.out.println("Фильтрация данных");
        return "";
    }

    public static void generate(final List<String> filePaths) {
        filePaths.forEach(x -> {
            readAndProcessData(x);
        });
    }

    public static String readAndProcessData(final String filePath) {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            lineCash.put(INT_LINE, new ArrayList<>());
            lineCash.put(FLOAT_LINE, new ArrayList<>());
            lineCash.put(STRING_LINE, new ArrayList<>());
            String line;

            int cashCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                lineCash.get(getLineType(line)).add(line);
                cashCounter++;
                if (cashCounter == CASH_SIZE) {
                    writeProcessedDataCash();
                    cashCounter = 0;
                }
            }
            //check cash flush
            int count = lineCash.values().stream()
                    .mapToInt(List::size)
                    .sum();
            if (count > 0) {
                writeProcessedDataCash();
            }

        } catch (IOException e) {
            //TODO перехватить и обработать ошибки, записать имеющиеся данные
            e.printStackTrace();
        }
        return "File has been read";
    }

    private static void writeProcessedDataCash() throws IOException {
        lineCash.forEach((type, line) -> {
            String newLine = System.lineSeparator();
            if (!line.isEmpty()) {
                String data = line.stream()
                        .collect(Collectors.joining(newLine)) + newLine;
                writeData(data, type);
                //clear cash
                lineCash.get(type).clear();
            }
        });
    }

    private static void writeData(String data, DataType type) {
        String writeDir = App.getOutputPath();
        String fileName = type.getBaseFileName();
        String filePath = writeDir + System.getProperty("file.separator") + App.getFileNamePrefix() + fileName;
        if (data.length() > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, appendStatus.get(type)))) {
                writer.write(data);
                appendStatus.put(type, true);
            } catch (IOException e) {
                System.out.println("ER:Error writing file: " + e.getMessage());
            }
        }
    }

    private static DataType getLineType(String line) {
        DataType type = isInt(line) ? INT_LINE
                :
                (isRealNum(line) ? FLOAT_LINE
                        :
                        STRING_LINE);
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
            new BigInteger(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
