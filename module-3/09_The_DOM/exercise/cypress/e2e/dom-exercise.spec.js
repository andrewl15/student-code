describe("dom-exercise", () => {
    before(() => {
      // Load the page or fixture into the Cypress browser
      cy.visit("../shopping-list/index.html");
    });
  
    it("shopping list title is added to the DOM", () => {
      cy.get("#title").should("have.text", "My Shopping List");
    });
  
    it("groceries are added to the DOM", () => {
      cy.get("#groceries > li").should("have.length", 10);
    });
  
    it("should have items in the list, but 0 items completed", () => {
      cy.get("#groceries > li.completed").should("have.length", 0);
      cy.get("#groceries > li").should("have.length.greaterThan", 0);
    });
  
    it("clicking the button marks all of the items complete", () => {
      cy.get("#mark-all-complete").click(); // Simulate the button click
      cy.get("#groceries > li.completed").should("have.length", 10);
    });
});
