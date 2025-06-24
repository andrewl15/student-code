package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SameFirstLastTest {
    SameFirstLast method = new SameFirstLast();
    private int[] testArray;

    @Test
    public void TheSame_length_greater_than_1_and_first_and_last_equal(){
        testArray = new int[]{2,3,4,5,2};

        boolean actual = method.isItTheSame(testArray);

        assertTrue(actual);
    }
    @Test
    public void TheSame_length_empty_array(){
        testArray = new int[0];

        boolean actual = method.isItTheSame(testArray);

        assertFalse(actual);
    }
    @Test
    public void TheSame_length_1st_and_last_are_not_equal(){
        testArray = new int[]{2,3,4,5,3};

        boolean actual = method.isItTheSame(testArray);

        assertFalse(actual);
    }
    @Test
    public void TheSame_length_1st_and_last_are_not_equal2(){
        testArray = new int[]{3,3,4,5,2};

        boolean actual = method.isItTheSame(testArray);

        assertFalse(actual);
    }
    @Test
    public void TheSame_null_array(){
        testArray = null;

        boolean actual = method.isItTheSame(testArray);

        assertFalse(actual);
    }
}
