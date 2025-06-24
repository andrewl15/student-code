package com.techelevator.tebucks.model;

import java.math.BigDecimal;

public class Account {
    private int accountID;
    private int user_id;

    public Account() {

    }

    public Account(int accountID, int user_id, BigDecimal balance) {
        this.accountID = accountID;
        this.user_id = user_id;
        this.balance = balance;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getUser_id() {
        return user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    private BigDecimal balance;
}
