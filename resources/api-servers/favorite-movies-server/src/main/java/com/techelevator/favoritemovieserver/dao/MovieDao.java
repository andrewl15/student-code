package com.techelevator.favoritemovieserver.dao;

import com.techelevator.favoritemovieserver.model.Movie;

import java.util.List;

public interface MovieDao {

	List<Movie> getMovies();

	void linkMovieUser(int movieId, int userId);

	List<Integer> getUserLikedIds(int userId);

	void unlinkMovieUser(int movieId, int userId);

	List<Movie> getUserLikedMovies(int userId);
}
