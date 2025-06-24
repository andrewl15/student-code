package com.techelevator.bookmark.model;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * Model class representing a Bookmark.
 *   - Bookmarks are associated to a User.
 *   - They may also optionally have associated Tags.
 *   - A public Bookmark is visible to all Users.
 *   - A flagged Bookmark is marked for review by an admin
 */
public class Bookmark {

    // Not required - will be empty on new Bookmarks
    private int bookmarkId;

    private int userId;
    private String userDisplayName;
    private List<Tag> tags;

    @NotBlank( message = "The field 'title' is required.")
    private String title;
    @NotBlank( message = "The field 'url' is required.")
    private String url;

    // Optional
    private String description;
    // Optional - defaults to current date
    private LocalDate createDate;
    // Optional - defaults to false
    private boolean isPublic;
    // Optional - defaults to false
    private boolean isFlagged;

    // Optional - Comma separated list of all associated tags
    // NOTE: When bookmarks are read, the DAO will join to the tag table and pull the associated tag names to save an
    // additional lookup later. When you display a bookmark, you almost always want to display the associated tags.
    private String allTags;

    public Bookmark() {}

    public Bookmark(int bookmarkId, int userId, String userDisplayName, String title, String url, String description, boolean isPublic, boolean isFlagged, String createDate, String allTags) {
        this(bookmarkId, userId, userDisplayName, title, url, description, isPublic, isFlagged, createDate, allTags, null);
    }

    public Bookmark(int bookmarkId, int userId, String userDisplayName, String title, String url, String description, boolean isPublic, boolean isFlagged, String createDate, String allTags, List<Tag> tags) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.userDisplayName = userDisplayName;
        this.title = title;
        this.url = url;
        this.description = description;
        this.isPublic = isPublic;
        this.isFlagged = isFlagged;
        this.createDate = LocalDate.parse(createDate);
        this.allTags = allTags;
        this.tags = tags;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public String getAllTags() {
        return allTags;
    }

    public void setAllTags(String allTags) {
        this.allTags = allTags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
