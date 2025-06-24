package com.techelevator.bookmark.service;

import com.techelevator.bookmark.dao.BookmarkDao;
import com.techelevator.bookmark.dao.TagDao;
import com.techelevator.bookmark.dao.UserDao;
import com.techelevator.bookmark.model.Bookmark;
import com.techelevator.bookmark.model.Tag;
import com.techelevator.bookmark.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
 * These tests use a library called Mockito to "fake" the DAO implementation classes used in the BookmarkService.
 * This allows the test to control how those DAO methods respond without the need to set up test data in an actual
 * database or some other sort of datastore. This allows the test to focus on testing just the logic in the service
 * class itself. The logic in the mock or "fake" DAO is expected to be tested elsewhere.
 */

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {

    private final Tag TAG_1 = new Tag(1, "Tag 1");
    private final Tag TAG_2 = new Tag(2, "Tag 2");
    private final Tag TAG_3 = new Tag(3, "Tag 3");
    private final Tag TAG_4 = new Tag(4, "Tag 4");
    private final Tag TAG_5 = new Tag(5, "Tag 5");
    private final Tag TAG_6 = new Tag(6, "Tag 6");

    private final User ADMIN_USER = new User(1, "admin", "password", "ROLE_ADMIN", "Admin", null, null);
    private final User CUSTOMER_USER = new User(2, "customer", "password", "ROLE_USER", "Customer", null, null);

    @Mock
    private Principal principal;

    @Mock
    private BookmarkDao mockBookmarkDao;

    @Mock
    private TagDao mockTagDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private BookmarkService bookmarkService;

    /*
     * Business rules for getBookmarksForUser:
     *   - Users with the "USER_ROLE" get only bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" get only the bookmarks marked as "public"
     */
    @Test
    public void getAllBookmarks_returns_correct_list_for_admin() {

        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        List<Bookmark> expected = new ArrayList<Bookmark>();
        when(mockBookmarkDao.getPublicBookmarks()).thenReturn(expected);

        // Act - call method
        List<Bookmark> actual = bookmarkService.getBookmarksForUser(principal);

        // Assert - check returned list was not null or empty
        assertEquals(expected, actual, "getBookmarksForUser did not return correct list for admin user role.");
    }

    @Test
    public void getAllBookmarks_returns_correct_list_for_customer() {

        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        List<Bookmark> expected = new ArrayList<Bookmark>();
        when(mockBookmarkDao.getBookmarksByUserId(CUSTOMER_USER.getId())).thenReturn(expected);

        // Act - call method
        List<Bookmark> actual = bookmarkService.getBookmarksForUser(principal);

        // Assert - check returned list was not null or empty
        assertEquals(expected, actual, "getBookmarksForUser did not return correct list for user role.");
    }

    /* Business rules for getBookmarkById:
     *   - Users with the "USER_ROLE" get only bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" get only the bookmarks marked as "public"
     */
    @Test
    public void getBookmarkById_returns_public_bookmark_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark expected = new Bookmark();
        expected.setBookmarkId(1);
        expected.setUserId(999); // Not the admin user id
        expected.setPublic(true);
        when(mockBookmarkDao.getBookmarkById(expected.getBookmarkId())).thenReturn(expected);

        try {
            // Act - call method
            Bookmark actual = bookmarkService.getBookmarkById(expected.getBookmarkId(), principal);
            // Assert - check returned list was not null or empty
            assertEquals(expected, actual, "getBookmarkById did not return correct bookmark.");
        } catch (AccessDeniedException e) {
            fail("getBookmarkById did not allow access for public bookmark.");
        }
    }

    @Test
    public void getBookmarkById_returns_public_bookmark_for_other_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark expected = new Bookmark();
        expected.setBookmarkId(1);
        expected.setUserId(999); // Not the customer user id
        expected.setPublic(true);
        when(mockBookmarkDao.getBookmarkById(expected.getBookmarkId())).thenReturn(expected);

        try {
            // Act - call method
            Bookmark actual = bookmarkService.getBookmarkById(expected.getBookmarkId(), principal);
            // Assert - check returned value
            // Using mock so only need to make sure it is not null which would mean method was not called as expected
            assertNotNull(actual, "getBookmarkById returned null");
        } catch (AccessDeniedException e) {
            fail("getBookmarkById did not allow access for public bookmark.");
        }
    }

    @Test
    public void getBookmarkById_private_bookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark expected = new Bookmark();
        expected.setBookmarkId(1);
        expected.setUserId(999);   // Not the admin user id
        expected.setPublic(false); // Private bookmark
        when(mockBookmarkDao.getBookmarkById(expected.getBookmarkId())).thenReturn(expected);

        try {
            // Act - call method
            Bookmark actual = bookmarkService.getBookmarkById(expected.getBookmarkId(), principal);
            // Assert - fail if exception was not thrown
            fail("getBookmarkById did not prevent access to non-public bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void getBookmarkById_private_bookmark_access_denied_for_other_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark expected = new Bookmark();
        expected.setBookmarkId(1);
        expected.setUserId(999);   // Not the customer user id
        expected.setPublic(false); // Private bookmark
        when(mockBookmarkDao.getBookmarkById(expected.getBookmarkId())).thenReturn(expected);

        try {
            // Act - call method
            Bookmark actual = bookmarkService.getBookmarkById(expected.getBookmarkId(), principal);
            // Assert - fail if exception was not thrown
            fail("getBookmarkById did not prevent access to non-public bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    /* Business rules for addBookmark:
     *   - Users with the "ADMIN_ROLE" are not eligible to create Bookmarks
     *   - Users with the "USER_ROLE" may create their own Bookmarks
     *   - Users with the "USER_ROLE" MUST NOT create Bookmarks for (associated to) other users
     */
    @Test
    public void addBookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);

        try {
            // Act - call method
            Bookmark actual = bookmarkService.addBookmark(new Bookmark(), principal);
            // Assert - fail if exception was not thrown
            fail("addBookmark did not prevent admin from creating bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void addBookmark_creates_bookmark_associated_to_logged_in_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark expected = new Bookmark();
        expected.setUserId(CUSTOMER_USER.getId());
        when(mockBookmarkDao.createBookmark(refEq(expected))).thenReturn(expected);

        try {
            // Act - call method
            Bookmark bookmarkData = new Bookmark();
            bookmarkData.setUserId(999); // Not the customer user id
            Bookmark actual = bookmarkService.addBookmark(bookmarkData, principal);

            // Assert - check returned value
            // Using mock so only need to make sure it is not null which would mean method was not called as expected
            assertNotNull(actual, "addBookmark returned null");
        } catch (AccessDeniedException e) {
            fail("addBookmark did not allow access for customer user.");
        }
    }

    @Test
    public void addBookmark_with_tags_persists_tags() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmarkData = new Bookmark();
        bookmarkData.setUserId(999); // Not the customer user id
        bookmarkData.setTags(List.of(TAG_1, TAG_4));
        when(mockBookmarkDao.createBookmark(bookmarkData)).thenReturn(bookmarkData);

        // Act - call method
        bookmarkService.addBookmark(bookmarkData, principal);

        // Assert - were tags persisted?
        verify(mockBookmarkDao).linkBookmarkTag(bookmarkData.getBookmarkId(), TAG_1.getId());
        verify(mockBookmarkDao).linkBookmarkTag(bookmarkData.getBookmarkId(), TAG_4.getId());
        verify(mockBookmarkDao, times(2)).linkBookmarkTag(anyInt(), anyInt());
    }


    /*
     * Business rules for removeBookmark:
     *   - Users with the "ADMIN_ROLE" may only delete public Bookmarks
     *   - Users with the "USER_ROLE" may delete their own Bookmarks
     *   - Users with the "USER_ROLE" MUST NOT delete Bookmarks associated to other users
     */
    @Test
    public void removeBookmark_private_bookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setPublic(false);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            boolean result = bookmarkService.removeBookmark(bookmark.getBookmarkId(), principal);
            // Assert - fail if exception was not thrown
            fail("addBookmark did not prevent admin from deleting private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void removeBookmark_public_bookmark_removed_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setPublic(true);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);
        when(mockBookmarkDao.deleteBookmarkById(bookmark.getBookmarkId())).thenReturn(3); // return non-zero row count

        try {
            // Act - call method
            boolean result = bookmarkService.removeBookmark(bookmark.getBookmarkId(), principal);
            // Assert
            assertTrue(result, "Bookmark was not deleted");
        } catch (AccessDeniedException e) {
            fail("removeBookmark did not allow access for admin user.");
        }
    }

    @Test
    public void removeBookmark_other_users_bookmark_access_denied_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(999); // Not customer user id
        bookmark.setPublic(true);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            bookmarkService.removeBookmark(bookmark.getBookmarkId(), principal);
            // Assert - fail if exception was not thrown
            fail("removeBookmark did not prevent other user from deleting private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void removeBookmark_own_bookmark_removed_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);
        when(mockBookmarkDao.deleteBookmarkById(bookmark.getBookmarkId())).thenReturn(3); // return non-zero row count

        try {
            // Act - call method
            boolean result = bookmarkService.removeBookmark(bookmark.getBookmarkId(), principal);
            // Assert
            assertTrue(result, "Bookmark was not deleted");
        } catch (AccessDeniedException e) {
            fail("removeBookmark did not allow access for bookmark owner.");
        }
    }

    /*
     * Business rules for updateBookmark:
     *   - Users with the "ADMIN_ROLE" may update any public bookmark, including the flagged property
     *   - Users with the "USER_ROLE" may flag any public bookmark (set the flagged property to true)
     *   - Users with the "USER_ROLE" may update their own bookmarks (except as noted below)
     *   - Users with the "USER_ROLE" MUST NOT un-flag ANY bookmark (set the flagged property to false)
     *   - Users with the "USER_ROLE" MUST NOT give their bookmark to a different user (change associated user id)
     *   - Users with the "USER_ROLE" MUST NOT update any private bookmarks associated to another user
     */
    @Test
    public void updateBookmark_public_bookmark_updated_correctly_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        // Setup public bookmark which is flagged, associated to user
        final String createDate = "1999-09-09";
        Bookmark original = new Bookmark(99, CUSTOMER_USER.getId(), CUSTOMER_USER.getDisplayName(), "Initial title", "Initial url",
                "Initial description", true, true, createDate, "Initial tags");
        when(mockBookmarkDao.getBookmarkById(original.getBookmarkId())).thenReturn(original);

        // Bookmark id, user id, createDate, and tags cannot be changed
        Bookmark expected = new Bookmark(original.getBookmarkId(), original.getUserId(), CUSTOMER_USER.getDisplayName(),
                "Updated title", "Updated url", "Updated description", false, false,
                createDate, original.getAllTags());
        // DAO will not update tags or createDate, so service ignores these
        Bookmark updateData = new Bookmark(original.getBookmarkId(), original.getUserId(), CUSTOMER_USER.getDisplayName(),
                "Updated title", "Updated url", "Updated description", false, false,
                "2000-02-02", "Updated tags");
        when(mockBookmarkDao.updateBookmark(refEq(updateData)))
                .thenReturn(expected);

        try {
            // Act - call method, attempting to change all values
            Bookmark actual = bookmarkService.updateBookmark(updateData, principal);

            // Assert
            assertNotNull(actual, "UpdateBookmark returned null.");

        } catch (AccessDeniedException e) {
            fail("updateBookmark did not allow access for bookmark owner.");
        }
    }

    @Test
    public void updateBookmark_private_bookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());    // Not admin user id
        bookmark.setPublic(false);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            bookmarkService.updateBookmark(bookmark, principal);
            // Assert - fail if exception was not thrown
            fail("updateBookmark did not prevent admin user from updating private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void updateBookmark_other_user_private_bookmark_access_denied_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(999);    // Not customer user id
        bookmark.setPublic(false);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            bookmarkService.updateBookmark(bookmark, principal);
            // Assert - fail if exception was not thrown
            fail("updateBookmark did not prevent other user from updating private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void updateBookmark_flag_other_user_public_bookmark_allowed_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        // Setup public bookmark which is un-flagged, associated to another user
        final String createDate = "1999-09-09";
        Bookmark original = new Bookmark(99, 999, "Some User", "Initial title", "Initial url",
                "Initial description", true, false, createDate, "Initial tags");
        when(mockBookmarkDao.getBookmarkById(original.getBookmarkId())).thenReturn(original);

        // Only flagged value can be changed by user
        Bookmark expected = new Bookmark(original.getBookmarkId(), original.getUserId(), original.getUserDisplayName(),
                original.getTitle(), original.getUrl(), original.getDescription(), original.isPublic(), true,
                createDate, original.getAllTags());
        // Service will prevent update of all other fields except flagged.
        when(mockBookmarkDao.updateBookmark(refEq(expected))).thenReturn(expected);


        try {
            // Act - call method, attempting to change all values
            Bookmark updateData = new Bookmark(original.getBookmarkId(), 123, "Foo", "Updated title",
                    "Updated url", "Updated description", false, true, "2000-02-02", "Updated tags");
            Bookmark actual = bookmarkService.updateBookmark(updateData, principal);

            // Assert
            assertNotNull(actual, "UpdateBookmark returned null.");
        } catch (AccessDeniedException e) {
            fail("updateBookmark did not allow access to flag public bookmark.");
        }
    }

    @Test
    public void updateBookmark_own_bookmark_updated_correctly_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        // Setup non-public bookmark which is flagged, associated to user
        final String createDate = "1999-09-09";
        Bookmark original = new Bookmark(99, CUSTOMER_USER.getId(), CUSTOMER_USER.getDisplayName(),
                "Initial title", "Initial url", "Initial description", false, true,
                createDate, "Initial tags");
        when(mockBookmarkDao.getBookmarkById(original.getBookmarkId())).thenReturn(original);

        // Bookmark id, user id, createDate, tags and flagged value cannot be changed by user
        Bookmark expected = new Bookmark(original.getBookmarkId(), original.getUserId(), original.getUserDisplayName(),
                "Updated title", "Updated url", "Updated description", true, original.isFlagged(),
                createDate, original.getAllTags());
        // DAO will not update tags or createDate, not necessary for service to do anything to these values
        // Service should prevent update of flag & user id
        when(mockBookmarkDao.updateBookmark(refEq(new Bookmark(original.getBookmarkId(), original.getUserId(), "Foo",
                "Updated title", "Updated url", "Updated description", true, original.isFlagged(),
                "2000-02-02", "Updated tags"))))
                .thenReturn(expected);

        try {
            // Act - call method, attempting to change all values
            Bookmark updateData = new Bookmark(original.getBookmarkId(), 123, "Foo", "Updated title",
                    "Updated url", "Updated description", true, true, "2000-02-02", "Updated tags");
            Bookmark actual = bookmarkService.updateBookmark(updateData, principal);

            // Assert
            assertNotNull(actual, "updateBookmark returned null.");
        } catch (AccessDeniedException e) {
            fail("updateBookmark did not allow access for bookmark owner.");
        }
    }

    @Test
    public void updateBookmark_with_tags_resyncs_tags() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);

        Bookmark original = new Bookmark(99, CUSTOMER_USER.getId(), CUSTOMER_USER.getDisplayName(),
                "Initial title", "Initial url", "Initial description", false, false,
                "1999-09-09", "Initial tags", Arrays.asList(TAG_1, TAG_2));
        when(mockBookmarkDao.getBookmarkById(original.getBookmarkId())).thenReturn(original);

        when(mockBookmarkDao.updateBookmark(original)).thenReturn(original);
        when(mockTagDao.getTagsByBookmarkId(original.getBookmarkId())).thenReturn(new ArrayList<>());

        // Act - call method, attempting to change all values
        Bookmark actual = bookmarkService.updateBookmark(original, principal);

        // Assert
        verify(mockTagDao).getTagsByBookmarkId(original.getBookmarkId());
        verify(mockBookmarkDao).linkBookmarkTag(original.getBookmarkId(), TAG_1.getId());
        verify(mockBookmarkDao).linkBookmarkTag(original.getBookmarkId(), TAG_2.getId());
    }


    /*
     * Business rules for getBookmarkTags:
     *   - Users with the "USER_ROLE" may only get tags for bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" may only get tags a "public" bookmark
     */
    @Test
    public void getBookmarkTags_private_bookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            bookmarkService.getBookmarkTags(bookmark.getBookmarkId(), principal);
            // Assert - fail if exception was not thrown
            fail("getBookmarkTags did not prevent admin user from accessing private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void getBookmarkTags_public_bookmark_returns_tags_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(true);  // Public bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        List<Tag> expected = new ArrayList<>();
        when(mockTagDao.getTagsByBookmarkId(bookmark.getBookmarkId())).thenReturn(expected);

        try {
            // Act - call method
            List<Tag> actual = bookmarkService.getBookmarkTags(bookmark.getBookmarkId(), principal);
            // Assert - check returned value
            // Using mock so only need to make sure it is not null which would mean method was not called as expected
            assertNotNull(actual, "getBookmarkTags returned null");
        } catch (AccessDeniedException e) {
            fail("getBookmarkTags did not allow access to public bookmark tags for admin.");
        }
    }

    @Test
    public void getBookmarkTags_other_user_private_bookmark_access_denied_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(999);    // Not customer user id
        bookmark.setPublic(false);  // Private bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            bookmarkService.getBookmarkTags(bookmark.getBookmarkId(), principal);
            // Assert - fail if exception was not thrown
            fail("getBookmarkTags did not prevent other user from accessing private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void getBookmarkTags_other_user_public_bookmark_returns_tags_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(999);   // Not customer user id
        bookmark.setPublic(true);  // Public bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        List<Tag> expected = new ArrayList<>();
        when(mockTagDao.getTagsByBookmarkId(bookmark.getBookmarkId())).thenReturn(expected);

        try {
            // Act - call method
            List<Tag> actual = bookmarkService.getBookmarkTags(bookmark.getBookmarkId(), principal);
            // Assert - check returned value
            // Using mock so only need to make sure it is not null which would mean method was not called as expected
            assertNotNull(actual, "getBookmarkTags returned null");
        } catch (AccessDeniedException e) {
            fail("getBookmarkTags did not allow access to own bookmark tags for customer.");
        }
    }

    @Test
    public void getBookmarkTags_own_bookmark_returns_tags_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark OK - both should be fine
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        List<Tag> expected = new ArrayList<>();
        when(mockTagDao.getTagsByBookmarkId(bookmark.getBookmarkId())).thenReturn(expected);

        try {
            // Act - call method
            List<Tag> actual = bookmarkService.getBookmarkTags(bookmark.getBookmarkId(), principal);
            // Assert - check returned value
            // Using mock so only need to make sure it is not null which would mean method was not called as expected
            assertNotNull(actual, "getBookmarkTags returned null");
        } catch (AccessDeniedException e) {
            fail("getBookmarkTags did not allow access to own bookmark tags for customer.");
        }
    }

    /*
     * Business rules for addTagToBookmark:
     *   - Users with the "USER_ROLE" may only tag bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" may tag any "public" bookmark
     */
    @Test
    public void addTagToBookmark_private_bookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            bookmarkService.addTagToBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert - fail if exception was not thrown
            fail("addTagToBookmark did not prevent admin user from adding tag to private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void addTagToBookmark_public_bookmark_tag_added_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(true);  // Public bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            boolean result = bookmarkService.addTagToBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert
            assertTrue(result, "Bookmark tag was not added");
        } catch (AccessDeniedException e) {
            fail("addTagToBookmark did not allow access to tag public bookmark for admin.");
        }
    }

    @Test
    public void addTagToBookmark_other_user_bookmark_access_denied_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(999);    // Not customer user id
        bookmark.setPublic(true);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            bookmarkService.addTagToBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert - fail if exception was not thrown
            fail("addTagToBookmark did not prevent other user from adding tag to non-owned bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void addTagToBookmark_own_bookmark_tag_added_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark OK - both should be fine
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            boolean result = bookmarkService.addTagToBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert
            assertTrue(result, "Bookmark tag was not added");
        } catch (AccessDeniedException e) {
            fail("addTagToBookmark did not allow access to tag public bookmark for owner.");
        }
    }

    /*
     * Business rules for removeTagFromBookmark:
     *   - Users with the "USER_ROLE" may only untag bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" may untag any "public" bookmark
     */
    @Test
    public void removeTagFromBookmark_private_bookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            bookmarkService.removeTagFromBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert - fail if exception was not thrown
            fail("removeTagFromBookmark did not prevent admin user from removing tag of private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void removeTagFromBookmark_public_bookmark_tag_removed_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(true);  // Public bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            boolean result = bookmarkService.removeTagFromBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert
            assertTrue(result, "Bookmark tag was not removed");
        } catch (AccessDeniedException e) {
            fail("removeTagFromBookmark did not allow access to tag public bookmark for admin.");
        }
    }

    @Test
    public void removeTagFromBookmark_other_user_bookmark_access_denied_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(999);    // Not customer user id
        bookmark.setPublic(true);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            bookmarkService.removeTagFromBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert - fail if exception was not thrown
            fail("removeTagFromBookmark did not prevent other user from removing tag of non-owned bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void removeTagFromBookmark_own_bookmark_tag_removed_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark OK - both should be fine
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        Tag tag = new Tag(9999, "Test tag");
        when((mockTagDao.getTagById(tag.getId()))).thenReturn(tag);

        try {
            // Act - call method
            boolean result = bookmarkService.removeTagFromBookmark(bookmark.getBookmarkId(), tag.getId(), principal);
            // Assert
            assertTrue(result, "Bookmark tag was not removed");
        } catch (AccessDeniedException e) {
            fail("removeTagFromBookmark did not allow access to tag public bookmark for owner.");
        }
    }

    @Test
    public void updateBookmarkTag_own_bookmark_adds_all_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark OK - both should be fine
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);
        // Setup existing tags as empty, so update should add all
        List<Tag> existingTags = new ArrayList<>();
        when(mockTagDao.getTagsByBookmarkId(bookmark.getBookmarkId())).thenReturn(existingTags);

        List<Tag> updateTags = new ArrayList<>();
        updateTags.add(TAG_1);
        updateTags.add(TAG_3);

        try {
            // Act - call method
            boolean result = bookmarkService.updateBookmarkTags(bookmark.getBookmarkId(), updateTags, principal);
            // Assert
            assertTrue(result, "Bookmark tags were not updated");
            verify(mockBookmarkDao).linkBookmarkTag(bookmark.getBookmarkId(), TAG_1.getId());
            verify(mockBookmarkDao).linkBookmarkTag(bookmark.getBookmarkId(), TAG_3.getId());
        } catch (AccessDeniedException e) {
            fail("updateBookmarkTags did not allow access to tag public bookmark for owner.");
        }
    }

    @Test
    public void updateBookmarkTag_own_bookmark_removes_all_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark OK - both should be fine
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);
        List<Tag> existingTags = new ArrayList<>();
        existingTags.add(TAG_1);
        existingTags.add(TAG_3);
        when(mockTagDao.getTagsByBookmarkId(bookmark.getBookmarkId())).thenReturn(existingTags);
        // Setup update tags as empty, so update should remove all
        List<Tag> updateTags = new ArrayList<>();

        try {
            // Act - call method
            boolean result = bookmarkService.updateBookmarkTags(bookmark.getBookmarkId(), updateTags, principal);
            // Assert
            assertTrue(result, "Bookmark tags were not updated");
            verify(mockBookmarkDao).unlinkBookmarkTag(bookmark.getBookmarkId(), TAG_1.getId());
            verify(mockBookmarkDao).unlinkBookmarkTag(bookmark.getBookmarkId(), TAG_3.getId());
        } catch (AccessDeniedException e) {
            fail("updateBookmarkTags did not allow access to tag public bookmark for owner.");
        }
    }

    @Test
    public void updateBookmarkTag_own_bookmark_updates_all_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark OK - both should be fine
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);
        // Setup for a mix of updates and deletes
        List<Tag> existingTags = new ArrayList<>();
        existingTags.add(TAG_1);
        existingTags.add(TAG_2);
        existingTags.add(TAG_5);
        existingTags.add(TAG_6);
        when(mockTagDao.getTagsByBookmarkId(bookmark.getBookmarkId())).thenReturn(existingTags);
        List<Tag> updateTags = new ArrayList<>();
        updateTags.add(TAG_1);
        updateTags.add(TAG_2);
        updateTags.add(TAG_3);
        updateTags.add(TAG_4);

        try {
            // Act - call method
            boolean result = bookmarkService.updateBookmarkTags(bookmark.getBookmarkId(), updateTags, principal);
            // Assert
            assertTrue(result, "Bookmark tags were not updated");
            verify(mockBookmarkDao).unlinkBookmarkTag(bookmark.getBookmarkId(), TAG_5.getId());
            verify(mockBookmarkDao).unlinkBookmarkTag(bookmark.getBookmarkId(), TAG_6.getId());
            verify(mockBookmarkDao).linkBookmarkTag(bookmark.getBookmarkId(), TAG_3.getId());
            verify(mockBookmarkDao).linkBookmarkTag(bookmark.getBookmarkId(), TAG_4.getId());
        } catch (AccessDeniedException e) {
            fail("updateBookmarkTags did not allow access to tag public bookmark for owner.");
        }
    }

    @Test
    public void updateBookmarkTags_other_user_bookmark_access_denied_for_customer() {
        // Arrange - configure mocks
        setupMocksForUser(CUSTOMER_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(999);    // Not customer user id
        bookmark.setPublic(true);
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            bookmarkService.updateBookmarkTags(bookmark.getBookmarkId(), new ArrayList<>(), principal);
            // Assert - fail if exception was not thrown
            fail("updateBookmarkTags did not prevent other user from updating tag of non-owned bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    @Test
    public void updateBookmarkTags_private_bookmark_access_denied_for_admin() {
        // Arrange - configure mocks
        setupMocksForUser(ADMIN_USER);
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(99);
        bookmark.setUserId(CUSTOMER_USER.getId());
        bookmark.setPublic(false);  // Private bookmark
        when(mockBookmarkDao.getBookmarkById(bookmark.getBookmarkId())).thenReturn(bookmark);

        try {
            // Act - call method
            bookmarkService.updateBookmarkTags(bookmark.getBookmarkId(), new ArrayList<>(), principal);
            // Assert - fail if exception was not thrown
            fail("updateBookmarkTags did not prevent admin user from updating tag of private bookmark.");
        } catch (AccessDeniedException e) {
            // Do nothing, this is the expected behavior
        }
    }

    // Helper methods
    private void setupMocksForUser(User user) {
        String username = user.getUsername();
        when(principal.getName()).thenReturn(username);
        when(userDao.getUserByUsername(username)).thenReturn(user);
    }
}

