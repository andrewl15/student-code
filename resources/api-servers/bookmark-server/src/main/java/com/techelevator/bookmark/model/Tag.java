package com.techelevator.bookmark.model;

/**
 * Model class representing a Tag. Tags are associated to Bookmarks.
 */
public class Tag {

    private int id;
    private String name;

    public Tag() {}

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int tagId) {
        this.id = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
