package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Bookmark;

import java.util.List;

/**
 * DAO class for interacting with the bookmark information in the data store.

 * The DAO pattern allows us to encapsulate and abstract interactions with a data store.
 * By using an interface, we are able to change the implementing class to support different
 * types of data sources. For example, we might have a DAO implementation that uses a database,
 * or we might have one that gets information from a file store. The methods are the same, but
 * the implementation, or the logic within those methods may be very different.
 */
public interface BookmarkDao {

    /**
     * Get a bookmark from the datastore with the specified id.
     * If the id is not found, return null.
     *
     * @param bookmarkId The id of the bookmark to return.
     * @return The matching Bookmark object, or null if the bookmarkId is not found.
     */
    Bookmark getBookmarkById(int bookmarkId);

    /**
     * Get all bookmarks from the datastore ordered alphabetically by title.
     *
     * @return List of all Bookmark objects, or an empty list if no Tags are found.
     */
    List<Bookmark> getBookmarks();

    /**
     * Get all bookmarks from the datastore for a specified user.
     * Bookmarks are ordered alphabetically by title.
     *
     * @param userId the id of the user
     * @return List of all the Bookmark objects associated to the user, or an empty list if none are found.
     */
    List<Bookmark> getBookmarksByUserId(int userId);

    /**
     * Get all public bookmarks from the datastore that are associated to a specific user.
     * Bookmarks are ordered alphabetically by title.
     *
     * @param userId the id of the user
     * @return List of all the public Bookmark objects associated to the user, or an empty list if none are found.
     */
    List<Bookmark> getPublicBookmarksByUserId(int userId);

    /**
     * Get all bookmarks from the datastore that are marked as public.
     * Bookmarks are ordered alphabetically by title.
     *
     * @return List of all the public Bookmark objects, or an empty list if none are found.
     */
    List<Bookmark> getPublicBookmarks();

    /**
     * Get all bookmarks from the datastore that are marked as flagged.
     * Bookmarks are ordered alphabetically by title.
     *
     * @return List of all the flagged Bookmark objects, or an empty list if none are found.
     */
    List<Bookmark> getFlaggedBookmarks();

    /**
     * Gets all bookmarks from the datastore that contain the filter criteria in either the title, description,
     * associated tag names, or associated user display name.
     * @param searchTerm the string to filter by
     * @param publicOnly true if only public bookmarks should be included, false otherwise
     * @param useWildCard true if the searchTerm is wrapped by SQL wild card characters
     * @return List of the filtered Bookmark objects, or an empty list if none are found
     */
    List<Bookmark> filterBookmarks(String searchTerm, boolean publicOnly, boolean useWildCard);

    /**
     * Adds a new bookmark to the datastore.
     *
     * @param newBookmark the Bookmark object to add.
     * @return The added Bookmark object with its new id and any default values filled in.
     */
    Bookmark createBookmark(Bookmark newBookmark);

    /**
     * Removes a bookmark and any tag associations from the datastore.
     *
     * @param bookmarkId The id of the Bookmark to remove. If the id is not found, no error will occur.
     * @return count of bookmarks removed
     */
    int deleteBookmarkById(int bookmarkId);

    /**
     * Update a bookmark in the datastore.
     * Only the title, url, description, and isPublic properties may be updated. Other values are ignored.
     *
     * @param modifiedBookmark The Bookmark object to update.
     */
    Bookmark updateBookmark(Bookmark modifiedBookmark);

    /**
     * Add a tag association to a bookmark.
     * @param bookmarkId The id of the bookmark to associate to the tag.
     * @param tagId The id of the tag to associate to the bookmark.
     */
    void linkBookmarkTag(int bookmarkId, int tagId);

    /**
     * Remove a tag association from a bookmark.
     * @param bookmarkId The id of the bookmark to unlink from the tag.
     * @param tagId The id of the tag to unlink from the bookmark.
     */
    void unlinkBookmarkTag(int bookmarkId, int tagId);
}
