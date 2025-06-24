package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.model.User;
import com.techelevator.tebucks.services.Util;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserDao userDao;
    public UsersController(UserDao userDao){
        this.userDao = userDao;
    }

    @GetMapping()
    public List<User> getUsers(Principal principal){
        try{
            return userDao.getOtherUsers(Util.getCurrentUserId(principal,userDao));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
