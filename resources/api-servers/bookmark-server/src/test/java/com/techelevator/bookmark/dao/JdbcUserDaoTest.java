package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Authority;
import com.techelevator.bookmark.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoTest extends BaseDaoTest {

    private static final User USER_1 = new User(101, "user1", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC","ROLE_USER", "User 1 - Jo Tagolia", "Test Image URL", "Test Bio");
    private static final User USER_2 = new User(102, "user2", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "ROLE_USER", "User 2", null, null);
    private static final User USER_ADMIN = new User(100, "admin", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "ROLE_ADMIN", null, null, null);
    // Get should sort Users by username
    private static final List<User> ALL_USERS = Arrays.asList(new User[] {USER_ADMIN, USER_1, USER_2});

    private JdbcUserDao dao;

    @BeforeEach
    public void setup() {
        dao = new JdbcUserDao(dataSource);
    }

    @Test
    public void getUsers_returns_correct_list_sorted_by_name() {
        List<User> result = dao.getUsers();
        assertNotNull(result, "getUsers returned null");
        assertUserListsMatch(ALL_USERS, result, "getUsers returned wrong or partial data");
    }

    @Test
    public void getUserById_returns_correct_user() {
        User result = dao.getUserById(USER_1.getId());
        assertNotNull(result, "getUserById returned null");
        assertUsersMatch(USER_1, result, "getUserById returned wrong or partial data");
    }

    @Test
    public void getUserById_returns_null_when_id_not_found() {
        User result = dao.getUserById(9999);
        assertNull(result, "getUserById failed to return null for id not in database");
    }

    @Test
    public void createUser_returns_user_with_id_and_correct_values() {
        User newUser = new User(0, "new_user", "password", "ROLE_TEST", null, null, null);
        User createdUser = dao.createUser(newUser.getUsername(), "password", "TEST");

        assertNotNull(createdUser, "createUser returned null");

        int newId = createdUser.getId();
        assertTrue(newId > 0, "createUser returned a user without an id");

        newUser.setId(newId);
        assertUsersMatch(newUser, createdUser, "createUser returned a user with wrong or partial data");
    }

    @Test
    public void createUser_has_expected_values_when_retrieved() {
        User newUser = new User(0, "new_user", "password", "ROLE_TEST", "New Test User", "Image Url", "Test Bio");
        User createdUser = dao.createUser(newUser.getUsername(), "password", "TEST");

        int newId = createdUser.getId();
        User retrievedUser = dao.getUserById(newId);

        assertUsersMatch(createdUser, retrievedUser, "createUser did not save correct data in database");
    }

    @Test
    public void updateUser_returns_user_with_correct_values() {
        User user = new User();
        user.setId(USER_2.getId());
        user.setUsername(USER_2.getUsername());
        user.setAuthorities(USER_2.getAuthorities());

        // Change updatable fields
        user.setDisplayName("Modified Name");
        user.setProfileImageUrl("http://updated.com");
        user.setShortBio("Modified Bio");

        User result = dao.updateUser(user);
        assertUsersMatch(user, result, "updateUser returned wrong or partial data");
    }

    @Test
    public void updateUser_has_expected_values_when_retrieved() {
        User user = new User();
        user.setId(USER_2.getId());
        user.setUsername(USER_2.getUsername());
        user.setAuthorities(USER_2.getAuthorities());

        // Change updatable fields
        user.setDisplayName("Modified Name");
        user.setProfileImageUrl("http://updated.com");
        user.setShortBio("Modified Bio");

        User result = dao.updateUser(user);
        User retrievedUser = dao.getUserById(result.getId());
        assertUsersMatch(user, retrievedUser, "updateUser did not save correct data in database");
    }


    private void assertUsersMatch(User expected, User actual, String messagePrefix) {
        assertEquals(expected.getId(), actual.getId(), messagePrefix + ": User user_id field does not match expected value.");
        assertEquals(expected.getId(), actual.getId(),
                messagePrefix + ": User (user_id=" + expected.getId() + ") contains unexpected data in field 'user_id'.");
        assertEquals(expected.getUsername(), actual.getUsername(),
                messagePrefix + ": User (user_id=" + expected.getId() + ") contains unexpected data in field 'username'.");
        // Password will be hashed differently each time, and is not returned by all queries, so ignore

        assertEquals(expected.getAuthorities().size(), actual.getAuthorities().size(),
                messagePrefix + ": User (user_id=" + expected.getId() + ") contains wrong number of authorities.");
        for (Authority authority : expected.getAuthorities()){
            assertTrue(actual.getAuthorities().contains(authority),
                    messagePrefix + ": User (user_id=" + expected.getId() + ") does not contain expected authority " + authority.getName());
        }

        assertEquals(expected.getDisplayName(), actual.getDisplayName(),
                messagePrefix + ": User display_name field does not match expected value.");
        assertEquals(expected.getProfileImageUrl(), actual.getProfileImageUrl(),
                messagePrefix + ": User img_url field does not match expected value.");
        assertEquals(expected.getShortBio(), actual.getShortBio(),
                messagePrefix + ": User short_bio field does not match expected value.");
    }

    private void assertUserListsMatch(List<User> expectedList, List<User> actualList, String messagePrefix) {
        assertEquals(expectedList.size(), actualList.size(), messagePrefix + ": List size incorrect.");
        for (int i = 0; i < expectedList.size(); i++) {
            User expected = expectedList.get(i);
            User actual = actualList.get(i);
            if (i == 0) {
                // For first item, if ids do not match, may indicate bad sorting
                assertEquals(expected.getId(), actual.getId(),
                        messagePrefix + ": first user id does not match expected, check sort order");
            }
            assertUsersMatch(expected, actual, messagePrefix);
        }
    }
}
