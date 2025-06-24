package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordCountTest {
    private String[] testArray;
    private Map<String, Integer> expected;

    WordCount method = new WordCount();
    @BeforeEach
    public void init(){
        expected = new HashMap<>();
        testArray = new String[0];
    }

    @Test
    public void getCount_with_null_input_returns_empty_map(){
        testArray = null;
        int expectedSize = 0;
        Map<String, Integer> actual = method.getCount(testArray);

        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void getCount_with_Empty_input_returns_empty_map(){
        expected = new HashMap<>();
        Map<String, Integer> actual = method.getCount(testArray);

        assertEquals(expected, actual);
    }

    @Test
    public void getCount_returns_correct_map_for_normal_array(){
        testArray = new String[]{"Andrew","Bill","Steve"};
        expected.put("Andrew", 1);
        expected.put("Bill", 1);
        expected.put("Steve", 1);

        Map<String, Integer> actual = method.getCount(testArray);

        assertEquals(expected, actual);
    }

    @Test
    public void getCount_returns_correct_map_for_values_that_appear_more_than_once(){
        testArray = new String[]{"Andrew","Bill","Steve","Andrew"};
        expected.put("Andrew", 2);
        expected.put("Bill", 1);
        expected.put("Steve", 1);

        Map<String, Integer> actual = method.getCount(testArray);

        assertEquals(expected, actual);
    }
}
