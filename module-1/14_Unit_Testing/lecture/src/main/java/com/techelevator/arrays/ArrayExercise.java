package com.techelevator.arrays;

public class ArrayExercise {

    public int[] reverseArray(int[] arrayToReverse) {
        if(arrayToReverse == null){
            return null;
        }
        int[] output = new int[arrayToReverse.length];
        int outputIndex = 0;
        for(int i = arrayToReverse.length - 1; i > -1; i--){
            output[outputIndex] = arrayToReverse[i];
            outputIndex++;
        }
        return output;
    }
}
