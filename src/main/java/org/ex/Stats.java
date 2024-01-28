package org.ex;

import java.math.BigDecimal;
import java.util.List;

public class Stats {
    private static BigDecimal numMin;
    private static BigDecimal numMax;
    private static BigDecimal numSum;
    private static BigDecimal numAverage;
    private static int numCounter;
    private static int strCounter;
    private static int minLength;
    private static int maxLength;

    protected Stats() {
        setNumMin(BigDecimal.ZERO);
        setNumMax(BigDecimal.ZERO);
        setNumSum(BigDecimal.ZERO);
        setNumAverage(BigDecimal.ZERO);
    }

    public void showStats() {
        if (App.isFullReport() && App.isShortReport()) {
            getAndShowShort();
            getAndShowFull();
        } else if (App.isFullReport()) {
            getAndShowFull();
        } else {
            getAndShowShort();
        }
    }

    public int getAndShowShort() {
        int result = numCounter + strCounter;
        System.out.println("Total processed records: " + result);
        return result;
    }

    public String getAndShowFull() {
        System.out.println("Numbers processed stats:");
        System.out.printf("Total: %d\n"
                        + "minimal value is: %f\n"
                        + "maximal value is: %f\n"
                        + "summary value is: %f\n"
                        + "average value is: %f\n",
                getNumCounter(), getNumMin(), getNumMax(), getNumSum(), getNumAverage());
        System.out.println("=======================");
        System.out.println("String processed stats:");
        System.out.printf("Total: %d\n"
                        + "minimal length is: %s\n"
                        + "maximal length is: %s\n",
                getStrCounter(), getMinLength(), getMaxLength());
        //:TODO собрать возврат, определить форматтер отображения
        return "result print completed";
    }

    public void processRecord(DataType type, final List<String> rawRecords) {
        switch (type) {
            case STRING_LINE:
                processString(rawRecords);
                break;
            default:
                processNumbers(rawRecords);
        }
    }

    private void processNumbers(List<String> numberValues) {
        if (numberValues.size() > 0) {
            setNumAverage(getNewAverage(numberValues));
            setNumCounter(getNumCounter() + numberValues.size());
            setNumMin(getNewMin(numberValues));
            setNumMax(getNewMax(numberValues));
            setNumSum(getNewSum(numberValues));
        }
    }

    private BigDecimal getNewSum(List<String> numberValues) {
        return numberValues.stream()
                .map(BigDecimal::new)
                .reduce(getNumSum(), BigDecimal::add);
    }

    private BigDecimal getNewAverage(List<String> numberValues) {
        BigDecimal newAverage = numberValues.stream()
                .map(BigDecimal::new)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sum = getNumAverage().add(newAverage);
        BigDecimal average = sum.divide(BigDecimal.valueOf(2), 6, BigDecimal.ROUND_HALF_UP);
        return average;
    }

    private static BigDecimal getNewMax(List<String> numberValues) {
        BigDecimal newMaxValue = numberValues.stream()
                .map(BigDecimal::new)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        newMaxValue = getNumMax().max(newMaxValue);
        return newMaxValue;
    }

    private static BigDecimal getNewMin(List<String> numberValues) {
        BigDecimal newMinValue = numberValues.stream()
                .map(BigDecimal::new)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        newMinValue = getNumMin().min(newMinValue);
        return newMinValue;
    }

    private void processString(List<String> strings) {
        setStrCounter(getStrCounter() + strings.size());
        if (getMinLength() == 0) {
            setMinLength(strings.get(0).length());
        } else {
            setMinLength(getNewMinLength(strings));
        }
        setMaxLength(getNewMaxLength(strings));
    }

    private int getNewMaxLength(List<String> strings) {
        int newMax = strings.stream()
                .mapToInt(String::length)
                .max()
                .orElse(getMaxLength());
        newMax = newMax > getMaxLength() ? newMax : getMaxLength();
        return newMax;
    }

    private int getNewMinLength(List<String> strings) {
        int newMin = strings.stream()
                .filter(x -> x.length() > 0)
                .mapToInt(String::length)
                .min()
                .orElse(getMinLength());
        newMin = newMin < getMinLength() ? newMin : getMinLength();
        return newMin;
    }

    public static BigDecimal getNumMin() {
        return numMin;
    }

    public static void setNumMin(BigDecimal numMin) {
        Stats.numMin = numMin;
    }

    public static BigDecimal getNumMax() {
        return numMax;
    }

    public static void setNumMax(BigDecimal numMax) {
        Stats.numMax = numMax;
    }

    public static BigDecimal getNumSum() {
        return numSum;
    }

    public static void setNumSum(BigDecimal numSum) {
        Stats.numSum = numSum;
    }

    public static BigDecimal getNumAverage() {
        return numAverage;
    }

    public static void setNumAverage(BigDecimal numAverage) {
        Stats.numAverage = numAverage;
    }

    public static int getNumCounter() {
        return numCounter;
    }

    public static void setNumCounter(int numCounter) {
        Stats.numCounter = numCounter;
    }

    public static int getStrCounter() {
        return strCounter;
    }

    public static void setStrCounter(int strCounter) {
        Stats.strCounter = strCounter;
    }

    public static int getMinLength() {
        return minLength;
    }

    public static void setMinLength(int minLength) {
        Stats.minLength = minLength;
    }

    public static int getMaxLength() {
        return maxLength;
    }

    public static void setMaxLength(int maxLength) {
        Stats.maxLength = maxLength;
    }
}
