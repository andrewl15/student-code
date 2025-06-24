package com.techelevator;

import com.techelevator.dao.DAO;
import com.techelevator.dao.FileDAO;
import com.techelevator.exceptions.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class VirtualVendingMachine {
    // Set a final for the number of items in a slot
    // make it static so it can be used in the DAO
    public static final int NUMBER_IN_SLOT = 5;

    private DAO dataAccessObject = new FileDAO();
    /// <summary>
    /// This hold the balance
    /// </summary>
    private BigDecimal currentBalance = BigDecimal.ZERO;
    /// <summary>
    /// Public getter to return the current balance in the machine
    /// </summary>
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    /// <summary>
    /// This stores the inventory. The string will be the slot id and the value will hold a queue of products.
    /// </summary>
    private Map<String, Queue<VendingMachineItem>> inventory;

    public Map<String, Queue<VendingMachineItem>> getInventory() {
        return inventory;
    }
    /**
     * This holds information for the sales report
     */
    private Map<String,Integer> rawSalesData = new HashMap<>();
    private BigDecimal totalSales = BigDecimal.ZERO;
    /// <summary>
    /// Default Constructor for the machine. The constructor loads the files and populates the inventory. It also resets the sales log
    /// </summary>
    public VirtualVendingMachine() throws VendingMachineException {
        // load it up!
        inventory = dataAccessObject.loadMachine();
        // initialize the sales report
        initializeSalesReport(inventory);
        // reset the log
        dataAccessObject.resetLog();
    }
    /**
     * This will initialize the salesReport data so each item will have a record
     * @param inventory
     */
    private void initializeSalesReport(Map<String, Queue<VendingMachineItem>> inventory){
        // initialize the HashMap
        rawSalesData = new TreeMap<>();
        // loop through the keys in the inventory
        for(String key : inventory.keySet()){
            // get the Queue from the inventory with the key
            // then use peek to get access to the name
            String salesKey = inventory.get(key).peek().getItemName();
            // add that item to the HashMap
            rawSalesData.put(salesKey,0);
        }
    }
    /// <summary>
    /// Public method to add money to the vending machine's balance
    /// </summary>
    /// <param name="dollars">Amount of dollars to add.</param>
    /// <returns>The machine balance after the money is added</returns>
    public  BigDecimal feedMoney(int dollars) throws InvalidDollarAmount {
        // check for negative values. Need to check here in case this class is used elsewhere
        if(dollars < 0) {
            throw new InvalidDollarAmount();
        }
        // create a BigDecimal out of input
        BigDecimal dollarsIn = new BigDecimal(dollars);
        dollarsIn = dollarsIn.setScale(2, RoundingMode.FLOOR);
        currentBalance = currentBalance.add(dollarsIn);
        dataAccessObject.logTransaction("FEED MONEY", dollarsIn, currentBalance);
        return getCurrentBalance();
    }
    /// <summary>
    /// Part of the vending method, this gets an item from the specified slot
    /// </summary>
    /// <param name="slotID">The slot identifier</param>
    /// <returns>The next item in the specified slot</returns>
    private VendingMachineItem getItemOutOfSlot(String slotID) throws InvalidSlotIDException {
        // Check to see if the slotID is valid, if it is get the item, if it isn't return null;
        // can't use ternary here since the false case throws and exception
        if(inventory.containsKey(slotID)) {
            return inventory.get(slotID).remove();
        } else {
            throw new InvalidSlotIDException();
        }
    }
    /// <summary>
    /// Returns how many items are in a give slot
    /// </summary>
    /// <param name="slotID">The slot identifier</param>
    /// <returns>How many items are in the slot</returns>
    private int getQuantityOfSlot(String slotID) {
        // Check to see if the slotID is valid, if it is get the count, if it isn't return 0;
        return inventory.containsKey(slotID) ? inventory.get(slotID).size() : 0;
    }
    /// <summary>
    /// Get the cost of the next item in the specified slot
    /// </summary>
    /// <param name="slotID">The slot identifier</param>
    /// <returns>The price of the next item in the slot</returns>
    private BigDecimal getCostOfSlot(String slotID) {
        // Check to see if the slotID is valid and there is something in the slot, if it is get the price, if it isn't return 0;
        return inventory.containsKey(slotID) ? inventory.get(slotID).peek().getPrice() : BigDecimal.ZERO;
    }

    /**
     * This will store the item bought in the MAP
     * @param itemBought A VendingMachineItem purchased
     */
    private void logForSalesReport(VendingMachineItem itemBought){
        // log the purchase for sales report
        if(rawSalesData.containsKey(itemBought.getItemName())){
            int currentCount = rawSalesData.get(itemBought.getItemName());
            rawSalesData.put(itemBought.getItemName(),currentCount+1);
        } else {
            rawSalesData.put(itemBought.getItemName(),1);
        }
        totalSales = totalSales.add(itemBought.getPrice());
    }
    /// <summary>
    /// Purchase an item from the machine
    /// </summary>
    /// <param name="slotID">The slot identifier</param>
    /// <returns>The vended product</returns>
    public VendingMachineItem purchase(String slotID) throws InvalidSlotIDException, InsufficientFundsException, OutOfStockException {
        // Translate the slot to all uppercase for comparison
        slotID = slotID.toUpperCase();
        // check if there is anything at the slot
        if(getQuantityOfSlot(slotID) > 0) {
            // is there enough money?
            if(currentBalance.compareTo(getCostOfSlot(slotID)) >= 0) {
                // decrement the balance by the cost of the item
                currentBalance = currentBalance.subtract(getCostOfSlot(slotID));
                // get the item from the slot
                VendingMachineItem purchasedItem = getItemOutOfSlot(slotID);
                // log the purchase
                dataAccessObject.logTransaction(purchasedItem.getItemName() + " " + slotID, purchasedItem.getPrice(), getCurrentBalance());
                // store for sales report writing
                logForSalesReport(purchasedItem);
                // return the item to the purchaser
                return purchasedItem;
            }
            // If not enough money, through InsufficientFundsException
            throw new InsufficientFundsException();
        }
        // if there is nothing in slot, throw and OutOfStockException
        throw new OutOfStockException();
    }
    /// <summary>
    /// Method to return change from the vending machine
    /// </summary>
    /// <returns>A changedrawer object containing the change breakdown</returns>
    public ChangeDrawer returnChange()
    {
        // Log the transaction. Do it here since the CurrentBalance will change after MakeChange is called
        dataAccessObject.logTransaction("GIVE CHANGE", currentBalance, BigDecimal.ZERO);

        ChangeDrawer changeDrawer = new ChangeDrawer();
        changeDrawer.makeChange(currentBalance);
        return changeDrawer;
    }
    public void createSalesReport(){
        dataAccessObject.writeSalesReport(rawSalesData,totalSales);
    }
}
