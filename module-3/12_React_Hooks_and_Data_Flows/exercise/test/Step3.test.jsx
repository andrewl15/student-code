import { describe, test, expect, beforeEach, vi } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import BooksView from "../src/views/BooksView/BooksView";
import BookService from "../src/services/BookService";

vi.mock("../src/services/BookService");

describe("Step Three", () => {
  beforeEach(() => {
    vi.resetAllMocks();

    const mockBooks = {
      data: [
        {
          id: 1,
          title: "Mock Book 1",
          authors: [{ id: 22, fullName: "Author One" }],
          genres: [{ id: 333, name: "Fiction" }],
          overview: "Mock overview",
          coverImage: "/mock-cover.jpg",
        },
        {
          id: 2,
          title: "Mock Book 2",
          authors: [{ id: 44, fullName: "Author Two" }],
          genres: [{ id: 555, name: "Non-Fiction" }],
          overview: "Mock overview 2",
          coverImage: "/mock-cover-2.jpg",
        },
      ],
    };

    BookService.getBooks.mockResolvedValue(mockBooks);
  });

  test("useEffect fetches books and updates state", async () => {
    render(<BooksView />);

    // Ensure loading message appears first
    expect(screen.getByText(/loading/i)).toBeInTheDocument();

    // Wait until loading disappears and books are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
      expect(screen.getByText(/total books: 2/i)).toBeInTheDocument();
    });
  });

  test("BooksView uses BookCard to render books", async () => {
    render(<BooksView />);

    // Wait until loading disappears and books are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    const bookCards = screen.getAllByRole("article");
    expect(bookCards.length).toBe(2);
    bookCards.forEach((card) => {
      expect(card).toHaveAttribute(
        "class",
        expect.stringContaining("bookCard")
      );
    });
  });
});