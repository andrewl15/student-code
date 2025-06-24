package com.techelevator.dao;

import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcParkDao implements ParkDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcParkDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getParkCount() {
        // need an output variable
        int output = 0;
        // need a sql statement to get the park count
        String sql = "select count(*) as count from park;";
        // pass that statement to the client (jdbcTemplate)
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        // see if I got back some data
        if(results.next()){
            // if I did, convert it and store it in the output variable
            output = results.getInt("count");
        }

        // return the output variable
        return output;
    }
    
    @Override
    public LocalDate getOldestParkDate() {
        LocalDate output = null;
        String sql = "select MIN(date_established) as oldest_park from park;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if(results.next()){
            output = results.getDate("oldest_park").toLocalDate();
        }
        return output;
    }
    
    @Override
    public double getAverageParkArea() {
        double output = 0.0;
        String sql = "select avg(area) as average_area from park;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if(results.next()){
            output = results.getDouble("average_area");
        }
        return output;
    }
    
    @Override
    public List<String> getParkNames() {
        return new ArrayList<>();
    }
    
    @Override
    public Park getRandomPark() {
        return new Park();
    }

    @Override
    public List<Park> getParksWithCamping() {
        List<Park> output = new ArrayList<>();
        String sql = "select park_id,park_name,date_established,area,has_camping " +
                "from park " +
                "where has_camping = true;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            // convert that row into a Park model
            Park temp = getPark(results);
            output.add(temp);
        }
        return output;
    }

    private Park getPark(SqlRowSet results) {
        Park temp = new Park();
        temp.setParkId(results.getInt("park_id"));
        temp.setParkName(results.getString("park_name"));
        temp.setDateEstablished(results.getDate("date_established").toLocalDate());
        temp.setArea(results.getDouble("area"));
        temp.setHasCamping(results.getBoolean("has_camping"));
        return temp;
    }

    @Override
    public Park getParkById(int parkId) {
        Park output = null;
        String sql = "select park_id,park_name,date_established,area,has_camping " +
                "from park where park_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,parkId);
        if(results.next()){
            output = getPark(results);
        }
        return output;
    }

    @Override
    public List<Park> getParksByState(String stateAbbreviation) {
        List<Park> output = new ArrayList<>();
        String sql = "select park.park_id,park_name,date_established,area,has_camping from park " +
                "JOIN park_state as ps on ps.park_id = park.park_id " +
                "where state_abbreviation = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,stateAbbreviation);
        while (results.next()){
            output.add(getPark(results));
        }
        return output;
    }

    // this is just an example not in the CLI
    public List<Park> getParksForStateOptionalCamping(String state_abbreviation, boolean hasCamping){
        List<Park> output = new ArrayList<>();
        String sql = "select park.park_id,park_name,date_established,area,has_camping from park " +
                "JOIN park_state as ps on ps.park_id = park.park_id " +
                "where state_abbreviation = ? AND has_camping = ?;";
        //                                                    First ?           Second ?
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,state_abbreviation,hasCamping);
        while(results.next()){
            output.add(getPark(results));
        }
        return output;
    }
    @Override
    public List<Park> getParksByName(String name, boolean useWildCard) {
        List<Park> output = new ArrayList<>();
        String sql = "select park.park_id,park_name,date_established,area,has_camping from park " +
                "where park_name ilike ?;";

        // if useWildCard, add the % to the start and end of name
        if(useWildCard){
            name = "%" + name + "%";
        }
        // if not, don't do anything
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,name);
        while(results.next()){
            output.add(getPark(results));
        }
        return output;
    }

    private Park mapRowToPark(SqlRowSet rowSet) {
        return new Park();
    }
}
