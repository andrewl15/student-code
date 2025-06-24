import { beforeEach, describe, expect, test } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import "@testing-library/jest-dom";
import Nav from "../src/components/Nav/Nav";
import { UserContext } from "../src/context/UserContext";

describe("Step Two", () => {
  beforeEach(() => {
    cleanup();
  });
  const testUser = {
    id: 1,
    username: "test_user",
  };

  function testIfIsLoggedInIsRedefined() {
    const navContents = Nav.toString();
    if (!navContents.includes("isLoggedIn")) {
      expect(navContents, "Nav must define isLoggedIn").toContain("isLoggedIn");
    }
    if (navContents.includes("isLoggedIn = false")) {
      expect(
        navContents,
        "Nav must set isLoggedIn based on if user is logged in or not"
      ).not.toContain("isLoggedIn = false");
    }
  }

  test('Show "My Likes" and "Logout" for logged in user', () => {
    testIfIsLoggedInIsRedefined();

    // test with user context, expect My Likes and Logout to be visible and Login to be hidden
    render(
      <UserContext.Provider value={testUser}>
        <Nav />
      </UserContext.Provider>
    );

    const navElement = screen.getByRole("navigation");
    expect(navElement).toBeDefined();

    expect(screen.getByText("My Likes")).toBeDefined();
    expect(screen.getByText("Logout")).toBeDefined();
    expect(screen.queryByText("Login")).toBeNull();
  });

  test('Show "Login" for no logged in user', () => {
    testIfIsLoggedInIsRedefined();

    // test without user context, expect Login to be visible and My Likes and Logout to be hidden
    render(
      <UserContext.Provider value={null}>
        <Nav />
      </UserContext.Provider>
    );

    const navElement = screen.getByRole("navigation");
    expect(navElement).toBeDefined();

    expect(screen.queryByText("My Likes")).toBeNull();
    expect(screen.queryByText("Logout")).toBeNull();
    expect(screen.getByText("Login")).toBeDefined();
  });
});