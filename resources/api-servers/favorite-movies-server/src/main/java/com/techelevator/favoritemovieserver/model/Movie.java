package com.techelevator.favoritemovieserver.model;

import java.time.LocalDate;

public class Movie {
	private int movieId;
	private String title;
	private LocalDate releaseDate;
	private String rating;
	private String director;
	private boolean userLiked;

	public Movie() { }

	public Movie(int movieId, String title, LocalDate releaseDate, String rating, String director) {
		this.movieId = movieId;
		this.title = title;
		this.releaseDate = releaseDate;
		this.rating = rating;
		this.director = director;
		this.userLiked = false;
	}
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public boolean isUserLiked() {
		return userLiked;
	}

	public void setUserLiked(boolean userLiked) {
		this.userLiked = userLiked;
	}
}
