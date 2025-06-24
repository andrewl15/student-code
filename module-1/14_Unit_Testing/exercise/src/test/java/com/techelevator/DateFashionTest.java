package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateFashionTest {
    private int youTest;
    private int dateTest;
    private int expected;
     DateFashion method = new DateFashion();

    @Test
    public void getATable_with_both_being_5_returns_1(){
        youTest = 5;
        dateTest = 5;
        expected = 1;

        int actual = method.getATable(youTest, dateTest);

        assertEquals(expected, actual);
    }
    @Test
    public void getATable_with_one_being_2_and_one_being_not_2orless_return_0(){
        youTest = 2;
        dateTest = 5;
        expected = 0;

        int actual = method.getATable(youTest, dateTest);

        assertEquals(expected, actual);
    }
    @Test
    public void getATable_with_both_one_being_negative_and_other_being_10(){
        youTest = 10;
        dateTest = -5;
        expected = 0;

        int actual = method.getATable(youTest, dateTest);

        assertEquals(expected, actual);
    }
    @Test
    public void getATable_with_both_one_being_5_and_other_being_out_of_range(){
        youTest = 20;
        dateTest = 5;
        expected = 2;

        int actual = method.getATable(youTest, dateTest);

        assertEquals(expected, actual);
    }
}
