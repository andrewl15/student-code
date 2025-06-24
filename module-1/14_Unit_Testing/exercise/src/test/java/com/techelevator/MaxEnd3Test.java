package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MaxEnd3Test {
    MaxEnd3 method = new MaxEnd3();

    private int[] testArray;

    @Test
    public void MaxEnd_normal_input_first_index_larger(){
        testArray = new int[]{9,2,5};

        int[] expected = new int[]{9,9,9};
        int[] actual = method.makeArray(testArray);

        assertArrayEquals(expected, actual);
    }
    @Test
    public void MaxEnd_normal_input_last_index_larger(){
        testArray = new int[]{9,2,5,3,6,4,10};

        int[] expected = new int[]{10,10,10,10,10,10,10};
        int[] actual = method.makeArray(testArray);

        assertArrayEquals(expected, actual);
    }
    @Test
    public void MaxEnd_bot_values_are_equal(){
        testArray = new int[]{7, 3, 7, 5};

        int[] expected = new int[]{7, 7,7,7};

        int[] actual = method.makeArray(testArray);

        assertArrayEquals(expected, actual);
    }
}
