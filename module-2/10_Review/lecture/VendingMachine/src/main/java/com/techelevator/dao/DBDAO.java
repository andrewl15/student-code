package com.techelevator.dao;

import com.techelevator.*;
import com.techelevator.exceptions.VendingMachineException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.pool2.proxy.JdkProxySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class DBDAO implements DAO{

    private JdbcTemplate jdbcTemplate;

    public DBDAO(){
        // datasource for DB
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/VVM");
        dataSource.setPassword("postgres1");
        dataSource.setUsername("postgres");
        // jdbcTemplate initialized
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public Map<String, Queue<VendingMachineItem>> loadMachine() throws VendingMachineException, NumberFormatException {
        Map<String,Queue<VendingMachineItem>> output = new TreeMap<>();

        String sql = "select slot_id, item_name, item_price, item_type from items where is_active order by slot_id;";
        try{
            SqlRowSet data = jdbcTemplate.queryForRowSet(sql);
            while(data.next()){
                // doing this to leverage code from queue method
                String[] temp = new String[4];
                temp[0] = data.getString("slot_id");
                temp[1] = data.getString("item_name");
                temp[2] = data.getString("item_price");
                temp[3] = data.getString("item_type");
                output.put(temp[0],buildQueue(temp));
            }
        } catch(Exception e){
            throw new VendingMachineException("Cannot get the data.");
        }
        initSaleTable(output);
        return output;
    }

    @Override
    public void logTransaction(String action, BigDecimal txAmount, BigDecimal currentBalance) {
        LocalDateTime current  = LocalDateTime.now();

        String sql = "insert into tx_log(date_time, action, starting_value, ending_value)" +
                "values(?,?,?,?);";
        try{
            jdbcTemplate.update(sql, current, action, txAmount, currentBalance);
        } catch(Exception e){

        }
    }

    @Override
    public void writeSalesReport(Map<String, Integer> salesData, BigDecimal totalSales) {
        String sql = "update sales set quantity = ? where item_name = ?;";
        for(String key : salesData.keySet()){
            try{
                jdbcTemplate.update(sql, salesData.get(key), key);
            } catch(Exception e){}
        }
    }

    @Override
    public void resetLog() {

    }

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

    private void initSaleTable(Map<String,Queue<VendingMachineItem>> inventory){
        String sql = "insert into sales(item_name, quantity) values (?,?);";

        for (String key : inventory.keySet()) {
            try{
                String item_name = inventory.get(key).peek().getItemName();
                jdbcTemplate.update(sql, item_name, 0);
            } catch(Exception e){

            }

        }

    }
}
