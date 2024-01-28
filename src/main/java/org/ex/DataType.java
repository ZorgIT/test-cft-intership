package org.ex;

public enum DataType {
    FLOAT_LINE("floats.txt"),
    INT_LINE("integers.txt"),
    STRING_LINE("strings.txt");

    private String baseFileName;

    DataType(String name) {
        this.baseFileName = name;
    }

    public String getBaseFileName() {
        return baseFileName;
    }
}
