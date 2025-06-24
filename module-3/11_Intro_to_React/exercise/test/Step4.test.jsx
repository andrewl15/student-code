import { beforeEach, describe, expect, test } from "vitest";
import { render, screen } from "@testing-library/react";
import MoviesView from "../src/views/MoviesView/MoviesView";
import MovieService from "../src/services/MovieService";

const allMovies = MovieService.getMovies();
const likedMovies = [3, 4, 7, 9];

describe("Step Four", () => {
  beforeEach(() => {
    render(<MoviesView />);
  });

  test("Should have a Like/Unlike button in each row", () => {
    for (const movie of allMovies) {
      const movieTitleElement = screen.getByText(movie.title);
      const movieRow = movieTitleElement.parentElement;
      const buttonElement = movieRow.querySelector("button");
      expect(buttonElement).toBeDefined();
      expect(buttonElement.tagName).toBe("BUTTON");
      expect(buttonElement.classList).toBeDefined();
      expect(buttonElement.classList.value).toContain("likeButton");
      expect(buttonElement.textContent).toBeDefined();
      if (likedMovies.includes(movie.movieId)) {
        expect(buttonElement.textContent).toBe("Unlike");
      } else {
        expect(buttonElement.textContent).toBe("Like");
      }
    }
  });
});