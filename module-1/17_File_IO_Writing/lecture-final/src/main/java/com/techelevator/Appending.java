package com.techelevator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Appending {
    private final int NUM_TIMES = 1500000;
    public void appendToFile() throws FileNotFoundException, IOException {
        try(FileOutputStream outputStream = new FileOutputStream("append.txt",true);
            PrintWriter pw = new PrintWriter(outputStream)){
            for(int i = 0; i < NUM_TIMES; i++){
                pw.println(i);
            }
        }
    }
    public void appendToFileBad() throws FileNotFoundException, IOException {
        try(FileOutputStream outputStream = new FileOutputStream("appendBad.txt",true)){
            for(int i = 0; i < NUM_TIMES; i++){
                try(PrintWriter pw = new PrintWriter(outputStream)){
                    pw.println(i);
                }

            }
        }
    }
}
