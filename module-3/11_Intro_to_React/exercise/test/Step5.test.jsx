import { beforeEach, describe, expect, test } from "vitest";
import { render, screen } from "@testing-library/react";
import MoviesView from "../src/views/MoviesView/MoviesView";
import MovieService from "../src/services/MovieService";

const allMovies = MovieService.getMovies();
const likedMovies = [3, 4, 7, 9];

describe("Step Five", () => {
  beforeEach(() => {
    render(<MoviesView />);
  });

  test('Should have a button with text "Unlike" after clicking "Like"', () => {
    for (const nonLikedMovie of allMovies.filter(
      (movie) => !likedMovies.includes(movie.movieId)
    )) {
      const movieTitleElement = screen.getByText(nonLikedMovie.title);
      const movieRow = movieTitleElement.parentElement;
      const buttonElement = movieRow.querySelector("button");
      expect(buttonElement).toBeDefined();
      expect(buttonElement.tagName).toBe("BUTTON");
      expect(buttonElement.textContent).toBe("Like");
      buttonElement.click();
      expect(buttonElement.textContent).toBe("Unlike");
    }
  });

  test('Should have a button with text "Like" after clicking "Unlike"', () => {
    for (const likedMovie of allMovies.filter((movie) =>
      likedMovies.includes(movie.movieId)
    )) {
      const movieTitleElement = screen.getByText(likedMovie.title);
      const movieRow = movieTitleElement.parentElement;
      const buttonElement = movieRow.querySelector("button");
      expect(buttonElement).toBeDefined();
      expect(buttonElement.tagName).toBe("BUTTON");
      expect(buttonElement.textContent).toBe("Unlike");
      buttonElement.click();
      expect(buttonElement.textContent).toBe("Like");
    }
  });
});