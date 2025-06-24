package com.techelevator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinderTest {
    private List<Integer> testList;

    Finder method = new Finder();

    @Test
    public void findLargest_given_empty_list(){
        testList = new ArrayList<>();
        Integer expected = null;

        Integer actual = method.findLargest(testList);

        assertEquals(expected,actual);
    }

    @Test
    public void findLargest_given_null_should_return_null(){
        testList = null;
        Integer expected = null;

        Integer actual = method.findLargest(testList);

        assertEquals(expected,actual);
    }
    @Test
    public void findLargest_given_all_the_same_number(){
        testList = new ArrayList<>();
        testList.add(0);
        testList.add(0);
        testList.add(0);
        testList.add(0);

        Integer expected = 0;

        Integer actual = method.findLargest(testList);

        assertEquals(expected,actual);
    }
    @Test
    public void findLargest_given_normal_list(){
        testList = new ArrayList<>();
        testList.add(5);
        testList.add(2);
        testList.add(4);
        testList.add(-50);

        Integer expected = 5;

        Integer actual = method.findLargest(testList);

        assertEquals(expected,actual);
    }
    @Test
    public void findLargest_given_list_with_back2back_larger_int(){
        testList = new ArrayList<>();
        testList.add(200);
        testList.add(230);
        testList.add(250);
        testList.add(251);

        Integer expected = 251;

        Integer actual = method.findLargest(testList);

        assertEquals(expected,actual);
    }
    @Test
    public void findLargest_a_value_in_list_is_null(){
        testList = new ArrayList<>();
        testList.add(200);
        testList.add(null);
        testList.add(250);
        testList.add(null);

        Integer expected = 250;

        Integer actual = method.findLargest(testList);

        assertEquals(expected,actual);
    }
    @Test
    public void findLargest_first_value_is_null(){
        testList = new ArrayList<>();
        testList.add(null);
        testList.add(200);
        testList.add(250);
        testList.add(null);

        Integer expected = 250;

        Integer actual = method.findLargest(testList);

        assertEquals(expected,actual);
    }

}
