package com.techelevator.favoritemovieserver.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.favoritemovieserver.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

import com.techelevator.favoritemovieserver.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT user_id, username, password_hash, role FROM users WHERE user_id = ?;";
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
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

		// Intentionally excluding password_hash - Not a good idea to allow mass selection of user password data (even if hashed).
        String sql = "SELECT user_id, username, role FROM users ORDER BY username;";
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
    public User getUserByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        User user = null;
        String sql = "SELECT user_id, username, password_hash, role FROM users WHERE username = LOWER(TRIM(?));";
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

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
		// Password column is not always included
		if (hasColumnName(rs, "password_hash")) {
			user.setPassword(rs.getString("password_hash"));
		}
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        return user;
    }

	/*
	 * Helper method to determine if a SqlRowSet contains a particular column.
	 * Used by the mapRowToUser method to check if the password_hash is included.
	 */
	private boolean hasColumnName(SqlRowSet rs, String columnName) {
		SqlRowSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();

		// resultSet column indexes start from 1
		for (int i = 1; i < numberOfColumns + 1; i++) {
			String currentColumn = rsMetaData.getColumnName(i);
			if (columnName.equalsIgnoreCase(currentColumn)) {
				return true;
			}
		}
		return false;
	}
}
