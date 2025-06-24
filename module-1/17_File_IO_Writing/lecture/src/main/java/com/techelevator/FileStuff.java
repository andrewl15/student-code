package com.techelevator;

import java.io.IOException;

public class FileStuff {
    public static void main(String[] args){
        //instantiate object
        LoopingCollections lc = new LoopingCollections();
        Appending append = new Appending();
        try {
            lc.run();
            long start = System.nanoTime();
            append.appendToFile();
            long end = System.nanoTime();
            System.out.println("AppendToFile took " + (end - start) + " nanoseconds");
            start = System.nanoTime();
            append.appendToFileBad();
            end = System.nanoTime();
            System.out.println("AppendToFileBad took " + (end - start) + " nanoseconds");

        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
