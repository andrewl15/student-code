package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonStartTest {
    NonStart method = new NonStart();
    private String testA;
    private String testB;

    private String expected;

    @Test
    public void NonStart_normal_strings(){
        testA = "Hello";
        testB = "Bonjour";

        expected = "elloonjour";

        String actual = method.getPartialString(testA,testB);

        assertEquals(expected, actual);
    }
    @Test
    public void NonStart_null_returns(){
        testA = null;
        testB = null;

        expected = "";

        String actual = method.getPartialString(testA,testB);

        assertEquals(expected, actual);
    }
    @Test
    public void NonStart_one_null_one_size2(){
        testA = null;
        testB = "aa";

        expected = "a";

        String actual = method.getPartialString(testA,testB);

        assertEquals(expected, actual);
    }
    @Test
    public void NonStart_one_null_one_size1(){
        testA = null;
        testB = "a";

        expected = "";

        String actual = method.getPartialString(testA,testB);

        assertEquals(expected, actual);
    }

}
