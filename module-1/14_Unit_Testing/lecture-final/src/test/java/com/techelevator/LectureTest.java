package com.techelevator;

import org.junit.jupiter.api.AfterEach; // The @AfterEach annotation is used to execute a method after every test
import org.junit.jupiter.api.Assertions; // The Assertions class has static assertion methods for validating test results
import org.junit.jupiter.api.BeforeEach; // The @BeforeEach annotation is used to execute a method before every test
import org.junit.jupiter.api.Test; // The @Test annotation is used to label methods that should be run as tests
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

/* Like all other Java code, unit testing code is defined within a class.
 * Each test class will typically contain all the unit tests for a single "unit" of production code.
 *
 * If the "unit" is a single class from the production code, then the convention is that the test class belongs
 *  to the same package as the class under test and the name of the test class is the same as the production
 *  class with "Test" at the end. For example, the test class for the production class "Foo" would be "FooTest"
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class LectureTest {

	/*
	 * If a method is annotated with @BeforeEach, it will be executed immediately prior to every test.
	 * It is intended to be used when there is repetitive setup (i.e. "Arrange") that is performed by several tests
	 */
	@BeforeEach
	public void setup() {
		System.out.println("setup");
	}

	/*
	 * If a method is annotated with @AfterEach, it will be executed immediately after every test.
	 * It is intended to be used when there is repetitive cleanup that is performed by several tests
	 * (e.g. deleting temp files, rolling back database transactions, etc.)
	 */
	@AfterEach
	public void teardown() {
		System.out.println("teardown");
	}

	/*
	 * Each test is implemented as a method with the @Test annotation. When the JUnit framework is invoked,
	 * it looks for these @Test annotations on the test class and runs such methods as tests.
	 *
	 * You'll notice that the naming convention used in the test methods below deviates from standard Java
	 * method naming conventions by using underscores between words instead of camelCase. This is sometimes
	 * common in tests as methods tend to have longer names making camelCase more challenging to read.
	 * However, you can also use camelCase for test method names. Follow the norm at your organization.
	 *
	 * Regardless of whether you use camelCase or underscores, your method names should be very descriptive
	 * to the point of being overly verbose. This is fine because you'll never write code to invoke your
	 * test methods, they're only invoked by the JUnit framework, hence descriptive is better than concise.
	 *
	 * Test methods always:
	 * - are public
	 * - return void
	 * - take no arguments
	 */
	@Test
	public void length_returns_the_number_of_characters_in_a_String() {
		System.out.println("length_returns_the_number_of_characters_in_a_String");  // FOR DEMONSTRATION PURPOSES ONLY,
																					// don't do this in your own tests

		/*
		 * The assertEquals method validates that two values are equal and fails the test if they are not equal
		 */

		String theString = "Java"; // Arrange
		int length = theString.length(); // Act
		Assertions.assertEquals(4, length); // Assert
	}

	@Test
	public void startsWith_returns_true_if_a_string_starts_with_the_specified_characters() {
		System.out.println("startsWith_returns_true_if_a_string_starts_with_the_specified_characters"); // FOR
																										// DEMONSTRATION
																										// PURPOSES
																										// ONLY, don't
																										// do this in
																										// your own
																										// tests

		/*
		 * The assertTrue method validates that the boolean value provided is true and fails the test if it is false.
		 */

		String theString = "Hello World!"; // Arrange
		boolean startsWithHello = theString.startsWith("Hello"); // Act

		/*
		 * every assert method allows the last parameter to be a String that contains a message
		 * that should be displayed when the assertion fails
		 *
		 * This is particularly helpful with assertTrue as otherwise the failure output would simply state
		 * "Expected: true Actual: false", which sometimes isn't much help in figuring out what went wrong
		 */
		Assertions.assertTrue(startsWithHello, "String did not start with Hello as expected."); // Assert
	}

	@Test
	public void this_test_fails_every_time() {
		System.out.println("this_test_fails_every_time");   // FOR DEMONSTRATION PURPOSES ONLY,
															// don't do this in your tests

		/* The fail method will cause a test to fail */

		Assertions.fail("This is how I can force a test to fail");
	}

	public void this_method_is_not_a_test_because_it_does_not_have_the_Test_annotation() {
		// This method will not be run by Junit, because it's not a test
	}
}
