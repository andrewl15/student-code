package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Less20Test {
    Less20 method = new Less20();

    private int testInt;

    @Test
    public void Less20_negative_number(){
        testInt = -1;

        boolean actual = method.isLessThanMultipleOf20(testInt);

        assertFalse(actual);
    }

    @Test
    public void Less20_is_a_multiple(){
        testInt = 39;

        boolean actual = method.isLessThanMultipleOf20(testInt);

        assertTrue(actual);
    }
    @Test
    public void Less20_is_a_zero(){
        testInt = 0;

        boolean actual = method.isLessThanMultipleOf20(testInt);

        assertFalse(actual);
    }

}
