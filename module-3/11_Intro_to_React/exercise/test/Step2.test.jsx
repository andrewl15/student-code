import { beforeEach, describe, expect, test } from "vitest";
import { render, screen } from "@testing-library/react";
import MoviesView from "../src/views/MoviesView/MoviesView";
import MovieService from "../src/services/MovieService";

const allMovies = MovieService.getMovies();

describe("Step Two", () => {
  beforeEach(() => {
    render(<MoviesView />);
  });

  test("Should have a table", () => {
    const tableElement = screen.getByRole("table");
    expect(tableElement).toBeDefined();
    expect(tableElement.tagName).toBe("TABLE");
  });

  test("Should have a table head", () => {
    const tableElement = screen.getByRole("table");
    expect(tableElement).toBeDefined();

    const firstTableRowElement = tableElement.children[0];
    expect(firstTableRowElement).toBeDefined();
    expect(firstTableRowElement.tagName).toBe("THEAD");
  });

  test("Should have 5 columns in table", () => {
    const tableRowElements = screen.getAllByRole("row");
    expect(tableRowElements).toBeDefined();
    expect(tableRowElements.length).toBeGreaterThan(0); // need to have at least one row to determine the number of columns
    expect(tableRowElements[0].children.length).toBeGreaterThanOrEqual(5); // won't penalize if there's more than five
  });

  test("Should have all movie data in table body", () => {
    const tableElement = screen.getByRole("table");
    const allRows = Array.from(tableElement.rows);
    const tableRows = allRows.filter((row) => row.children[0].tagName !== "TH"); // filter header row
    expect(tableRows.length).toBe(allMovies.length);
    for (const tableRow of tableRows) {
      expect(tableRow.textContent).toBeDefined();
      expect(
        allMovies.some((movie) => tableRow.textContent.includes(movie.title))
      ).toBeTruthy();
      expect(
        allMovies.some((movie) =>
          tableRow.textContent.includes(movie.releaseDate)
        )
      ).toBeTruthy();
      expect(
        allMovies.some((movie) => tableRow.textContent.includes(movie.rating))
      ).toBeTruthy();
      expect(
        allMovies.some((movie) => tableRow.textContent.includes(movie.director))
      ).toBeTruthy();
      expect(
        allMovies.some((movie) =>
          tableRow.textContent.includes(movie.userLiked ? "Liked" : "")
        )
      ).toBeTruthy();
    }
  });
});