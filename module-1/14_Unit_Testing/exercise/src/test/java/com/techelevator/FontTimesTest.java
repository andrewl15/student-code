package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FontTimesTest {
    FrontTimes method = new FrontTimes();

    private String testString;
    private int testInt;

    @Test
    public void generateString_null_input_returns_empty_string(){
        testString = null;
        testInt = 2;
        String expected = "";

        String actual = method.generateString(testString, testInt);

        assertEquals(expected, actual);
    }
    @Test
    public void generateString_given_string_length_3(){
        testString = "abc";
        testInt = 3;
        String expected = "abcabcabc";

        String actual = method.generateString(testString, testInt);

        assertEquals(expected, actual);
    }
    @Test
    public void generateString_given_stringLength_greater_than_3(){
        testString = "abcdefg";
        testInt = 2;

        String expected = "abcabc";

        String actual = method.generateString(testString, testInt);

        assertEquals(expected, actual);
    }

    @Test
    public void generateString_empty_string(){
        testString = "";
        testInt = 2;

        String expected = "";

        assertEquals(expected, method.generateString(testString,testInt));
    }

    @Test
    public void generateString_single_character_returns_character(){
        testString = "a";
        testInt = 3;

        String expected = "aaa";

        assertEquals(expected, method.generateString(testString,testInt));


    }
}
