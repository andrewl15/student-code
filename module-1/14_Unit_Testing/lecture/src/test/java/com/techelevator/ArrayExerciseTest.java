package com.techelevator;

import com.techelevator.arrays.ArrayExercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayExerciseTest {
    private ArrayExercise sut;
    @BeforeEach
    public void init(){
        sut = new ArrayExercise();
    }

    @Test
    public void reverseArray_with_null_input_should_return_null(){
        //Arrange


        //Act
        int[] actual = sut.reverseArray(null);

        //assert
        assertNull(actual);
    }
    @Test
    public void reverseArray_happy_path(){
        //Arrange
        int[] input = {1,2,3,4,5,6};
        int[] expected = {6,5,4,3,2,1};

        //Act
        int[] actual = sut.reverseArray(input);

        //assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void reverseArray_with_empty_array(){
        int[] input = new int[0];
        int[] exp = new int[0];

        int[] actual = sut.reverseArray(input);

        assertArrayEquals(exp, actual);
    }
}
