package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class jdbcAccountDao implements AccountDAO{
    private final JdbcTemplate jdbcTemplate;

    public jdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account GetAccountByUserID(int userId) {
        Account output = null;
        String sql = "select * from account where user_id=?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,userId);
        if(result.next()) {
            output = mapRowToAccount(result);
        }
        return output;
    }
    private Account mapRowToAccount(SqlRowSet rs) {
        return new Account(rs.getInt("account_id"), rs.getInt("user_id"), rs.getBigDecimal("balance"));
    }
}
