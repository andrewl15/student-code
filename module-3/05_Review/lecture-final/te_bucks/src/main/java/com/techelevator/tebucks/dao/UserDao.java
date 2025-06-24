package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.RegisterUserDto;
import com.techelevator.tebucks.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();
    User getUserByUsername(String username);
    User getUserByID(int id);
    User createUser(RegisterUserDto user);
    List<User> getAllUsers();
    List<User> getOtherUsers(int userExcludeID);
    int findIdByUsername(String username);
}

