package org.ex;

public enum DataType {
    FLOAT_LINE("_floats.txt"),
    INT_LINE("_integers.txt"),
    STRING_LINE("_strings.txt");

    private String baseFileName;

    DataType(String name) {
        this.baseFileName = name;
    }

    public String getBaseFileName() {
        return baseFileName;
    }
}
