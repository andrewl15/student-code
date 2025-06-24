package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.model.RegisterUserDto;
import com.techelevator.tebucks.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT user_id, username, password_hash, first_name, last_name, email FROM users WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        User user = null;
        String sql = "SELECT user_id, username, password_hash, first_name, last_name, email FROM users WHERE username = LOWER(TRIM(?));";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if (rowSet.next()) {
                user = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }
    @Override
    public User getUserByID(int id) {
        User user = null;
        String sql = "SELECT user_id, username, password_hash, first_name, last_name, email FROM users WHERE user_id = ?;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
            if (rowSet.next()) {
                user = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User createUser(RegisterUserDto user) {
        // create user
        String sql = "INSERT INTO users (username, password_hash, first_name, last_name, email) VALUES (TRIM(?), ?, ?, ?, ?) RETURNING user_id";
        String passwordHash = new BCryptPasswordEncoder().encode(user.getPassword());
        try {
            Integer newUserId = jdbcTemplate.queryForObject(sql, int.class,
                    user.getUsername(),
                    passwordHash,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail());

            if (newUserId == null) {
                throw new DaoException("Could not create user");
            }

            // TODO Remove this bad code!! Bad Tom.
            sql = "INSERT INTO account (user_id,balance) VALUES(?,1000);";
            jdbcTemplate.update(sql,newUserId);
            return getUserById(newUserId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return getOtherUsers(0);
    }

    @Override
    public List<User> getOtherUsers(int userExcludeID) {
        List<User> output = new ArrayList<>();
        String sql = "SELECT * from users where user_id != ?";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,userExcludeID);
            while(result.next()){
                User temp = mapRowToUser(result);
                output.add(temp);
            }
        } catch (DataAccessException de){
            throw new DaoException("Can't get users.", de);
        }
        return output;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, first_name, last_name, email, password_hash FROM tebucks_user";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("SELECT user_id FROM users WHERE username = ?", int.class, username);
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
}
