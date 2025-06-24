package com.techelevator.dao;

import com.techelevator.*;
import com.techelevator.exceptions.VendingMachineException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class DBDAO implements DAO {

    private JdbcTemplate jdbcTemplate;

    public DBDAO() {
        // datasource for the db
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/VVM");
        dataSource.setPassword("postgres1");
        dataSource.setUsername("postgres");
        // jdbctemplate initialized
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, Queue<VendingMachineItem>> loadMachine() throws VendingMachineException, NumberFormatException {
        Map<String, Queue<VendingMachineItem>> output = new TreeMap<>();

        // get the rows from the database
        String sql = "select slotid,name,price,type from items where isactive order by slotid;";
        // create an String array out of each row
        try {
            SqlRowSet data = jdbcTemplate.queryForRowSet(sql);
            while (data.next()) {
                // doing this to leverage code I wrote for FileDAO
                String[] temp = new String[4];
                temp[0] = data.getString("slotid");
                temp[1] = data.getString("name");
                temp[2] = data.getString("price");
                temp[3] = data.getString("type");
                // call the helper method to create the queue
                // add to the map
                output.put(temp[0], buildQueue(temp));
            }
        } catch (Exception e) {
            throw new VendingMachineException("Cannot get the data.");
        }
        initSalesTable(output);
        return output;
    }

    @Override
    public void logTransaction(String action, BigDecimal txAmount, BigDecimal currentBalance) {
        LocalDateTime current = LocalDateTime.now();
        String sql = "insert into txlog(date_time,action,starting_value,ending_value) " +
                "VALUES(?,?,?,?);";
        try {
            jdbcTemplate.update(sql, current, action, txAmount, currentBalance);

        } catch (Exception e) {
            String foo = e.getMessage();
        }
    }

    @Override
    public void writeSalesReport(Map<String, Integer> salesData, BigDecimal totalSales) {
        String sql = "UPDATE sales set quantity=? where item_name=?";
        for(String key : salesData.keySet()){
            try{
                jdbcTemplate.update(sql,salesData.get(key),key);
            } catch(Exception e){}
        }
    }

    @Override
    public void resetLog() {

    }

    private Queue<VendingMachineItem> buildQueue(String[] item) {
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

    private void initSalesTable(Map<String, Queue<VendingMachineItem>> inventory) {
        String sql = "INSERT INTO sales(item_name,quantity) VALUES(?,?)";

        for (String key : inventory.keySet()) {
            try {
                String item_name = inventory.get(key).peek().getItemName();
                jdbcTemplate.update(sql, item_name, 0);
            } catch (Exception e) {
            }
        }
    }
}
