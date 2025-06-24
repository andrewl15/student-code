package com.techelevator.auctions.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AuctionValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    public AuctionValidationTest() {

    }

    @BeforeEach
    public void should_have_no_violations() {

        // This @BeforeEach method asserts validate() method has no regressions prior to each test.

        Auction auction = new Auction("TEST_TITLE", "TEST_DESC", "TEST_USER", 99);

        Set<ConstraintViolation<Auction>> violations = validator.validate(auction);

        assertTrue(violations.isEmpty(), "@BeforeEach method failed. Shouldn't have any validation errors on a valid auction.");
    }


    @Test
    public void titleShouldNotBeBlank() {
        Auction auction = new Auction("", "TEST_DESC", "TEST_USER", 99);
        Set<ConstraintViolation<Auction>> violations = validator.validate(auction);
        assertEquals(1, violations.size(), "Incorrect number of validation errors for blank title.");

        ConstraintViolation<Auction> violation = violations.iterator().next();
        assertEquals("The title field must not be blank.", violation.getMessage(), "Validation message for blank title is incorrect.");
        assertEquals("title", violation.getPropertyPath().toString(), "Incorrect property tested for blank title.");
    }

    @Test
    public void descriptionShouldNotBeBlank() {
        Auction auction = new Auction("TEST_TITLE", "", "TEST_USER", 99);
        Set<ConstraintViolation<Auction>> violations = validator.validate(auction);
        assertEquals(1, violations.size(), "Incorrect number of validation errors for blank description.");

        ConstraintViolation<Auction> violation = violations.iterator().next();
        assertEquals("The description field must not be blank.", violation.getMessage(), "Validation message for blank description is incorrect.");
        assertEquals("description", violation.getPropertyPath().toString(), "Incorrect property tested for blank description.");
    }

    @Test
    public void userShouldNotBeBlank() {
        Auction auction = new Auction("TEST_TITLE", "TEST_DESC", "", 99);
        Set<ConstraintViolation<Auction>> violations = validator.validate(auction);
        assertEquals(1, violations.size(), "Incorrect number of validation errors for blank user.");

        ConstraintViolation<Auction> violation = violations.iterator().next();
        assertEquals("The user field must not be blank.", violation.getMessage(), "Validation message for blank user is incorrect.");
        assertEquals("user", violation.getPropertyPath().toString(), "Incorrect property tested for blank user.");
    }

    @Test
    public void currentBidShouldBeGreaterThanZero() {
        Auction auction = new Auction("TEST_TITLE", "TEST_DESC", "TEST_USER", 0);
        Set<ConstraintViolation<Auction>> violations = validator.validate(auction);
        assertEquals(1, violations.size(), "Incorrect number of validation errors for currentBid == 0.");

        ConstraintViolation<Auction> violation = violations.iterator().next();
        assertEquals("The currentBid field must be greater than 0.", violation.getMessage(), "Validation message for currentBid == 0 is incorrect.");
        assertEquals("currentBid", violation.getPropertyPath().toString(), "Incorrect property tested for currentBid == 0.");
    }

    @Test
    public void currentBidShouldBeGreaterThanZeroWhenNotSet() {
        Auction auction = new Auction();
        auction.setTitle("TEST_TITLE");
        auction.setDescription("TEST_DESC");
        auction.setUser("TEST_USER");
        Set<ConstraintViolation<Auction>> violations = validator.validate(auction);
        assertEquals(violations.size(), 1, "Incorrect number of validation errors for currentBid not set.");

        ConstraintViolation<Auction> violation = violations.iterator().next();
        assertEquals("The currentBid field must be greater than 0.", violation.getMessage(), "Validation message for currentBid not set is incorrect.");
        assertEquals("currentBid", violation.getPropertyPath().toString(), "Incorrect property tested for currentBid not set.");
    }

}
