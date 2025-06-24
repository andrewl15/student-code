import { beforeEach, describe, expect, test } from "vitest";
import { render, screen } from "@testing-library/react";
import App from "../src/App";

describe("Step One", () => {
  beforeEach(() => {
    render(<App />);
  });

  test("Should contain AppHeader component", () => {
    try {
      expect(screen.getByText(/Favorite Movies App/i)).toBeDefined();
    } catch {
      throw new Error(
        "AppHeader component not found in App component - if you have added it to App, make sure you did not change the header text in AppHeader"
      );
    }

    const appHeader = screen.getAllByRole("banner");
    expect(appHeader).toBeDefined();
    const thisAppHeader = appHeader.filter(
      (header) => header.id === "app-header"
    )[0];
    expect(thisAppHeader.tagName).toBe("HEADER");
    expect(thisAppHeader.parentElement.tagName).toBe("DIV");
  });

  test("Should contain MoviesView component", () => {
    try {
      expect(screen.getByText(/Movies List/i)).toBeDefined();
    } catch {
      throw new Error(
        "MoviesView component not found in App component - If you have added it to App, make sure you did not change the header text in MoviesView"
      );
    }

    const appHeader = screen.getAllByRole("banner");
    expect(appHeader).toBeDefined();
    const thisAppHeader = appHeader.filter(
      (header) => header.id === "movie-list-header"
    )[0];
    expect(thisAppHeader.tagName).toBe("HEADER");
    expect(thisAppHeader.parentElement.tagName).toBe("MAIN");
  });
});