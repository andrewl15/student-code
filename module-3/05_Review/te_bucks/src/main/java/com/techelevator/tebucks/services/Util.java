package com.techelevator.tebucks.services;

import com.techelevator.tebucks.dao.UserDao;

import java.security.Principal;

public class Util {
    public static int getCurrentUserId(Principal principal, UserDao userDao) {
        try{
            return userDao.findIdByUsername(principal.getName());
        } catch (Exception e){
            return -1;
        }

    }

}
