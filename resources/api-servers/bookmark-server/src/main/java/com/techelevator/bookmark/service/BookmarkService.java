package com.techelevator.bookmark.service;

import com.techelevator.bookmark.dao.BookmarkDao;
import com.techelevator.bookmark.dao.TagDao;
import com.techelevator.bookmark.dao.UserDao;
import com.techelevator.bookmark.exception.DaoException;
import com.techelevator.bookmark.model.Authority;
import com.techelevator.bookmark.model.Bookmark;
import com.techelevator.bookmark.model.Tag;
import com.techelevator.bookmark.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.*;

/**
 * The BookmarkService is a class for handling the business logic around creating, reading,
 * updating, and deleting Bookmarks.
 *
 * It depends on three DAOs for retrieving and storing data: BookmarkDao, UserDao, and TagDao.
 * Instances of each of those DAOs are provided via dependency injection.
 */
@Component
public class BookmarkService {

    private BookmarkDao bookmarkDao;
    private UserDao userDao;
    private TagDao tagDao;

    public BookmarkService(UserDao userDao, BookmarkDao bookmarkDao, TagDao tagDao) {
        this.bookmarkDao = bookmarkDao;
        this.userDao = userDao;
        this.tagDao = tagDao;
    }

    /**
     * Gets all Bookmarks for a specified user.
     *
     * Business rules for Bookmark access:
     *   - Users with the "USER_ROLE" get only bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" get only the bookmarks marked as "public"
     *
     * @param principal the Principal for the user
     * @return the list of Bookmarks visible to that user
     */
    public List<Bookmark> getBookmarksForUser(Principal principal) {
        List<Bookmark> results = new ArrayList<>();

        User user = getUser(principal);
        if (user != null) {
            if (user.getAuthorities().contains(Authority.ADMIN_AUTHORITY)){
                results = bookmarkDao.getPublicBookmarks();
            } else {
                results = bookmarkDao.getBookmarksByUserId(user.getId());
            }
        }

        return results;
    }


    /**
     * Gets all Bookmarks marked as public. These are visible to any user.
     *
     * @return List of all public bookmarks
     */
    public List<Bookmark> getAllPublicBookmarks() {
        return bookmarkDao.getPublicBookmarks();
    }

    /**
     * Gets all public Bookmarks that are associated with a specific user.
     *
     * @param userId the id of the user to use for filtering
     * @return List of all matching public bookmarks
     */
    public List<Bookmark> getPublicBookmarksByUserId(int userId) {
        return bookmarkDao.getPublicBookmarksByUserId(userId);
    }

    /**
     * Gets all public Bookmarks that contain the filter string in either the title, description or associated tags.
     *
     * @param filterString string to use for filtering bookmarks
     * @return List of all matching public bookmarks
     */
    public List<Bookmark> filterPublicBookmarks(String filterString) {
        return bookmarkDao.filterBookmarks(filterString, true, true);
    }

    /**
     * Gets all Bookmarks marked as flagged.
     * These are assumed to be public and visible to any user.
     *
     * @return List of all flagged bookmarks
     */
    public List<Bookmark> getFlaggedBookmarks() {
        return bookmarkDao.getFlaggedBookmarks();
    }

    /**
     * Gets a single Bookmark by id.
     * The Bookmark must be public or associated to the user.
     *
     * @param bookmarkId the id for the bookmark
     * @param principal the Principal for the user
     * @return the Bookmark if found and accessible to the user, null otherwise
     */
    public Bookmark getBookmarkById(int bookmarkId, Principal principal) {
        Bookmark bookmark = null;

        User user = getUser(principal);
        if (user != null) {
            bookmark = bookmarkDao.getBookmarkById(bookmarkId);
            if (bookmark != null) {
                if (bookmark.getUserId() != user.getId() && bookmark.isPublic() == false) {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }

        return bookmark;
    }

    /**
     * Adds a new Bookmark.
     *
     * Business rules:
     *   - Users with the "ADMIN_ROLE" are not eligible to create Bookmarks
     *   - Users with the "USER_ROLE" may create their own Bookmarks
     *   - Users with the "USER_ROLE" MUST NOT create Bookmarks for (associated to) other users
     *
     * @param newBookmark the information for the new bookmark
     * @param principal the Principal for the user
     * @return the newly created bookmark
     */
    public Bookmark addBookmark(Bookmark newBookmark, Principal principal) {
        Bookmark bookmark = null;

        User user = getUser(principal);
        if (user != null) {
            if (isAdminUser(user)) {
                throw new AccessDeniedException("Access denied");
            }

            // Make sure the Bookmark is associated to this user
            newBookmark.setUserId(user.getId());

            bookmark = bookmarkDao.createBookmark(newBookmark);
            final List<Tag> tags = newBookmark.getTags();
            if (tags != null) {
                for (Tag tag : tags) {
                    bookmarkDao.linkBookmarkTag(bookmark.getBookmarkId(), tag.getId());
                }
            }

        }

        return bookmark;
    }

    /**
     * Removes a single Bookmark by id.
     * Business rules:
     *   - Users with the "ADMIN_ROLE" may only delete public Bookmarks
     *   - Users with the "USER_ROLE" may delete their own Bookmarks
     *   - Users with the "USER_ROLE" MUST NOT delete Bookmarks associated to other users
     *
     * @param bookmarkId the id for the bookmark
     * @param principal the Principal for the user
     * @return true if bookmark is found and removed, false otherwise
     * @throws AccessDeniedException if user does not have required access
     */
    public boolean removeBookmark(int bookmarkId, Principal principal) {
        boolean bookmarkRemoved = false;

        Bookmark bookmark = bookmarkDao.getBookmarkById(bookmarkId);
        if (bookmark != null) {
            User user = getUser(principal);
            if (user != null) {
                if (bookmark.getUserId() == user.getId() || (bookmark.isPublic() && isAdminUser(user))) {
                    int deleteCount = bookmarkDao.deleteBookmarkById(bookmarkId);
                    // Compare return value to verify that a bookmark was removed
                    // May be greater than 1 as may also delete tag associations too
                    bookmarkRemoved = (deleteCount != 0);
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }

        return bookmarkRemoved;
    }

    /**
     * Updates a Bookmark if it exists and is updatable by the user.
     * This method does not modify the Bookmark's createDate or associated tags.
     *
     * Business rules for update access:
     *   - Users with the "ADMIN_ROLE" may update any public bookmark, including the flagged property
     *   - Users with the "USER_ROLE" may flag any public bookmark (set the flagged property to true)
     *   - Users with the "USER_ROLE" may update their own bookmarks (except as noted below)
     *   - Users with the "USER_ROLE" MUST NOT un-flag ANY bookmark (set the flagged property to false)
     *   - Users with the "USER_ROLE" MUST NOT give their bookmark to a different user (change associated user id)
     *   - Users with the "USER_ROLE" MUST NOT update any private bookmarks associated to another user
     *
     * @param modifiedBookmark the data to set on the bookmark
     * @param principal the Principal for the user
     * @return the updated bookmark if found and updated, or null otherwise
     * @throws AccessDeniedException if user does not have required access
     */
    public Bookmark updateBookmark(Bookmark modifiedBookmark, Principal principal) {

        // Verify bookmark exists
        Bookmark existingBookmark = bookmarkDao.getBookmarkById(modifiedBookmark.getBookmarkId());
        if (existingBookmark == null) {
            return null;
        }

        // Check if the user is associated to the bookmark.
        User user = getUser(principal);
        if (user == null) {
            return null;
        }
        Bookmark bookmark = null;

        if (existingBookmark.getUserId() == user.getId()) {
            // Allow update of everything but the flag
            // Use the existingBookmark data for flagged & userId to make sure they are unchanged
            modifiedBookmark.setFlagged(existingBookmark.isFlagged());
            modifiedBookmark.setUserId(existingBookmark.getUserId());
            bookmark = bookmarkDao.updateBookmark(modifiedBookmark);
        }
        else {
            // Check if the bookmark is public
            if (existingBookmark.isPublic()) {
                // If the user is an admin, allow full update
                if (isAdminUser(user)) {
                    bookmark = bookmarkDao.updateBookmark(modifiedBookmark);
                }
                else {
                    // If existing Bookmark is both public and un-flagged, and the update sets flagged true
                    // allow the update but only to set the flag to true
                    if (existingBookmark.isPublic() && !existingBookmark.isFlagged() && modifiedBookmark.isFlagged()) {
                        // Use the existingBookmark data to make sure nothing else is changed
                        existingBookmark.setFlagged(true);
                        bookmark = bookmarkDao.updateBookmark(existingBookmark);
                    }
                    else {
                        throw new AccessDeniedException("Access denied");
                    }
                }
            }
            else {
                throw new AccessDeniedException("Access denied");
            }
        }

        if (bookmark != null && modifiedBookmark.getTags() != null) {
            syncBookmarkTags(bookmark, modifiedBookmark.getTags());
        }

        return bookmark;
    }

    /**
     * Gets a list of tags associated to a specified bookmark.
     *
     * Business rules:
     *   - Users with the "USER_ROLE" may get tags for any bookmarks associated to their user id
     *   - Users with the "USER_ROLE" may get tags for public bookmarks
     *   - Users with the "ADMIN_ROLE" may only get tags a "public" bookmark
     *
     * @param bookmarkId the id for the bookmark
     * @param principal the Principal for the user
     * @return the list of associated tags if the bookmark is found, or null otherwise
     * @throws AccessDeniedException if user does not have required access
     */
    public List<Tag> getBookmarkTags(int bookmarkId, Principal principal) {
        List<Tag> tags = new ArrayList<>();

        Bookmark bookmark = bookmarkDao.getBookmarkById(bookmarkId);
        if (bookmark != null) {
            User user = getUser(principal);
            if (user != null) {
                if (bookmark.getUserId() == user.getId() || bookmark.isPublic()) {
                    tags = tagDao.getTagsByBookmarkId(bookmarkId);
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }

        return tags;
    }

    /**
     * Adds an association between a Bookmark and a Tag.
     *
     * Business rules:
     *   - Users with the "USER_ROLE" may only tag bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" may tag any "public" bookmark
     *
     * @param bookmarkId the id for the bookmark
     * @param tagId the id for the tag
     * @param principal the Principal for the user
     * @return true the bookmark is tagged, or false otherwise
     * @throws AccessDeniedException if user does not have required access
     */
    public boolean addTagToBookmark(int bookmarkId, int tagId, Principal principal) {
        boolean tagAdded = false;

        Bookmark bookmark = bookmarkDao.getBookmarkById(bookmarkId);
        Tag tag = tagDao.getTagById(tagId);
        if (bookmark != null && tag != null) {
            User user = getUser(principal);
            if (user != null) {
                if (bookmark.getUserId() == user.getId() || (bookmark.isPublic() && isAdminUser(user))) {
                    bookmarkDao.linkBookmarkTag(bookmarkId, tagId);
                    tagAdded = true;
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }

        return tagAdded;
    }

    /**
     * Removes an association between a Bookmark and a Tag.
     *
     * Business rules:
     *   - Users with the "USER_ROLE" may only untag bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" may untag any "public" bookmark
     *
     * @param bookmarkId the id for the bookmark
     * @param tagId the id for the tag
     * @param principal the Principal for the user
     * @return true the bookmark is untagged, or false otherwise
     * @throws AccessDeniedException if user does not have required access
     */
    public boolean removeTagFromBookmark(int bookmarkId, int tagId, Principal principal) {
        boolean tagRemoved = false;

        Bookmark bookmark = bookmarkDao.getBookmarkById(bookmarkId);
        Tag tag = tagDao.getTagById(tagId);
        if (bookmark != null && tag != null) {
            User user = getUser(principal);
            if (user != null) {
                if (bookmark.getUserId() == user.getId() || (bookmark.isPublic() && isAdminUser(user))) {
                    bookmarkDao.unlinkBookmarkTag(bookmarkId, tagId);
                    tagRemoved = true;
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }

        return tagRemoved;
    }

    /**
     * Updates the set of Tags associated to a Bookmark.
     *
     * Business rules:
     *   - Users with the "USER_ROLE" may only tag or untag bookmarks associated to their user id
     *   - Users with the "ADMIN_ROLE" may tag or untag any "public" bookmark
     *
     * @param bookmarkId the id for the bookmark
     * @param updatedTags the list of all tags that should be associated to the bookmark
     * @param principal the Principal for the user
     * @return true the bookmark tags are modified, or false otherwise
     * @throws AccessDeniedException if user does not have required access
     * @throws NullPointerException if updatedTags is null
     */
    @Transactional
    public boolean updateBookmarkTags(int bookmarkId, List<Tag> updatedTags, Principal principal) {
        Objects.requireNonNull(updatedTags, "a list of tags must be provided. use an empty list to remove all tags associated with the given bookmark.");
        boolean tagsUpdated = false;

        Bookmark bookmark = bookmarkDao.getBookmarkById(bookmarkId);
        if (bookmark != null) {
            User user = getUser(principal);
            if (user != null) {
                if (bookmark.getUserId() == user.getId() || (bookmark.isPublic() && isAdminUser(user))) {
                    tagsUpdated = syncBookmarkTags(bookmark, updatedTags);
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }

        return tagsUpdated;
    }

    /**
     * sync the set of tags associated with the given bookmark to match the given list of tags
     *
     * @param bookmark the bookmark to update
     * @param updatedTags the list of tags to compare to the existing tags
     * @return true if the list of tags associated to the bookmark was modified,
     *         false otherwise
     */
    private boolean syncBookmarkTags(Bookmark bookmark, List<Tag> updatedTags) {
        boolean tagsUpdated = false;

        // Make sure tags is sorted
        Collections.sort(updatedTags, new Comparator<Tag>() {
            public int compare(Tag o1, Tag o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        // Go through update list, looking for differences
        List<Tag> existing = tagDao.getTagsByBookmarkId(bookmark.getBookmarkId());
        int existingIndex = 0;
        int updateIndex = 0;
        while (updateIndex < updatedTags.size() & existingIndex < existing.size()) {
            String existingTag = existing.get(existingIndex).getName();
            String updateTag = updatedTags.get(updateIndex).getName();
            int compareResult = updateTag.compareTo(existingTag);
            if (compareResult > 0) {
                // update string is after existing, so existing tags need to be removed
                bookmarkDao.unlinkBookmarkTag(bookmark.getBookmarkId(), existing.get(existingIndex).getId());
                tagsUpdated = true;
                // move to the next existing tag, but not the next update tag
                existingIndex++;
            } else if (compareResult < 0) {
                // update string is before existing, so must be new, add it
                bookmarkDao.linkBookmarkTag(bookmark.getBookmarkId(), updatedTags.get(updateIndex).getId());
                tagsUpdated = true;
                // move to the next updateTag, but not the next existing tag
                updateIndex++;
            } else {
                // The values are equal, so leave it be and move on to the next for both
                updateIndex++;
                existingIndex++;
            }
        }

        // Make sure we aren't missing any additions
        for (int i = updateIndex; i < updatedTags.size(); i++) {
            bookmarkDao.linkBookmarkTag(bookmark.getBookmarkId(), updatedTags.get(i).getId());
            tagsUpdated = true;
        }

        // Make sure we aren't missing any deletions
        for (int i = existingIndex; i < existing.size(); i++) {
            bookmarkDao.unlinkBookmarkTag(bookmark.getBookmarkId(), existing.get(i).getId());
            tagsUpdated = true;
        }
        return tagsUpdated;
    }

    /*
     * Helper method to get the User object from the Principal.
     */
    private User getUser(Principal principal) {
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        return user;
    }

    /*
     * Helper method to check if a user is an Admin user.
     */
    private boolean isAdminUser(User user) {
        return user.getAuthorities().contains(Authority.ADMIN_AUTHORITY);
    }
}
