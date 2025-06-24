package com.techelevator.favoritemovieserver.dao;

import com.techelevator.favoritemovieserver.exception.DaoException;
import com.techelevator.favoritemovieserver.model.Movie;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMovieDao implements MovieDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Movie> getMovies() {
		List<Movie> movies = new ArrayList<>();
		String sql = "SELECT movie_id, title, release_date, rating, director FROM movie;";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				Movie movie = mapRowToMovie(results);
				movies.add(movie);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return movies;
	}

	@Override
	public void linkMovieUser(int movieId, int userId) {
		String sql = "INSERT INTO movie_user_like (movie_id, user_id) VALUES (?, ?);";
		try {
			jdbcTemplate.update(sql, movieId, userId);
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
	}

	@Override
	public List<Integer> getUserLikedIds(int userId) {
		List<Integer> likedMovieIds;
		String sql = "SELECT movie_id FROM movie_user_like WHERE user_id = ?;";
		try {
			likedMovieIds = jdbcTemplate.queryForList(sql, Integer.class, userId);
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return likedMovieIds;
	}

	@Override
	public void unlinkMovieUser(int movieId, int userId) {
		String sql = "DELETE FROM movie_user_like WHERE movie_id = ? AND user_id = ?;";
		try {
			jdbcTemplate.update(sql, movieId, userId);
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
	}

	@Override
	public List<Movie> getUserLikedMovies(int userId) {
		List<Movie> likes = new ArrayList<>();
		String sql = "SELECT m.movie_id, title, release_date, rating, director " +
				"FROM movie m " +
				"JOIN movie_user_like f ON m.movie_id = f.movie_id " +
				"WHERE f.user_id = ?;";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
			while (results.next()) {
				Movie movie = mapRowToMovie(results);
				likes.add(movie);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return likes;
	}

	private Movie mapRowToMovie(SqlRowSet results) {
		Movie movie = new Movie();
		movie.setMovieId(results.getInt("movie_id"));
		movie.setTitle(results.getString("title"));
		if (results.getDate("release_date") != null) {
			movie.setReleaseDate(results.getDate("release_date").toLocalDate());
		}
		movie.setRating(results.getString("rating"));
		movie.setDirector(results.getString("director"));
		return movie;
	}

}
