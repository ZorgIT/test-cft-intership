package org.ex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class Filter {
    private Filter() {
    }

    public static String filter() {
        System.out.println("Фильтрация данных");
        return "";
    }

    public static void generate(final String filePath1,
                                final String filePath2) {
        readFile(filePath1);
        readFile(filePath2);

    }

    public static String readFile(final String filePath) {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                filterLine(line);
            }

        } catch (IOException e) {
            //TODO перехватить и обработать ошибки
            e.printStackTrace();
        }
        return "File has been read";
    }

    private static void filterLine(String line) {
        String type = isInt(line) ? "int" :
                (isRealNum(line) ? "real" :
                        "string");
        switch (type) {
            case "real":
                //TODO запись в структуру данных для статистики и файл
                System.out.println(line + " is real number");
                break;
            case "int":
                System.out.println(line + " is int number");
                //TODO запись в структуру данных для статистики и файл
                break;
            default:
                System.out.println(line + " is String");
                //TODO запись в структуру данных для статистики и файл
        }

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
