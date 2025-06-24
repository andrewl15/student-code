package com.techelevator.bookmark.dao;

import com.techelevator.bookmark.exception.DaoException;
import com.techelevator.bookmark.model.Bookmark;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * The JdbcBookmarkDao class is used for interacting with the bookmark information in the datastore.

 * While the DAO pattern allows us to encapsulate and abstract interactions with a data store,
 * this implementation class is specifically used to access data from a SQL database using JDBC.

 * This DAO supports full CRUD (Create, Read, Update, Delete) for Bookmarks and also allows
 * the addition and deletion of Tag associations. Bookmarks are always associated to a single User.
 */
@Component
public class JdbcBookmarkDao implements BookmarkDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookmarkDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Bookmark getBookmarkById(int bookmarkId) {
        Bookmark bookmark = null;
        
        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE bookmark.bookmark_id = ? " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookmarkId);
            if (results.next()) {
                bookmark = mapRowToBookmark(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bookmark;

    }

    @Override
    public List<Bookmark> getBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>();
        
        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Bookmark bookmark = mapRowToBookmark(results);
                bookmarks.add(bookmark);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bookmarks;
    }


    @Override
    public List<Bookmark> getBookmarksByUserId(int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE bookmark.user_id = ? " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Bookmark bookmark = mapRowToBookmark(results);
                bookmarks.add(bookmark);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> getPublicBookmarksByUserId(int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE is_public AND bookmark.user_id = ? " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Bookmark bookmark = mapRowToBookmark(results);
                bookmarks.add(bookmark);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> getPublicBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE is_public " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Bookmark bookmark = mapRowToBookmark(results);
                bookmarks.add(bookmark);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> getFlaggedBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>();

        // A Bookmark may not have any associated Tags, so a LEFT JOIN is required 
        String sql = "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                "WHERE is_flagged " +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Bookmark bookmark = mapRowToBookmark(results);
                bookmarks.add(bookmark);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> filterBookmarks(String searchTerm, boolean publicOnly, boolean useWildCard) {
        List<Bookmark> bookmarks = new ArrayList<>();

        // Set up the WHERE to optionally filter for public bookmarks only
        String sqlWhere = publicOnly ? "WHERE is_public " : "";

        String sql = "Select * from (" +
                "SELECT bookmark.bookmark_id, bookmark.user_id, COALESCE(app_user.display_name, app_user.username) as display_name, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, " +
                "bookmark.is_flagged, bookmark.create_date, string_agg( tag.name, ', ' ORDER BY tag.name ) as all_tags FROM bookmark " +
                "JOIN app_user on bookmark.user_id = app_user.user_id " +
                "LEFT JOIN bookmark_tag on bookmark.bookmark_id = bookmark_tag.bookmark_id " +
                "LEFT JOIN tag ON bookmark_tag.tag_id = tag.tag_id " +
                sqlWhere +
                "GROUP BY bookmark.bookmark_id, bookmark.user_id, app_user.display_name, app_user.username, bookmark.title, bookmark.url, bookmark.description, bookmark.is_public, bookmark.is_flagged, bookmark.create_date " +
                "ORDER BY title" +
            ") AS bookmarks " +
            "WHERE (title ILIKE ? OR description ILIKE ? OR all_tags ILIKE ? OR display_name ILIKE ?);";

        if (useWildCard) {
            // add % to the searchTerm string to sub into the query
            searchTerm = '%' + searchTerm + '%';
        }

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchTerm, searchTerm, searchTerm, searchTerm);
            while (results.next()) {
                Bookmark bookmark = mapRowToBookmark(results);
                bookmarks.add(bookmark);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bookmarks;
    }

    @Override
    public Bookmark createBookmark(Bookmark bookmark) {
        Bookmark newBookmark = null;

        try {
            int newBookmarkId;
            if (bookmark.getCreateDate() == null) {
                String sql = "INSERT INTO bookmark (user_id, title, url, description, is_public) VALUES" +
                        "(?, ?, ?, ?, ?) RETURNING bookmark_id;";
                newBookmarkId = jdbcTemplate.queryForObject(sql, int.class, bookmark.getUserId(), bookmark.getTitle(),
                        bookmark.getUrl(), bookmark.getDescription(), bookmark.isPublic());
            } else {
                String sql = "INSERT INTO bookmark (user_id, title, url, description, is_public, create_date) VALUES" +
                        "(?, ?, ?, ?, ?, ?) RETURNING bookmark_id;";
                newBookmarkId = jdbcTemplate.queryForObject(sql, int.class, bookmark.getUserId(), bookmark.getTitle(),
                        bookmark.getUrl(), bookmark.getDescription(), bookmark.isPublic(), bookmark.getCreateDate());
            }
            newBookmark =  getBookmarkById(newBookmarkId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newBookmark;
    }

    @Override
    public int deleteBookmarkById(int bookmarkId) {
        int count;

        try {
            // Delete associations to Tags, then delete Bookmark
            // Note - Multiple statements in a single batch are automatically wrapped in a transaction -
            // these two statements will either succeed or fail as a unit.
            count = jdbcTemplate.update("DELETE FROM bookmark_tag WHERE bookmark_id=?", bookmarkId);
            count += jdbcTemplate.update("DELETE FROM bookmark WHERE bookmark_id=?;", bookmarkId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return count;
    }

    @Override
    public Bookmark updateBookmark(Bookmark modifiedBookmark) {
        Bookmark updatedBookmark = null;

        String sql = "UPDATE bookmark SET title=?, url=?, description=?, is_public=?, is_flagged=? WHERE bookmark_id=?;";

        try {
            int rowsAffected = jdbcTemplate.update(sql, modifiedBookmark.getTitle(), modifiedBookmark.getUrl(), modifiedBookmark.getDescription(),
                    modifiedBookmark.isPublic(), modifiedBookmark.isFlagged(), modifiedBookmark.getBookmarkId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedBookmark = getBookmarkById(modifiedBookmark.getBookmarkId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedBookmark;
    }

    @Override
    public void linkBookmarkTag(int bookmarkId, int tagId){
        String sql = "INSERT INTO bookmark_tag (bookmark_id, tag_id) VALUES (?, ?);";

        try {
            jdbcTemplate.update(sql, bookmarkId, tagId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void unlinkBookmarkTag(int bookmarkId, int tagId){
        String sql = "DELETE FROM bookmark_tag WHERE bookmark_id=? AND tag_id=?;";

        try {
            jdbcTemplate.update(sql, bookmarkId, tagId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    /*
     * Helper method to convert a SqlRowSet into a Bookmark object.
     */
    private Bookmark mapRowToBookmark(SqlRowSet rs) {
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(rs.getInt("bookmark_id"));
        bookmark.setUserId(rs.getInt("user_id"));
        bookmark.setUserDisplayName(rs.getString("display_name"));
        bookmark.setUrl(rs.getString("url"));
        bookmark.setTitle(rs.getString("title"));
        bookmark.setDescription(rs.getString("description"));
        bookmark.setCreateDate(rs.getDate("create_date").toLocalDate());
        bookmark.setPublic(rs.getBoolean("is_public"));
        bookmark.setFlagged(rs.getBoolean("is_flagged"));
        bookmark.setAllTags(rs.getString("all_tags"));
        return bookmark;
    }
}
