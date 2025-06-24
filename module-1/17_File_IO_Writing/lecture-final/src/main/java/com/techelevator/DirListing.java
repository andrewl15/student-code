package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class DirListing {
    public void makeDirList() throws IOException {
        String[] pathNames;
        File inputDir = new File("c:\\");
        if(inputDir.exists() && !inputDir.isFile()){
            // let's get a list of the directories
            pathNames = inputDir.list();
            File outputFile = new File(".","CDriveInfo.txt");
            outputFile.createNewFile();

            try(PrintWriter pw = new PrintWriter(outputFile)){
                for(String item : pathNames){
                    pw.println(item);
                    // manual flush the buffer to commit to the file
                    // this is expensive
                    // only do this with mission critical information
                    // pw.flush();
                }
            } catch (Exception e){
                System.out.println("Ooops");
            }
        }

    }
}
