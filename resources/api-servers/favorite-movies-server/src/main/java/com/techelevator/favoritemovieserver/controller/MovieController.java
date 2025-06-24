package com.techelevator.favoritemovieserver.controller;

import com.techelevator.favoritemovieserver.exception.DaoException;
import com.techelevator.favoritemovieserver.model.Movie;
import com.techelevator.favoritemovieserver.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/movies")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Movie> getMovies(Principal principal) {
		List<Movie> movies;

		try {
			movies = movieService.getMovies(principal);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

		return movies;
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(path = "/{movieId}/like", method = RequestMethod.PUT)
	public void addLikedMovie(@PathVariable int movieId, Principal principal) {
		try {
			movieService.addLikedMovie(movieId, principal);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(path = "/{movieId}/like", method = RequestMethod.DELETE)
	public void removeLikedMovie(@PathVariable int movieId, Principal principal) {
		try {
			movieService.removeLikedMovie(movieId, principal);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Get the liked movies for the authenticated user.
	 * @param principal The authenticated user.
	 * @return A list of liked movies.
	 */
	@RequestMapping(path = "/likes", method = RequestMethod.GET)
	public List<Movie> getUserLikes(Principal principal) {
		List<Movie> likes;

		try {
			likes = movieService.getUserLikes(principal);
		}
		catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

		return likes;
	}
}
