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
    /**
     * Big data file can be processed by buffered reader.
     * a simple cache was developed instead of aa for educational purposes
     * cache reduce write operation to the disk
     * CACHE_SIZE is line count before write process started
     */

    private static Map<DataType, Boolean> appendStatus;
    private static Map<DataType, List<String>> lineCache = new HashMap<>();
    private static final int CACHE_SIZE = 100;
    private static Stats stats;

    static {
        appendStatus = new HashMap<>();
        appendStatus.put(INT_LINE, App.isAppendStatus());
        appendStatus.put(FLOAT_LINE, App.isAppendStatus());
        appendStatus.put(STRING_LINE, App.isAppendStatus());
    }


    private Filter() {
    }

    public static void setStats(Stats stats) {
        Filter.stats = stats;
    }

    public static void generate(final List<String> filePaths, final Stats statsE) {
        setStats(statsE);
        filePaths.forEach(x -> {
            try {
                readAndProcessData(x);
            } catch (IOException e) {
                System.out.println("Error reading file: " + x + "[" + e.getMessage() + "]");
            }
        });
    }

    public static String readAndProcessData(final String filePath) throws IOException {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            lineCache.put(INT_LINE, new ArrayList<>());
            lineCache.put(FLOAT_LINE, new ArrayList<>());
            lineCache.put(STRING_LINE, new ArrayList<>());
            String line;

            int cacheCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                lineCache.get(getLineType(line)).add(line);
                cacheCounter++;
                if (cacheCounter == CACHE_SIZE) {
                    writeProcessedDataCache();
                    cacheCounter = 0;
                }
            }

            int count = lineCache.values().stream()
                    .mapToInt(List::size)
                    .sum();
            if (count > 0) {
                writeProcessedDataCache();
            }

        } catch (IOException e) {
            //TODO перехватить и обработать ошибки, записать имеющиеся данные
            e.printStackTrace();
        }
        return "File has been read";
    }

    private static void writeProcessedDataCache() throws IOException {
        for (Map.Entry<DataType, List<String>> entry : lineCache.entrySet()) {
            DataType type = entry.getKey();
            List<String> line = entry.getValue();

            String newLine = System.lineSeparator();
            if (!line.isEmpty()) {
                String data = line.stream()
                        .collect(Collectors.joining(newLine)) + newLine;
                writeData(data, type);
                stats.processRecord(type, line);
                line.clear(); // clear cache
            }
        }
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
                System.out.println("Error writing file: " + e.getMessage());
            }
        }
    }

    static DataType getLineType(String line) {
        DataType type = isInt(line) ? DataType.INT_LINE
                : (isRealNum(line) ? DataType.FLOAT_LINE : DataType.STRING_LINE);
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
