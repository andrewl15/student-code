import { beforeEach, describe, expect, test, vi } from "vitest";
import { render, cleanup } from "@testing-library/react";
import * as UserContextFile from "../src/context/UserContext";
import "@testing-library/jest-dom";
import App from "../src/App";

describe("Step One", () => {
  beforeEach(() => {
    cleanup();
    vi.resetAllMocks();
  });

  test("Should define UserContext", () => {
    const context = UserContextFile.UserContext;
    if (!context) {
      expect(
        context,
        "UserContext not defined in UserContext.jsx"
      ).toBeDefined();
    }

    const consumer = UserContextFile.UserContext?.Consumer;
    if (!consumer) {
      expect(consumer, "UserContext not defined as context").toBeDefined();
    }

    const contextDefaultValue =
      UserContextFile.UserContext.Consumer._context._currentValue;
    if (contextDefaultValue !== null) {
      expect(
        contextDefaultValue,
        "UserContext default value should be null"
      ).toBeNull();
    }
  });

  test("Add UserContext Provider to App.jsx", async () => {
    render(<App />); // test if App component renders without error, like if UserContext.Provider is added but not imported
    const appContents = App.toString();
    if (!appContents.includes("UserContext.Provider")) {
      expect(
        appContents,
        "UserContext.Provider not found in App component"
      ).toContain("UserContext.Provider");
    }
  });
});
