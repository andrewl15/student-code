package com.techelevator;

import java.io.*;
import java.time.LocalDateTime;

public class Log {
    public static void writeLog(String reason) {
        String directoryName = "data";
        String fileName = "log.txt";
        File logDirectory = new File(directoryName);
        // does it exist?
        if (!logDirectory.exists()) {
            // create it
            logDirectory.mkdir();
        }
        String temp = directoryName + "\\" + fileName;
        try (FileOutputStream outputStream = new FileOutputStream(temp, true);
             PrintWriter pw = new PrintWriter(outputStream)) {
            pw.println(LocalDateTime.now() + " -- " + reason);
            pw.println();
        } catch (Exception e) {
            // do nothing
        }
    }
}
