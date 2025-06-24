package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Bookmark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcBookmarkDaoTest extends BaseDaoTest {

    private static final int USER_ID_ADMIN = 100;
    private static final int USER_ID_1 = 101;
    private static final int USER_ID_2 = 102;

    private static final Bookmark BOOKMARK_1 =
            new Bookmark(300, 101, "User 1 - Jo Tagolia", "User 1 - A", "https://www.tester.com", "Test Home.", false, false, "2020-01-20", "Tag 1, Tag 2, Tag 3");
    private static final Bookmark BOOKMARK_2 =
            new Bookmark(301, 102, "User 2", "User 2 - B", "https://www.tester.com", "User 2 - Test Home.", false, false, "2020-01-21", "Tag 3");
    private static final Bookmark BOOKMARK_3 =
            new Bookmark(302, 101, "User 1 - Jo Tagolia", "User 1 - C - public, flagged", "https://www.test.com/sub1/", null, true, true, "2020-01-21", null);
    private static final Bookmark BOOKMARK_4 =
            new Bookmark(303, 101, "User 1 - Jo Tagolia", "User 1 - B - public", "https://fake.org/", "Fake test site", true, false, "2020-01-22", null);
    private static final Bookmark BOOKMARK_5 =
            new Bookmark(304, 102, "User 2", "User 2 - A - public, flagged", "https://www.test.com/sub2/", null, true, true, "2020-01-22", "Tag 1, Tag 3");

    private static final List<Bookmark> ALL_BOOKMARKS = Arrays.asList(new Bookmark[] {BOOKMARK_1, BOOKMARK_4, BOOKMARK_3, BOOKMARK_5, BOOKMARK_2});

    private JdbcBookmarkDao dao;

    @BeforeEach
    public void setup() {
        dao = new JdbcBookmarkDao(dataSource);
    }

    @Test
    public void getBookmarkById_returns_correct_bookmark() {
        Bookmark result = dao.getBookmarkById(BOOKMARK_1.getBookmarkId());
        assertNotNull(result, "getBookmarkById returned null");
        assertBookmarksMatch(BOOKMARK_1, result, "getBookmarkById returned wrong or partial data");
    }

    @Test
    public void getBookmarkById_returns_null_when_id_not_found() {
        Bookmark result = dao.getBookmarkById(9999);
        assertNull(result, "getBookmarkById did not return null when bookmark id is not found");
    }

    @Test
    public void getBookmarks_returns_correct_list_sorted_by_title() {
        List<Bookmark> result = dao.getBookmarks();
        assertBookmarkListsMatch(ALL_BOOKMARKS, result, "getBookmarks returned wrong or partial data");
    }

    @Test
    public void getBookmarksByUserId_returns_correct_list_sorted_by_title() {
        List<Bookmark> USER_1_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_1;
        }).collect(Collectors.toList());

        List<Bookmark> user1Bookmarks = dao.getBookmarksByUserId(USER_ID_1);
        assertBookmarkListsMatch(USER_1_BOOKMARKS, user1Bookmarks, "getBookmarksByUserId returned wrong or partial data for user 1");

        List<Bookmark> USER_2_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_2;
        }).collect(Collectors.toList());

        List<Bookmark> user2Bookmarks = dao.getBookmarksByUserId(USER_ID_2);
        assertBookmarkListsMatch(USER_2_BOOKMARKS, user2Bookmarks, "getBookmarksByUserId returned wrong or partial data for user 2");

        List<Bookmark> adminBookmarks = dao.getBookmarksByUserId(USER_ID_ADMIN);
        assertBookmarkListsMatch(new ArrayList<Bookmark>(), adminBookmarks, "getBookmarksByUserId returned wrong or partial data for admin user");
    }

    @Test
    public void getPublicBookmarksByUserId_returns_correct_list_sorted_by_title() {
        List<Bookmark> USER_1_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_1 && item.isPublic();
        }).collect(Collectors.toList());

        List<Bookmark> user1Bookmarks = dao.getPublicBookmarksByUserId(USER_ID_1);
        assertBookmarkListsMatch(USER_1_BOOKMARKS, user1Bookmarks, "getPublicBookmarksByUserId returned wrong or partial data for user 1");

        List<Bookmark> USER_2_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getUserId() == USER_ID_2 && item.isPublic();
        }).collect(Collectors.toList());

        List<Bookmark> user2Bookmarks = dao.getPublicBookmarksByUserId(USER_ID_2);
        assertBookmarkListsMatch(USER_2_BOOKMARKS, user2Bookmarks, "getPublicBookmarksByUserId returned wrong or partial data for user 2");

        List<Bookmark> adminBookmarks = dao.getPublicBookmarksByUserId(USER_ID_ADMIN);
        assertBookmarkListsMatch(new ArrayList<Bookmark>(), adminBookmarks, "getPublicBookmarksByUserId returned wrong or partial data for admin user");
    }

    @Test
    public void getPublicBookmarks_returns_correct_list_sorted_by_title() {
        List<Bookmark> PUBLIC_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic();
        }).collect(Collectors.toList());

        List<Bookmark> result = dao.getPublicBookmarks();
        assertBookmarkListsMatch(PUBLIC_BOOKMARKS, result, "getPublicBookmarks returned wrong or partial data");

    }

    @Test
    public void getFlaggedBookmarks_returns_correct_list_sorted_by_title() {
        List<Bookmark> FLAGGED_BOOKMARKS = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isFlagged();
        }).collect(Collectors.toList());

        List<Bookmark> result = dao.getFlaggedBookmarks();
        assertBookmarkListsMatch(FLAGGED_BOOKMARKS, result, "getFlaggedBookmarks returned wrong or partial data");
    }

    @Test
    public void filterBookmarks_all_returns_correct_list_sorted_by_title() {
        // Try something in description
        String filterString = "test";
        List<Bookmark> expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getTitle().toLowerCase().contains(filterString.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString.toLowerCase()));
        }).collect(Collectors.toList());

        List<Bookmark> actual = dao.filterBookmarks(filterString, false, true);
        assertBookmarkListsMatch(expected, actual, "filterBookmarks returned wrong or partial data");

        // Repeat with something in tags and display name
        String filterString2 = "tag";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getTitle().toLowerCase().contains(filterString2.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString2.toLowerCase()));
        }).collect(Collectors.toList());

        actual = dao.filterBookmarks(filterString2, false, true);
        assertBookmarkListsMatch(expected, actual, "filterBookmarks returned wrong or partial data");

        // Repeat with something in title, description, and display name
        String filterString3 = "user 2";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.getTitle().toLowerCase().contains(filterString3.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString3.toLowerCase()));
        }).collect(Collectors.toList());

        actual = dao.filterBookmarks(filterString3, false, true);
        assertBookmarkListsMatch(expected, actual, "filterBookmarks returned wrong or partial data");
    }

    @Test
    public void filterBookmarks_public_returns_correct_list_sorted_by_title() {
        // Try something in description
        String filterString = "test";
        List<Bookmark> expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic() && (item.getTitle().toLowerCase().contains(filterString.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString.toLowerCase())));
        }).collect(Collectors.toList());

        List<Bookmark> actual = dao.filterBookmarks(filterString, true, true);
        assertBookmarkListsMatch(expected, actual, "filterBookmarks returned wrong or partial data");

        // Repeat with something in tags and display name
        String filterString2 = "tag";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic() && (item.getTitle().toLowerCase().contains(filterString2.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString2.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString2.toLowerCase())));
        }).collect(Collectors.toList());

        actual = dao.filterBookmarks(filterString2, true, true);
        assertBookmarkListsMatch(expected, actual, "filterBookmarks returned wrong or partial data");

        // Repeat with something in title, description, and display name
        String filterString3 = "user 2";
        expected = ALL_BOOKMARKS.stream().filter(item -> {
            return item.isPublic() && (item.getTitle().toLowerCase().contains(filterString3.toLowerCase()) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getAllTags() != null && item.getAllTags().toLowerCase().contains(filterString3.toLowerCase())) ||
                    (item.getUserDisplayName() != null && item.getUserDisplayName().toLowerCase().contains(filterString3.toLowerCase())));
        }).collect(Collectors.toList());

        actual = dao.filterBookmarks(filterString3, true, true);
        assertBookmarkListsMatch(expected, actual, "filterBookmarks returned wrong or partial data");
    }

    @Test
    public void createBookmark_returns_newBookmark_with_id_and_correct_values() {
        Bookmark testBookmark = new Bookmark();
        testBookmark.setUserId(BOOKMARK_1.getUserId());
        testBookmark.setTitle("New Test Bookmark");
        testBookmark.setUrl("http://test.com");
        testBookmark.setDescription("Test Bookmark 1 Description");
        testBookmark.setPublic(true);
        // Cannot flag a bookmark on creation
        testBookmark.setCreateDate(LocalDate.parse("2020-01-01"));

        Bookmark result = dao.createBookmark(testBookmark);
        assertNotNull(result, "createBookmark returned null");

        int newId = result.getBookmarkId();
        assertTrue(newId > 0, "createBookmark returned a bookmark without an id");

        // Update the bookmarkId and user display name to expected values (not sent in with create data)
        testBookmark.setBookmarkId(newId);
        testBookmark.setUserDisplayName(BOOKMARK_1.getUserDisplayName());
        assertBookmarksMatch(testBookmark, result, "createBookmark returned wrong or partial data");
    }

    @Test
    public void createdBookmark_bookmark_has_expected_values_when_retrieved() {
        Bookmark testBookmark = new Bookmark();
        testBookmark.setUserId(BOOKMARK_1.getUserId());
        testBookmark.setTitle("New Test Bookmark");
        testBookmark.setUrl("http://test.com");
        testBookmark.setDescription("Test Bookmark 1 Description");
        testBookmark.setPublic(true);
        // Cannot flag a bookmark on creation
        testBookmark.setCreateDate(LocalDate.parse("2020-01-01"));

        Bookmark createdBookmark = dao.createBookmark(testBookmark);
        int newId = createdBookmark.getBookmarkId();
        Bookmark retrievedBookmark = dao.getBookmarkById(newId);

        assertBookmarksMatch(createdBookmark, retrievedBookmark, "createBookmark did not save correct data in database");
    }

    @Test
    public void updateBookmark_returns_bookmark_with_correct_values() {
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(BOOKMARK_2.getBookmarkId());

        // Don't change non-updatable fields - tested separately
        bookmark.setUserId(BOOKMARK_2.getUserId());
        bookmark.setUserDisplayName(BOOKMARK_2.getUserDisplayName());
        bookmark.setCreateDate(BOOKMARK_2.getCreateDate());
        bookmark.setAllTags(BOOKMARK_2.getAllTags());

        // Change updatable fields
        bookmark.setTitle("Modified Bookmark");
        bookmark.setUrl("http://updated.com");
        bookmark.setDescription("Modified Description");
        bookmark.setPublic(!BOOKMARK_2.isPublic());
        bookmark.setFlagged(!BOOKMARK_2.isFlagged());

        Bookmark result = dao.updateBookmark(bookmark);
        assertBookmarksMatch(bookmark, result, "updateBookmark returned wrong or partial data");
    }

    @Test
    public void updateBookmark_does_not_modify_userId_createDate_allTags() {
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(BOOKMARK_2.getBookmarkId());

        // Don't change updatable fields - tested separately
        bookmark.setTitle(BOOKMARK_2.getTitle());
        bookmark.setUrl(BOOKMARK_2.getUrl());
        bookmark.setDescription(BOOKMARK_2.getDescription());
        bookmark.setPublic(BOOKMARK_2.isPublic());
        bookmark.setFlagged(BOOKMARK_2.isFlagged());

        // Try to modify data that must not be updatable
        bookmark.setUserId(9999);
        bookmark.setUserDisplayName("Foo");
        bookmark.setCreateDate(LocalDate.parse("1900-01-01"));
        bookmark.setAllTags("Updated");

        Bookmark result = dao.updateBookmark(bookmark);
        assertBookmarksMatch(BOOKMARK_2, result, "updateBookmark returned wrong or partial data");
    }

    @Test
    public void updated_bookmark_has_expected_values_when_retrieved() {
        Bookmark bookmark = new Bookmark();
        // Set data that cannot change from original
        bookmark.setBookmarkId(BOOKMARK_2.getBookmarkId());

        // Change updatable fields
        bookmark.setTitle("Modified Bookmark");
        bookmark.setUrl("http://updated.com");
        bookmark.setDescription("Modified Description");
        bookmark.setPublic(!BOOKMARK_2.isPublic());

        // Modify data that must not be updatable
        bookmark.setUserId(9999);
        bookmark.setFlagged(!BOOKMARK_2.isFlagged());
        bookmark.setCreateDate(LocalDate.parse("1900-01-01"));
        bookmark.setAllTags("Updated");

        Bookmark updatedBookmark = dao.updateBookmark(bookmark);
        int newId = updatedBookmark.getBookmarkId();
        Bookmark retrievedBookmark = dao.getBookmarkById(newId);

        assertBookmarksMatch(updatedBookmark, retrievedBookmark, "updateBookmark did not save correct data in database");
    }

    @Test
    public void deleteBookmarkById_removes_bookmark_with_no_tags() {
        dao.deleteBookmarkById(BOOKMARK_4.getBookmarkId());
        Bookmark bookmark = dao.getBookmarkById(BOOKMARK_4.getBookmarkId());
        assertNull(bookmark, "deleteBookmarkById failed to removed bookmark from database");

        List<Bookmark> allBookmarks = dao.getBookmarks();
        assertEquals(ALL_BOOKMARKS.size()-1, allBookmarks.size(), "deleteBookmarkById removed the wrong number of bookmarks");
    }

    @Test
    public void deleteBookmarkById_removes_bookmark_and_tag_associations() {
        dao.deleteBookmarkById(BOOKMARK_1.getBookmarkId());
        Bookmark bookmark = dao.getBookmarkById(BOOKMARK_1.getBookmarkId());
        assertNull(bookmark, "deleteBookmarkById failed to removed bookmark from database");

        List<Bookmark> allBookmarks = dao.getBookmarks();
        assertEquals(ALL_BOOKMARKS.size()-1, allBookmarks.size(), "deleteBookmarkById removed the wrong number of bookmarks");
    }

    @Test
    public void deleteBookmarkById_does_not_error_when_id_not_found() {
        try {
            dao.deleteBookmarkById(9999);
            List<Bookmark> allBookmarks = dao.getBookmarks();
            assertEquals(ALL_BOOKMARKS.size(), allBookmarks.size(), "no bookmarks should have been deleted");
        } catch (Exception e) {
            fail("deleteBookmarkById should not error when not found.");
        }
    }

    @Test
    public void linkBookmarkTag_correctly_adds_association() {
        // Check tags are as expected...
        String currentValue = "Tag 1, Tag 3";
        assertEquals(currentValue, BOOKMARK_5.getAllTags(), "Check test data, expecting test bookmark to have Tags 1 and 3");
        // Add Test Tag 2 with id 201
        dao.linkBookmarkTag(BOOKMARK_5.getBookmarkId(), 201);
        Bookmark updated = dao.getBookmarkById(BOOKMARK_5.getBookmarkId());
        assertEquals("Tag 1, Tag 2, Tag 3", updated.getAllTags(), "Tag 2 was not added correctly. allTags field does not match expected.");
    }

    @Test
    public void unlinkBookmarkTag_correctly_removes_association() {
        // Check tags are as expected...
        String currentValue = "Tag 1, Tag 3";
        assertEquals(currentValue, BOOKMARK_5.getAllTags(), "Check test data, expecting test bookmark to have Tags 1 and 3");
        // Remove Test Tag 1 with id 202
        dao.unlinkBookmarkTag(BOOKMARK_5.getBookmarkId(), 202);
        Bookmark updated = dao.getBookmarkById(BOOKMARK_5.getBookmarkId());
        assertEquals("Tag 3", updated.getAllTags(), "Tag 1 was not removed correctly. allTags field does not match expected.");
    }

    private void assertBookmarksMatch(Bookmark expected, Bookmark actual, String messagePrefix) {
        if (expected == null) {
            assertNull(actual, messagePrefix + ": value should be null");
        } else {
            assertNotNull(actual, messagePrefix + ": value should not be null");
            assertEquals(expected.getBookmarkId(), actual.getBookmarkId(),
                    String.format("%s Bookmark bookmark_id field does not match expected value.", messagePrefix));

            String message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "user_id");
            assertEquals(expected.getUserId(), actual.getUserId(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "user_display_name");
            assertEquals(expected.getUserDisplayName(), actual.getUserDisplayName(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "title");
            assertEquals(expected.getTitle(), actual.getTitle(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "url");
            assertEquals(expected.getUrl(), actual.getUrl(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "description");
            assertEquals(expected.getDescription(), actual.getDescription(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "isPublic");
            assertEquals(expected.isPublic(), actual.isPublic(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "isFlagged");
            assertEquals(expected.isFlagged(), actual.isFlagged(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "create_date");
            assertEquals(expected.getCreateDate(), actual.getCreateDate(), message);

            message = String.format("%s Bookmark (bookmark_id=%d) %s field does not match expected value.", messagePrefix, actual.getBookmarkId(), "allTags");
            assertEquals(expected.getAllTags(), actual.getAllTags(), message + " Verify bookmark_tag associations.");
        }
    }

    private void assertBookmarkListsMatch(List<Bookmark> expectedList, List<Bookmark> actualList, String messagePrefix) {
        if (expectedList == null) {
            assertNull(actualList, messagePrefix + ": List should be null");
        } else {
            assertNotNull(actualList, messagePrefix + ": List should not be null");
            assertEquals(expectedList.size(), actualList.size(),
                    messagePrefix + ": Number of Bookmarks does not match expected.");
            for (int i = 0; i < expectedList.size(); i++) {
                Bookmark expected = expectedList.get(i);
                Bookmark actual = actualList.get(i);
                if (i == 0) {
                    // For first item, if ids do not match, may indicate bad sorting
                    assertEquals(expected.getBookmarkId(), actual.getBookmarkId(),
                            "First Bookmark id does not match expected, check sort order");
                }
                assertBookmarksMatch(expected, actual, messagePrefix);
            }
        }
    }
}
