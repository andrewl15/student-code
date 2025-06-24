import { describe, test, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import AuthorCard from "../src/components/AuthorCard/AuthorCard";

vi.mock("../src/services/BookService");

describe("Step Two", () => {
  const mockAuthor = {
    id: 1,
    firstName: "Author",
    lastName: "One",
    fullName: "Author One",
    about: "Bio of Author One",
    image: "/mock-author1.jpg",
  };

  test("AuthorCard receives author prop and displays the correct name", () => {
    render(<AuthorCard author={mockAuthor} />);
    expect(screen.getByText(mockAuthor.fullName)).toBeInTheDocument();
  });

  test("AuthorCard receives author prop and displays the correct about", () => {
    render(<AuthorCard author={mockAuthor} />);
    expect(screen.getByText(mockAuthor.about)).toBeInTheDocument();
  });

  test("AuthorCard receives author prop and displays author profile image", () => {
    render(<AuthorCard author={mockAuthor} />);
    const img = screen.getByRole("img");
    expect(img).toHaveAttribute(
      "src",
      expect.stringContaining(mockAuthor.image)
    );
  });
});