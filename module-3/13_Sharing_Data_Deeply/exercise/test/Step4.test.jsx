import { beforeEach, describe, expect, test, vi } from "vitest";
import { render, screen, waitFor, fireEvent, cleanup } from "@testing-library/react";
import "@testing-library/jest-dom";
import MoviesView from "../src/views/MoviesView";
import MovieService from "../src/services/MovieService";
import { UserContext } from "../src/context/UserContext";

describe("Step Four", () => {
  const testUser = {
    id: 1,
    username: "test_user",
  };

  const mockMovieData = {
    data: [
      {
        movieId: 1,
        title: "Title 1",
        releaseDate: "1970-01-01",
        rating: "G",
        director: "Director 1",
        userLiked: false,
      },
      {
        movieId: 2,
        title: "Title 2",
        releaseDate: "2025-01-01",
        rating: "PG",
        director: "Director 2",
        userLiked: false,
      },
    ],
  };

  const mockMovieDataFirstLiked = structuredClone(mockMovieData);
  mockMovieDataFirstLiked.data[0].userLiked = true;

  vi.mock("../src/services/MovieService");

  beforeEach(() => {
    cleanup();
    vi.resetAllMocks();

    MovieService.likeMovie.mockResolvedValue();
    MovieService.unlikeMovie.mockResolvedValue();
  });

  test("Should succeed liking a movie", async () => {
    // Mock the initial movie data, both movies unliked
    MovieService.getMovies.mockResolvedValueOnce(mockMovieData);

    render(
      <UserContext.Provider value={testUser}>
        <MoviesView />
      </UserContext.Provider>
    );

    // Wait until loading disappears and movies are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    // Both movies should be in a state of unliked initially
    expect(screen.getAllByText("Like")).toHaveLength(2);

    // Mock the movie data after liking the first movie, doing this before button click
    MovieService.getMovies.mockResolvedValueOnce(mockMovieDataFirstLiked);

    // Click the Like button
    const likeButtons = screen.getAllByText("Like");
    const likeButton = likeButtons[0];
    fireEvent.click(likeButton);

    // Wait until loading disappears and movies are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    // Check if the movie is liked
    expect(screen.getByText("Unlike")).toBeInTheDocument(); // first movie
    expect(screen.getByText("Like")).toBeInTheDocument(); // second movie
  });

  test("Should gracefully handle errors when liking a movie", async () => {
    // Mock the initial movie data, both movies unliked
    MovieService.getMovies.mockResolvedValueOnce(mockMovieData);

    // Mock the error when liking a movie
    MovieService.likeMovie.mockRejectedValue(new Error("Failed to like movie"));

    render(
      <UserContext.Provider value={testUser}>
        <MoviesView />
      </UserContext.Provider>
    );

    // Wait until loading disappears and movies are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    // Both movies should be in a state of unliked initially
    expect(screen.getAllByText("Like")).toHaveLength(2);

    // Click the Like button
    const likeButtons = screen.getAllByText("Like");
    const likeButton = likeButtons[0];
    fireEvent.click(likeButton);

    // Wait until error notification appears
    await waitFor(() => {
      expect(screen.getByRole("alert")).toBeInTheDocument();
    });

    // Check if the movie is still un-liked
    expect(screen.getAllByText("Like")).toHaveLength(2); // both movies should be unliked
  });

  test("Should succeed un-liking a movie", async () => {
    // Mock the initial movie data, first movie liked
    MovieService.getMovies.mockResolvedValueOnce(mockMovieDataFirstLiked);

    render(
      <UserContext.Provider value={testUser}>
        <MoviesView />
      </UserContext.Provider>
    );

    // Wait until loading disappears and movies are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    // Should be one movie liked and one unliked initially
    expect(screen.getByText("Unlike")).toBeInTheDocument(); // first movie
    expect(screen.getByText("Like")).toBeInTheDocument(); // second movie

    // Mock the movie data after un-liking the first movie, doing this before button click
    MovieService.getMovies.mockResolvedValueOnce(mockMovieData);

    // Click the Unlike button
    const unlikeButtons = screen.getAllByText("Unlike");
    const unlikeButton = unlikeButtons[0];
    fireEvent.click(unlikeButton);

    // Wait until loading disappears and movies are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    // Check if the movie is un-liked
    expect(screen.getAllByText("Like")).toHaveLength(2); // both movies should be unliked
  });

  test("Should gracefully handle errors when un-liking a movie", async () => {
    // Mock the initial movie data, first movie liked
    MovieService.getMovies.mockResolvedValueOnce(mockMovieDataFirstLiked);

    // Mock the error when un-liking a movie
    MovieService.unlikeMovie.mockRejectedValue(
      new Error("Failed to unlike movie")
    );

    render(
      <UserContext.Provider value={testUser}>
        <MoviesView />
      </UserContext.Provider>
    );

    // Wait until loading disappears and movies are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    // Should be one movie liked and one unliked initially
    expect(screen.getByText("Unlike")).toBeInTheDocument(); // first movie
    expect(screen.getByText("Like")).toBeInTheDocument(); // second movie

    // Click the Unlike button
    const unlikeButtons = screen.getAllByText("Unlike");
    const unlikeButton = unlikeButtons[0];
    fireEvent.click(unlikeButton);

    // Wait until error notification appears
    await waitFor(() => {
      expect(screen.getByRole("alert")).toBeInTheDocument();
    });

    // Check if the movie is still liked
    expect(screen.getByText("Unlike")).toBeInTheDocument(); // first movie
    expect(screen.getByText("Like")).toBeInTheDocument(); // second movie
  });
});
