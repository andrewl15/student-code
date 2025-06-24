package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.model.Tag;

import java.util.List;

/**
 * DAO class for interacting with the tag information in the data store.

 * The DAO pattern allows us to encapsulate and abstract interactions with a data store.
 * By using an interface, we are able to change the implementing class to support different
 * types of data sources. For example, we might have a DAO implementation that uses a database,
 * or we might have one that gets information from a file store. The methods are the same, but
 * the implementation, or the logic within those methods may be very different.
 */
public interface TagDao {

    /**
     * Get all tags from the datastore ordered alphabetically by tag name.
     *
     * @return List of all Tag objects, or an empty list if no Tags are found.
     */
    List<Tag> getTags();

    /**
     * Get a tag from the datastore with the specified id.
     * If the id is not found, return null.
     *
     * @param tagId The id of the tag to return.
     * @return The matching Tag object, or null if the tagId is not found.
     */
    Tag getTagById(int tagId);

    /**
     * Gets a list of tags from the datastore associated to specified bookmark id.
     * If the id is not found, return null.
     *
     * @param bookmarkId The bookmark id associated to the tags to return.
     * @return A list of matching Tag objects, or an empty list if no Tags are found.
     */
    List<Tag> getTagsByBookmarkId(int bookmarkId);

    /**
     * Adds a new tag to the datastore.
     *
     * @param newTag the Tag object to add.
     * @return The added Tag object with its new id value filled in.
     */
    Tag createTag(Tag newTag);

    /**
     * Updates an existing tag.
     *
     * @param tag the Tag data to update.
     */
    Tag updateTag(Tag tag);

    /**
     * Removes a tag from the datastore.
     *
     * @param tagId The id of the Tag to remove. If the id is not found, no error will occur.
     * @throws org.springframework.dao.DataIntegrityViolationException if the Tag is associated to a Bookmark
     */
    int deleteTagById(int tagId);

}
