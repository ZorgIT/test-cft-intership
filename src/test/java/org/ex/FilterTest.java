package org.ex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterTest {

    @Test
    void getLineType() {
        String intLine = "123";
        assertEquals(DataType.INT_LINE, Filter.getLineType(intLine));

        String floatLine = "1.23";
        assertEquals(DataType.FLOAT_LINE, Filter.getLineType(floatLine));

        String stringLine = "lorem impsum )";
        assertEquals(DataType.STRING_LINE, Filter.getLineType(stringLine));
    }
}
