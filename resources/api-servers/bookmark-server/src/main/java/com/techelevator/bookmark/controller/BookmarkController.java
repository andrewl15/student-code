package com.techelevator.bookmark.controller;

import com.techelevator.bookmark.exception.DaoException;
import com.techelevator.bookmark.model.Bookmark;
import com.techelevator.bookmark.model.Tag;
import com.techelevator.bookmark.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * The BookmarkController is a class for handling HTTP Requests related to creating, reading,
 * updating, and deleting Bookmarks.
 *
 * It depends on an instance of BookmarkService for interacting with DAOs and handling business logic.
 * This is provided through dependency injection.
 */
@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping( path = "/bookmarks" )
public class BookmarkController {

    private BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @RequestMapping( method = RequestMethod.GET )
    public List<Bookmark> getAllBookmarks(Principal principal) {
        List<Bookmark> bookmarks = new ArrayList<>();

        try {
            bookmarks = bookmarkService.getBookmarksForUser(principal);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return bookmarks;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping( path = "/public", method = RequestMethod.GET )
    public List<Bookmark> getPublicBookmarks(@RequestParam(defaultValue  = "") String search) {
        List<Bookmark> bookmarks = new ArrayList<>();

        try {
            if (search != null) {
                bookmarks = bookmarkService.filterPublicBookmarks(search);
            }
            else {
                bookmarks = bookmarkService.getAllPublicBookmarks();
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return bookmarks;
    }

    @RequestMapping( path = "/users/{userId}", method = RequestMethod.GET )
    public List<Bookmark> getPublicBookmarksByUserId(@PathVariable int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();

        try {
            bookmarks = bookmarkService.getPublicBookmarksByUserId(userId);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return bookmarks;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping( path = "/flagged", method = RequestMethod.GET )
    public List<Bookmark> getFlaggedBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>();

        try {
            bookmarks = bookmarkService.getFlaggedBookmarks();
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return bookmarks;
    }

    @RequestMapping( path = "/{bookmarkId}", method = RequestMethod.GET)
    public Bookmark getBookmarkById(@PathVariable int bookmarkId, Principal principal) {
        Bookmark bookmark = null;

        try {
            bookmark = bookmarkService.getBookmarkById(bookmarkId, principal);
            if (bookmark == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found");
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return bookmark;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @RequestMapping( method = RequestMethod.POST )
    public Bookmark addBookmark(@Valid @RequestBody Bookmark newBookmark, Principal principal) {
        Bookmark bookmark = null;

        try {
            bookmark = bookmarkService.addBookmark(newBookmark, principal);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return bookmark;
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping( path = "/{bookmarkId}", method = RequestMethod.DELETE )
    public void removeBookmark(@PathVariable int bookmarkId, Principal principal) {
        try {
            if (bookmarkService.removeBookmark(bookmarkId, principal) == false) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found");
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping( path = "/{bookmarkId}", method = RequestMethod.PUT )
    public Bookmark updateBookmark(@PathVariable int bookmarkId, @Valid @RequestBody Bookmark modifiedBookmark, Principal principal) {
        Bookmark bookmark = null;

        try {
            // Make sure the bookmark id is set
            modifiedBookmark.setBookmarkId(bookmarkId);
            bookmark = bookmarkService.updateBookmark(modifiedBookmark, principal);
            if (bookmark == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found");
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return bookmark;
    }

    @RequestMapping( path="/{bookmarkId}/tags", method = RequestMethod.GET)
    public List<Tag> getBookmarkTags(@PathVariable int bookmarkId, Principal principal) {
        List<Tag> tags = new ArrayList<>();

        try {
            tags = bookmarkService.getBookmarkTags(bookmarkId, principal);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return tags;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping( path="/{bookmarkId}/tags/{tagId}", method = RequestMethod.POST)
    public void addTagToBookmark(@PathVariable int bookmarkId, @PathVariable int tagId, Principal principal) {
        try {
            boolean updated = bookmarkService.addTagToBookmark(bookmarkId, tagId, principal);
            if (updated == false) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark or Tag not found");
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping( path="/{bookmarkId}/tags", method = RequestMethod.PUT)
    public List<Tag> updateBookmarkTags(@PathVariable int bookmarkId,  @RequestBody List<Tag>tags, Principal principal) {
        List<Tag> updatedTags = new ArrayList<>();

        try {
            boolean updated = bookmarkService.updateBookmarkTags(bookmarkId, tags, principal);
            updatedTags = bookmarkService.getBookmarkTags(bookmarkId, principal);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return updatedTags;
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping( path="/{bookmarkId}/tags/{tagId}", method = RequestMethod.DELETE)
    public void removeTagFromBookmark(@PathVariable int bookmarkId, @PathVariable int tagId, Principal principal) {
        try {
            boolean updated = bookmarkService.removeTagFromBookmark(bookmarkId, tagId, principal);
            if (updated == false) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark or Tag not found");
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
