package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;

public interface AccountDAO {
    Account GetAccountByUserID(int userId);

}
