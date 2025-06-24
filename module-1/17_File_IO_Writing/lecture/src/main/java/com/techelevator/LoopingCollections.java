package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class LoopingCollections {
    public void run() throws IOException {
        Map<String, Double> programmingLanguages = new LinkedHashMap<>();
        programmingLanguages.put("Java", 100.0);
        programmingLanguages.put("C", 99.9);
        programmingLanguages.put("C++", 99.4);
        programmingLanguages.put("Python", 96.5);
        programmingLanguages.put("C#", 91.3);
        programmingLanguages.put("R", 84.8);
        programmingLanguages.put("PHP", 84.5);
        programmingLanguages.put("JavaScript", 83.0);
        programmingLanguages.put("Ruby", 76.2);
        programmingLanguages.put("Matlab", 72.4);


        //create the output file
        File outputFile = new File("programming_languages.txt");
        outputFile.createNewFile();

        //Write out information
        try(PrintWriter pw = new PrintWriter(outputFile)){
            //loop through the map and write the lines to the buffer
            for(String key: programmingLanguages.keySet()){
                String lineToWrite = key;
                lineToWrite += " is an IEEE is a top 10 language with a spectrum of ";
                lineToWrite += programmingLanguages.get(key);
                pw.println(lineToWrite);
            }
        }
    }

}
