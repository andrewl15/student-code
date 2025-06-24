package com.techelevator.favoritemovieserver.dao;

import com.techelevator.favoritemovieserver.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

}
