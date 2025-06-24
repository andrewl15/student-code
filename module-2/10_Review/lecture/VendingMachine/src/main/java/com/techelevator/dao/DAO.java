package com.techelevator.dao;

import com.techelevator.VendingMachineItem;
import com.techelevator.exceptions.VendingMachineException;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Queue;

public interface DAO {
    Map<String, Queue<VendingMachineItem>> loadMachine() throws VendingMachineException, NumberFormatException;
    void logTransaction(String action, BigDecimal txAmount, BigDecimal currentBalance);
    void writeSalesReport(Map<String,Integer> salesData,BigDecimal totalSales);
    void resetLog();
}
