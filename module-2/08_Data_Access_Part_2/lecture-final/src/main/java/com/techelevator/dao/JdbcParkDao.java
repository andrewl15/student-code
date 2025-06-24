package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Park;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

public class JdbcParkDao implements ParkDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcParkDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getParkCount() {
        int parkCount = 0;
        String sql = "SELECT COUNT(*) AS count FROM park;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()) {
                parkCount = results.getInt("count");
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to access the information", e);
        } catch (BadSqlGrammarException e){
            throw new DaoException("Unable to get park information",e);
        } catch (Exception e){
            throw new DaoException("I have no idea what went wrong.",e);
        }

        return parkCount;
    }
    
    @Override
    public List<String> getParkNames() {
        List<String> parkNames = new ArrayList<>();
        String sql = "SELECT park_name FROM park ORDER by park_name ASC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            parkNames.add(results.getString("park_name"));
        }
        return parkNames;
    }
    
    @Override
    public Park getRandomPark() {
        Park park = null;
        String sql = "SELECT park_id, park_name, date_established, area, has_camping " +
                     "FROM park ORDER BY RANDOM() LIMIT 1;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()) {
            park = mapRowToPark(results);
        }
        return park;
    }

    @Override
    public List<Park> getParksWithCamping() {
        List<Park> parks = new ArrayList<>();
        String sql = "SELECT park_id, park_name, date_established, area, has_camping " +
                     "FROM park " +
                     "WHERE has_camping = true;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            parks.add(mapRowToPark(results));
        }
        return parks;
    }

    @Override
    public Park getParkById(int parkId) {
        Park park = null;
        String sql = "SELECT park_id, park_name, date_established, area, has_camping " +
                     "FROM park " +
                     "WHERE park_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        if (results.next()) {
            park = mapRowToPark(results);
        }
        return park;
    }

    @Override
    public List<Park> getParksByState(String stateAbbreviation) {
        List<Park> parks = new ArrayList<>();
        String sql = "SELECT p.park_id, park_name, date_established, area, has_camping " +
                     "FROM park p " +
                     "JOIN park_state ps ON p.park_id = ps.park_id " +
                     "WHERE state_abbreviation = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, stateAbbreviation);
        while (results.next()) {
            parks.add(mapRowToPark(results));
        }
        return parks;
    }

    @Override
    public Park createPark(Park park) {
        Park output = null;
        String sql = "insert into park(park_name,date_established,area,has_camping) " +
                "VALUES(?,?,?,?) " +
                "RETURNING park_id;";
        try{
            int newParkId = jdbcTemplate.queryForObject(sql,int.class,
                    park.getParkName(),park.getDateEstablished(),park.getArea(),park.getHasCamping());
            output = getParkById(newParkId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Can't get a reference to the source of data.",e);
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Data breaks the rules.",e);
        }
        return output;
    }

    @Override
    public Park updatePark(Park park) {
        Park updatedPark = null;
        String sql = "update park \n" +
                "set park_name = ?,\n" +
                "date_established = ?,\n" +
                "area = ?,\n" +
                "has_camping = ?\n" +
                "WHERE park_id = ?;";
        try{
            int numberOfRows = jdbcTemplate.update(sql,
                    park.getParkName(),park.getDateEstablished(),park.getArea(),
                    park.getHasCamping(),park.getParkId());
            if(numberOfRows == 0){
                throw new DaoException("No parks were updated");
            } else {
                updatedPark = getParkById(park.getParkId());
            }
        } catch (Exception e) {
            throw new DaoException("Cannot update park",e);
        }
        return updatedPark;
    }

    @Override
    public int deleteParkById(int parkId) {
        int numberOfRowsImpacted = 0;
        // two delete statements
        // one for park_state
        String sqlParkState = "delete from park_state where park_id = ?;";
        // one for park
        String sqlPark = "delete from park where park_id = ?;";
        try{
            jdbcTemplate.update(sqlParkState,parkId);
            // I care how many parks where deleted
            numberOfRowsImpacted = jdbcTemplate.update(sqlPark,parkId);
        } catch(Exception e){
            throw new DaoException("Cannot remove park for some reason.",e);
        }
        return numberOfRowsImpacted;
    }

    @Override
    public void linkParkState(int parkId, String stateAbbreviation) {
        String sql = "insert into park_state VALUES(?,?);";
        try{
            jdbcTemplate.update(sql,parkId,stateAbbreviation);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Can't get at the data",e);
        }

    }

    private Park mapRowToPark(SqlRowSet rowSet) {
        Park park = new Park();
        park.setParkId(rowSet.getInt("park_id"));
        park.setParkName(rowSet.getString("park_name"));
        park.setDateEstablished(rowSet.getDate("date_established").toLocalDate());
        park.setArea(rowSet.getDouble("area"));
        park.setHasCamping(rowSet.getBoolean("has_camping"));
        return park;
    }
}
