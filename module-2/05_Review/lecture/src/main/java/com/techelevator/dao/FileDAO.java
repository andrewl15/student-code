package com.techelevator.dao;

import com.techelevator.*;
import com.techelevator.exceptions.VendingMachineException;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileDAO implements DAO {


    // Two errors can be thrown. One, if the file can't be found, the other if BigDecimal can't process the string in VendingMachineItem
    public Map<String, Queue<VendingMachineItem>> loadMachine() throws VendingMachineException, NumberFormatException {
        // Declare a local variable to hold the generated Map
        // Use TreeMap so items are sorted by key
        Map<String, Queue<VendingMachineItem>> result = new TreeMap<>();
        // Create the file object
        File inventoryInput = new File("vendingmachine.csv");
        // Does the file exist?
        if (inventoryInput.exists() && inventoryInput.isFile()) {
            try {
                // read in the file and populate the dictionary
                Scanner reader = new Scanner(inventoryInput);
                while (reader.hasNextLine()) {
                    // Read the line from the inventory file
                    String line = reader.nextLine();
                    // The items are separated by the | character, split each value into an item in an array
                    String[] item = line.split("\\|");
                    // Add to the Map, use the helper function to create the Queue
                    result.put(item[0].toUpperCase(), buildQueue(item));
                }
            } catch (FileNotFoundException e) {
                throw new VendingMachineException("Can't find data");
            }
        } else {
            throw new VendingMachineException("Cannot locate data " + inventoryInput.getName());
        }
        return result;
    }

    @Override
    public void logTransaction(String action, BigDecimal txAmount, BigDecimal currentBalance) {
        // Create the File object
        File logFile = new File("Log.txt");
        // Try with resources block since we're doing something dangerous: writing a file
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            // Make sure we are the right scale
            txAmount = txAmount.setScale(2, RoundingMode.FLOOR);
            currentBalance = currentBalance.setScale(2, RoundingMode.FLOOR);
            // Get the current Date and Time in the proper format
            String dateTimeForOutput = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm:ss a"));
            // write to the file
            writer.println(dateTimeForOutput + " " + action + " $" + txAmount + " $" + currentBalance);
        } catch (IOException e) {
            // do nothing if error
        }
    }

    @Override
    public void writeSalesReport(Map<String, Integer> salesData, BigDecimal totalSales) {
        // Create the file name
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM_dd_yyyy_h-mm-ss-a"));
        // Create the File object
        File logFile = new File(fileName + ".txt");
        // Try with resources block since we're doing something dangerous: writing a file
        try (PrintWriter writer = new PrintWriter(logFile)) {
            // Loop through the dictionary, printing the information
            for (String key : salesData.keySet()) {
                String lineToPrint = key + "|" + salesData.get(key);
                writer.println(lineToPrint);
            }
            writer.println("Total Sales: $" + totalSales.toString());
        } catch (IOException e) {
            // do nothing if error
            // System.out.println(e.getMessage());
        }

    }

    @Override
    public void resetLog() {
        // just really need to delete the log file
        File logFile = new File("Log.txt");
        try {
            logFile.delete();
        } catch (Exception e) {
            // don't need to do anything
        }

    }

    /**
     * Helper function to build the queue
     * @param item Array of the parts of a vending item
     * @return The completed queue
     */
    private Queue<VendingMachineItem> buildQueue(String[] item){
        // Set up a local queue that represents the items in a slot
        Queue<VendingMachineItem> queue = new LinkedList<>();
        // Check the type of item in a slot based on the input files
        switch (item[3].toLowerCase()) {
            case "chip":
                // add the appropriate number and type to the queue
                for (int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++) {
                    // declare an instantiate the product item
                    ChipItem chipItem = new ChipItem(item[1], item[2]);
                    // Add to the queue
                    queue.add(chipItem);
                }
                break;
            case "drink":
                // add the appropriate number and type to the queue
                for (int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++) {
                    // declare an instantiate the product item
                    BeverageItem beverageItem = new BeverageItem(item[1], item[2]);
                    // Add to the queue
                    queue.add(beverageItem);
                }
                break;
            case "candy":
                // add the appropriate number and type to the queue
                for (int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++) {
                    // declare an instantiate the product item
                    CandyItem candyItem = new CandyItem(item[1], item[2]);
                    // Add to the queue
                    queue.add(candyItem);
                }
                break;
            case "gum":
                // add the appropriate number and type to the queue
                for (int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++) {
                    // declare an instantiate the product item
                    GumItem gumItem = new GumItem(item[1], item[2]);
                    // Add to the queue
                    queue.add(gumItem);
                }
                break;
            default:
                break;
        }
        return queue;
    }
}
