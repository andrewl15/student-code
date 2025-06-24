import { describe, test, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import BookCard from "../src/components/BookCard/BookCard";

vi.mock("../src/services/BookService");

describe("Step Four", () => {
  const mockBook = {
    id: 1,
    title: "Mock Book 1",
    authors: [{ id: 22, fullName: "Author One" }],
    genres: [{ id: 333, name: "Fiction" }],
    overview: "Mock overview",
    coverImage: "/mock-cover.jpg",
  };

  const [likeTextRegEx] = [/🤍 like/i];

  test("BookCard receives book prop and displays the correct title", () => {
    render(<BookCard book={mockBook} onLike={() => {}} />);
    expect(screen.getByText(mockBook.title)).toBeInTheDocument();
  });

  test("BookCard receives book prop and displays the correct overview", () => {
    render(<BookCard book={mockBook} onLike={() => {}} />);
    expect(screen.getByText(mockBook.overview)).toBeInTheDocument();
  });

  test("BookCard receives book prop and displays book cover image", () => {
    render(<BookCard book={mockBook} onLike={() => {}} />);
    const img = screen.getByRole("img");
    expect(img).toHaveAttribute(
      "src",
      expect.stringContaining(mockBook.coverImage)
    );
  });

  test("BookCard has a like button", () => {
    render(<BookCard book={mockBook} onLike={() => {}} />);
    expect(
      screen.getByRole("button", { name: likeTextRegEx })
    ).toBeInTheDocument();
  });
});