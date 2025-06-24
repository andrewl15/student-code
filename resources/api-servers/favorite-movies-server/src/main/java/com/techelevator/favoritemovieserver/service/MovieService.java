package com.techelevator.favoritemovieserver.service;

import com.techelevator.favoritemovieserver.dao.MovieDao;
import com.techelevator.favoritemovieserver.dao.UserDao;
import com.techelevator.favoritemovieserver.model.Movie;
import com.techelevator.favoritemovieserver.model.User;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class MovieService {

	private final MovieDao movieDao;
	private final UserDao userDao;

	public MovieService(MovieDao movieDao, UserDao userDao) {
		this.movieDao = movieDao;
		this.userDao = userDao;
	}

	public List<Movie> getMovies(Principal principal) {
		List<Movie> movies = movieDao.getMovies();
		User user = getUser(principal);
		if (user != null) {
			List<Integer> likedMovieIds = movieDao.getUserLikedIds(user.getId());
			for (Movie movie : movies) {
				if (likedMovieIds.contains(movie.getMovieId())) {
					movie.setUserLiked(true);
				}
			}
		}
		return movies;
	}

	public void addLikedMovie(int movieId, Principal principal) {
		User user = getUser(principal);
		movieDao.linkMovieUser(movieId, user.getId());
	}

	public void removeLikedMovie(int movieId, Principal principal) {
		User user = getUser(principal);
		movieDao.unlinkMovieUser(movieId, user.getId());
	}

	public List<Movie> getUserLikes(Principal principal) {
		User user = getUser(principal);
		if (user == null) {
			return null;
		}
		return movieDao.getUserLikedMovies(user.getId());
	}

	/*
	 * Helper method to get the User object from the Principal.
	 */
	private User getUser(Principal principal) {
		if (principal == null) {
			return null;
		}
		String username = principal.getName();
		return userDao.getUserByUsername(username);
	}

}
