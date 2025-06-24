package com.techelevator.dao;

import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        // pass that statement to the client
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
        String sql = "select min(date_established) as oldest_park from park;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if(results.next()){
            output = Objects.requireNonNull(results.getDate("oldest_park")).toLocalDate();
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
        return 0.0;
    }
    
    @Override
    public List<String> getParkNames() {
        List<String> output = new ArrayList<>();
        return output;
    }
    
    @Override
    public Park getRandomPark() {
        return new Park();
    }

    @Override
    public List<Park> getParksWithCamping() {
        List<Park> output = new ArrayList<>();
        String sql = "select park_id, park_name, date_established, area, has_camping from park where has_camping;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            //convert row into park
            Park temp = getPark(results);
            output.add(temp);
        }
        return output;
    }

    private Park getPark(SqlRowSet results) {
        Park temp = new Park();
        temp.setParkId(results.getInt("park_id"));
        temp.setParkName(results.getNString("park_name"));
        temp.setDateEstablished(Objects.requireNonNull(results.getDate("date_established")).toLocalDate());
        temp.setArea(results.getDouble("area"));
        temp.setHasCamping(results.getBoolean("has_camping"));
        return temp;
    }

    @Override
    public Park getParkById(int parkId) {
        Park output = null;
        String sql = "select park_id, park_name, date_established, area, has_camping from park where park_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        if(results.next()){
            output = getPark(results);
        }
        return output;
    }

    @Override
    public List<Park> getParksByState(String stateAbbreviation) {
        List<Park> output = new ArrayList<>();
        String sql = "select park.park_id, park_name, date_established, area, has_camping from park \n" +
                "join park_state as ps on ps.park_id = park.park_id\n" +
                "where state_abbreviation = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, stateAbbreviation);
        while(results.next()){
            output.add(getPark(results));
        }
        return output;
    }

    @Override
    public List<Park> getParksByName(String name, boolean useWildCard) {
        List<Park> output = new ArrayList<>();
        String sql = "select park.park_id, park_name, date_established, area, has_camping from park\n" +
                "where park_name ilike ?;";
        // if useWildCard, add the % to the start and back
        // if not dont do anything
        if(useWildCard){
            name = "%" + name + "%";
        }
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
        while(results.next()){
            output.add(getPark(results));
        }
        return output;
    }

    public List<Park> getParksForStateOptionalCamping(String state_abbreviation, boolean hasCamping){
        List<Park> output = new ArrayList<>();
        String sql = "select park.park_id, park_name, date_established, area, has_camping from park \n" +
                "                join park_state as ps on ps.park_id = park.park_id \n" +
                "                where state_abbreviation = ? and has_camping = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, state_abbreviation, hasCamping);
        while(results.next()){
            output.add(getPark(results));
        }
        return output;
    }

    private Park mapRowToPark(SqlRowSet rowSet) {
        return new Park();
    }
}
