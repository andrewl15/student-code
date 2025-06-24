package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.exception.DaoException;
import com.techelevator.bookmark.model.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * The JdbcTagDao class is used for interacting with the tag information in the datastore.

 * While the DAO pattern allows us to encapsulate and abstract interactions with a data store,
 * this implementation class is specifically used to access data from a SQL database using JDBC.

 * This DAO supports full CRUD (Create, Read, Update, Delete) for Tags.
 * Tags are associated to zero or more Bookmarks.
 */
@Component
public class JdbcTagDao implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTagDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Tag> getTags() {
        List<Tag> allTags = new ArrayList<>();

        String sql = "SELECT tag_id, name FROM tag ORDER BY name;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Tag tag = mapRowToTag(results);
                allTags.add(tag);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return allTags;
    }

    @Override
    public Tag getTagById(int tagId) {
        Tag tag = null;

        String sql = "SELECT tag_id, name FROM tag WHERE tag_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tagId);
            if (results.next()) {
                tag = mapRowToTag(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return tag;
    }


    @Override
    public List<Tag> getTagsByBookmarkId(int bookmarkId) {
        List<Tag> tagList = new ArrayList<>();

        String sql = "SELECT tag.tag_id, name FROM tag " +
                "JOIN bookmark_tag on bookmark_tag.tag_id = tag.tag_id " +
                "WHERE bookmark_tag.bookmark_id = ? " +
                "ORDER BY name;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookmarkId);
            while (results.next()) {
                Tag tag = mapRowToTag(results);
                tagList.add(tag);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return tagList;
    }

    @Override
    public Tag createTag(Tag tag) {
        Tag newTag = null;

        String sql = "INSERT INTO tag (name) VALUES (?) RETURNING tag_id;";

        try {
            int tagId = jdbcTemplate.queryForObject(sql, int.class, tag.getName());
            newTag = getTagById(tagId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newTag;
    }

    @Override
    public Tag updateTag(Tag tag) {
        Tag updatedTag = null;

        String sql = "UPDATE tag SET name = ? WHERE tag_id=?;";

        try {
            int rowsAffected = jdbcTemplate.update(sql, tag.getName(), tag.getId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedTag = getTagById(tag.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedTag;
    }

    @Override
    public int deleteTagById(int tagId) {
        int count;

        String sql = "DELETE FROM tag WHERE tag_id=?;";

        try {
            count = jdbcTemplate.update(sql, tagId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return count;
    }

    /*
     * Helper method to convert a SqlRowSet into a Tag object.
     */
    private Tag mapRowToTag(SqlRowSet rs) {
        Tag tag = new Tag();
        tag.setId(rs.getInt("tag_id"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}
