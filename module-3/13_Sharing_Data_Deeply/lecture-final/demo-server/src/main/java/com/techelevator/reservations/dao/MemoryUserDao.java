package com.techelevator.reservations.dao;

import com.techelevator.reservations.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryUserDao implements UserDao {

    private static final List<User> users = new ArrayList<>();

    public MemoryUserDao() {
        if (users.isEmpty()) {
            loadUserData();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                user = u;
            }
        }
        return user;
    }

    private void loadUserData() {
		users.add(new User(1, "admin", "$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi", "ROLE_ADMIN"));
    }

}
