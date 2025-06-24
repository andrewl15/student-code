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

        // Create the output file
        File outputFile = new File("programming_languages.txt");
        outputFile.createNewFile();

        // Write out our information
        try(PrintWriter pw = new PrintWriter(outputFile)){
            // loop through our Map and write the lines to the buffer
            for(String key : programmingLanguages.keySet()){
                String lineToWrite = key;
                lineToWrite += " is an IEEE top ten langauge with a spectrum of ";
                lineToWrite += programmingLanguages.get(key);
                pw.println(lineToWrite);
            }
        }
    }

    public boolean fixErrors() throws FileNotFoundException {
        // to fix the errors, I need to read each line of the original
        // write to a different file
        File output = new File("programming_languages_fixed.txt");
        File input = new File("programming_languages.txt");
        try(Scanner fileReader = new Scanner(input);
            PrintWriter pw = new PrintWriter(output)){
            // read through the input file
            while(fileReader.hasNextLine()){
                // grab the line
                String line = fileReader.nextLine();
                line = line.replace("langauge","language");
                // write that to the new file
                pw.println(line);
            }
        }
        // let's make it look like we changed the original file
        input.delete();
        output.renameTo(input);
        return true;
    }
}
