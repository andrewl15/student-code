import { beforeEach, describe, expect, test, vi } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import "@testing-library/jest-dom";
import MovieTable from "../src/components/MovieTable/MovieTable";
import { UserContext } from "../src/context/UserContext";

describe("Step Three", () => {
  beforeEach(() => {
    cleanup();
  });
  const testUser = {
    id: 1,
    username: "test_user",
  };

  const testMovies = [
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
  ];

  const onLike = vi.fn();

  function testIfIsLoggedInIsRedefined() {
    const movieTableContents = MovieTable.toString();
    if (!movieTableContents.includes("isLoggedIn")) {
      expect(movieTableContents, "MovieTable must define isLoggedIn").toContain(
        "isLoggedIn"
      );
    }
    if (movieTableContents.includes("isLoggedIn = false")) {
      expect(
        movieTableContents,
        "MovieTable must set isLoggedIn based on if user is logged in or not"
      ).not.toContain("isLoggedIn = false");
    }
  }

  test('User Context controls "Like" visibility', () => {
    testIfIsLoggedInIsRedefined();

    // test with user context, expect "Like" button to be visible
    render(
      <UserContext.Provider value={testUser}>
        <MovieTable movies={testMovies} onLike={onLike} />
      </UserContext.Provider>
    );

    const tableElement = screen.getByRole("table");
    expect(tableElement).toBeDefined();

    expect(screen.getAllByText("Like")).toBeDefined();
  });

  test('No user hides "Like" column', () => {
    testIfIsLoggedInIsRedefined();

    // test without user context, expect "Like" button to be hidden
    render(
      <UserContext.Provider value={null}>
        <MovieTable movies={testMovies} onLike={onLike} />
      </UserContext.Provider>
    );

    const tableElement = screen.getByRole("table");
    expect(tableElement).toBeDefined();

    expect(screen.queryAllByText("Like?")).toHaveLength(0);
  });
});