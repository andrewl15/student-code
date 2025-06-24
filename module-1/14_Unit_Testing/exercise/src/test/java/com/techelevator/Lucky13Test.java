package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Lucky13Test {
    Lucky13 method = new Lucky13();

    int[] testArray;

    @Test
    public void getLucky_null_returns_true(){
        testArray = null;

        boolean actual = method.getLucky(testArray);

        assertTrue(actual);
    }
    @Test
    public void getLucky_array_with_no_1_or_3(){
        testArray = new int[]{0,2,4,5,6,7};

        boolean actual = method.getLucky(testArray);

        assertTrue(actual);
    }
    @Test
    public void getLucky_array_with_1_not_3(){
        testArray = new int[]{0,1,2,4,5,6,7};

        boolean actual = method.getLucky(testArray);

        assertFalse(actual);
    }
    @Test
    public void getLucky_array_with_3_not_1(){
        testArray = new int[]{0,2,3,4,5,6,7};

        boolean actual = method.getLucky(testArray);

        assertFalse(actual);
    }
    @Test
    public void getLucky_array_with_1_and_3(){
        testArray = new int[]{0,1,2,3,4,5,6,7};

        boolean actual = method.getLucky(testArray);

        assertFalse(actual);
    }
}
