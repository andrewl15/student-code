import { beforeEach, describe, expect, test } from "vitest";
import { render, screen } from "@testing-library/react";
import MoviesView from "../src/views/MoviesView/MoviesView";
import MovieService from "../src/services/MovieService";

const allMovies = MovieService.getMovies();

describe("Step Three", () => {
  beforeEach(() => {
    render(<MoviesView />);
  });

  test("Should have a styled table", () => {
    const tableElement = screen.getByRole("table");
    expect(tableElement).toBeDefined();
    expect(tableElement.tagName).toBe("TABLE");
    expect(tableElement.classList).toBeDefined();
    expect(tableElement.classList.value).toContain("tableFull");
  });

  test('Should have movie title in table cell with class "movieTitle"', () => {
    for (const movie of allMovies) {
      const movieTitleElement = screen.getByText(movie.title);
      expect(movieTitleElement).toBeDefined();
      expect(movieTitleElement.tagName).toBe("TD");
      expect(movieTitleElement.classList).toBeDefined();
      expect(movieTitleElement.classList.value).toContain("movieTitle");
    }
  });
});