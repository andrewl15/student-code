import { describe, test, expect, beforeEach, vi } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import BookService from "../src/services/BookService";
import AuthorsView from "../src/views/AuthorsView/AuthorsView";

vi.mock("../src/services/BookService");

describe("Step One", () => {
  beforeEach(() => {
    vi.resetAllMocks();

    const mockAuthors = {
      data: [
        {
          id: 1,
          fullName: "Author One",
          about: "Bio of Author One",
          image: "/mock-author1.jpg",
        },
        {
          id: 2,
          fullName: "Author Two",
          about: "Bio of Author Two",
          image: "/mock-author2.jpg",
        },
      ],
    };

    BookService.getAuthors.mockResolvedValue(mockAuthors);
  });

  test("useEffect fetches authors and updates state", async () => {
    render(<AuthorsView />);

    // Ensure loading message appears first
    expect(screen.getByText(/loading/i)).toBeInTheDocument();

    // Wait until loading disappears and authors are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
      expect(screen.getByText(/total authors: 2/i)).toBeInTheDocument();
    });
  });

  test("AuthorsView uses AuthorCard to render books", async () => {
    render(<AuthorsView />);

    // Wait until loading disappears and books are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    const bookCards = screen.getAllByRole("article");
    expect(bookCards.length).toBe(2);
    bookCards.forEach((card) => {
      expect(card).toHaveAttribute(
        "class",
        expect.stringContaining("authorCard")
      );
    });
  });
});