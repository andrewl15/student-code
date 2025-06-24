package com.techelevator.dao;

import com.techelevator.*;
import com.techelevator.exceptions.VendingMachineException;
import com.techelevator.model.VMItem;
import com.techelevator.model.VMSlot;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class APIDAO implements DAO{
    private final String BASE_API="https://vendingmachinedata.azurewebsites.net/api";
    private final RestClient restClient = RestClient.create(BASE_API);

    @Override
    public Map<String, Queue<VendingMachineItem>> loadMachine() throws VendingMachineException, NumberFormatException {
        // get the data
        VMSlot[] webData = restClient.get()
                .uri("/vendingmachine/items")
                .retrieve()
                .body(VMSlot[].class);

        // building our treeMap to keep the keys in order
        Map<String,Queue<VendingMachineItem>> inventory = new TreeMap<>();
        // loop through webData
        for(VMSlot slotData : webData){
            // pull out the slotid and make that the key
            inventory.put(slotData.getSlotID(),makeQueueForItem(slotData.getItem()));
            // create a Queue of VendingMachineItems for the number specified
            // add that as the value
        }

        return inventory;
    }

    @Override
    public void logTransaction(String action, BigDecimal txAmount, BigDecimal currentBalance) {

    }

    @Override
    public void writeSalesReport(Map<String, Integer> salesData, BigDecimal totalSales) {

    }

    @Override
    public void resetLog() {

    }

    private Queue<VendingMachineItem> makeQueueForItem(VMItem item){
        String desc = item.getDescription();
        String price = item.getPrice();
        String snackType = item.getSnackType().toLowerCase().trim();

        Queue<VendingMachineItem> output = new LinkedList<>();

        switch (snackType){
            case "chip":
                for(int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++){
                    ChipItem temp = new ChipItem(desc,price);
                    output.add(temp);
                }
                break;
            case "drink":
                for(int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++){
                    BeverageItem temp = new BeverageItem(desc,price);
                    output.add(temp);
                }
                break;
            case "gum":
                for(int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++){
                    GumItem temp = new GumItem(desc,price);
                    output.add(temp);
                }
                break;
            case "candy":
                for(int i = 0; i < VirtualVendingMachine.NUMBER_IN_SLOT; i++){
                    CandyItem temp = new CandyItem(desc,price);
                    output.add(temp);
                }
                break;
            default:
                break;
        }
        return output;
    }
 }
