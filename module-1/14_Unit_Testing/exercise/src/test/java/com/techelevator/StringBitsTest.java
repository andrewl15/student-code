package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringBitsTest {
    private String testString;
    private String expected;
    StringBits method = new StringBits();

    @BeforeEach
    public void setup(){testString = null; expected = null;}
    @Test
    public void getBits_with_null_input_should_return_empty_string(){
        testString = null;
        expected = "";

        String actual = method.getBits(testString);

        assertEquals(expected, actual);
    }
    @Test
    public void getBits_with_empty_input_should_return_empty_string(){
        testString = "";

        String actual = method.getBits(testString);

        assertEquals(testString, actual);
    }
    @Test
    public void getBits_regular_string_returns_expected_result(){
        testString = "Hello";
        expected = "Hlo";

        String actual = method.getBits(testString);

        assertEquals(expected, actual);
    }
    @Test
    public void getBits_single_char_String_returns_one_char(){
        testString = "a";

        String actual = method.getBits(testString);

        assertEquals(testString, actual);
    }

}
