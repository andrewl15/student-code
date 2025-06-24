import { describe, test, expect, beforeEach, vi } from "vitest";
import { render, screen, waitFor, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import BooksView from "../src/views/BooksView/BooksView";
import BookService from "../src/services/BookService";
import BookCard from "../src/components/BookCard/BookCard";

vi.mock("../src/services/BookService");

describe("Step Five", () => {
  const mockBook = {
    id: 1,
    title: "Mock Book 1",
    authors: [{ id: 22, fullName: "Author One" }],
    genres: [{ id: 333, name: "Fiction" }],
    overview: "Mock overview",
    coverImage: "/mock-cover.jpg",
  };

  const [likeTextRegEx, likedTextRegex] = [/🤍 like/i, /❤️ liked/i];

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

  test("BookCard receives callback prop and calls it when button is clicked", () => {
    const mockOnLike = vi.fn();

    render(<BookCard book={mockBook} onLike={mockOnLike} />);

    // Click the Like button
    const likeButtons = screen.getAllByText(likeTextRegEx);
    const likeButton = likeButtons[0];
    fireEvent.click(likeButton);

    // Ensure onLike was called with the correct argument
    expect(mockOnLike).toHaveBeenCalledWith(1);

    // Ensure button text changes to 'Liked'
    expect(likeButton).toHaveTextContent(likedTextRegex);

    // Click again to unlike
    fireEvent.click(likeButton);
    expect(mockOnLike).toHaveBeenCalledWith(-1);
  });

  test('The likedBooks count is initially 0', async () => {
    render(<BooksView />);

    // Wait until loading disappears and books are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    expect(screen.getByText(/liked books: 0/i)).toBeInTheDocument();
  });

  test('Clicking Like button increases likedBooks count', async () => {
    render(<BooksView />);

    // Wait until loading disappears and books are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    const likeButtons = screen.getAllByText(/🤍 like/i);
    fireEvent.click(likeButtons[0]);

    await waitFor(() => {
      expect(screen.getByText(/liked books: 1/i)).toBeInTheDocument();
    });
  });

  test('Clicking Like again decreases likedBooks count', async () => {
    render(<BooksView />);

    // Wait until loading disappears and books are rendered
    await waitFor(() => {
      expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
    });

    const likeButtons = screen.getAllByText(/🤍 like/i);
    const likeButton = likeButtons[0];
    fireEvent.click(likeButton); // Like once

    // ensure the liked count increased
    await waitFor(() => {
      expect(screen.getByText(/liked books: 1/i)).toBeInTheDocument();
    });

    fireEvent.click(likeButton); // Unlike

    // ensure the liked count decreased
    await waitFor(() => {
      expect(screen.getByText(/liked books: 0/i)).toBeInTheDocument();
    });
  });

});
