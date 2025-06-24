package com.techelevator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MorningExercisesTest
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class LectureTest {

    private Lecture exercises = new Lecture();

    @Test
    public void test01_testReturnNotOne() {
        assertNotEquals(1, exercises.returnNotOne(), "Value returned shouldn't be one");
    }

    @Test
    public void test02_testReturnNotHalf() {
        assertNotEquals(0.5, exercises.returnNotHalf(), 0.001, "Value returned shouldn't be 0.5");
    }

    @Test
    public void test03_testReturnName() {
        assertNotNull(exercises.returnName(), "Value returned shouldn't be null");
    }

    @Test
    public void test04_testReturnDoubleOfTwo() {
        assertTrue(Double.class.isInstance(exercises.returnDoubleOfTwo()), "Value returned should be a double");
        assertEquals(2.0, exercises.returnDoubleOfTwo(), 0, "Value returned should be 2.0");
    }

    @Test
    public void test05_testReturnNameOfLanguage() {
        Object result = exercises.returnNameOfLanguage();
        assertEquals("Java", result, "Value returned should be 'Java'");
    }

    @Test
    public void test06_testReturnTrueFromIf() {
        assertTrue(exercises.returnTrueFromIf(), "If statement should return true");
    }

    @Test
    public void test07_testReturnTrueWhenOneEqualsOne() {
        assertTrue(exercises.returnTrueWhenOneEqualsOne(), "If statement should return true");
    }

    @Test
    public void test08_testReturnWhenGreaterThanFive() {
        assertTrue(exercises.returnTrueWhenGreaterThanFive(6), "We should return true when parameter is greater than five");
        assertFalse(exercises.returnTrueWhenGreaterThanFive(5), "We should return false when parameter is five");
        assertFalse(exercises.returnTrueWhenGreaterThanFive(4), "We should return false when parameter is smaller than five");
    }

    @Test
    public void test09_testReturnWhenGreaterThanFiveInOneLine() {
        assertTrue(exercises.returnTrueWhenGreaterThanFiveInOneLine(6), "We should return true when parameter is greater than five");
        assertFalse(exercises.returnTrueWhenGreaterThanFiveInOneLine(5), "We should return false when parameter is five");
        assertFalse(exercises.returnTrueWhenGreaterThanFiveInOneLine(4), "We should return false when parameter is smaller than five");
    }

    @Test
    public void test10_testReturnNumberAfterAddThreeAndAddFive() {
        assertEquals(9, exercises.returnNumberAfterAddThreeAndAddFive(1, true, true),
                "We should add three and five when both are true");
        assertEquals(4, exercises.returnNumberAfterAddThreeAndAddFive(1, true, false),
                "We should add three when passed true false");
        assertEquals(6, exercises.returnNumberAfterAddThreeAndAddFive(1, false, true),
                "We should add five when passed false true");
        assertEquals(1, exercises.returnNumberAfterAddThreeAndAddFive(1, false, false),
                "We should return the original number when both are false");
    }

    @Test
    public void test11_testReturnFizzIfThree() {
        assertEquals("Fizz", exercises.returnFizzIfThree(3));
        assertEquals("", exercises.returnFizzIfThree(6));
    }

    @Test
    public void test12_testReturnFizzIfThreeUsingTernary() {
        assertEquals("Fizz", exercises.returnFizzIfThreeUsingTernary(3));
        assertEquals("", exercises.returnFizzIfThreeUsingTernary(6));
    }

    @Test
    public void test13_testReturnFizzOrBuzzOrNothing() {
        assertEquals("Fizz", exercises.returnFizzOrBuzzOrNothing(3));
        assertEquals("Buzz", exercises.returnFizzOrBuzzOrNothing(5));
        assertEquals("", exercises.returnFizzOrBuzzOrNothing(15));
    }

    @Test
    public void test14_testReturnAdultOrMinor() {
        assertEquals("Adult", exercises.returnAdultOrMinor(20));
        assertEquals("Adult", exercises.returnAdultOrMinor(18));
        assertEquals("Minor", exercises.returnAdultOrMinor(17));
    }

    @Test
    public void test15_testReturnAdultOrMinorAgain() {
        assertEquals("Adult", exercises.returnAdultOrMinorAgain(20));
        assertEquals("Adult", exercises.returnAdultOrMinorAgain(18));
        assertEquals("Minor", exercises.returnAdultOrMinorAgain(17));
    }

    @Test
    public void test16_testReturnAdultOrMinorOrTeen() {
        assertEquals("Adult", exercises.returnAdultOrMinorOrTeen(18));
        assertEquals("Teen", exercises.returnAdultOrMinorOrTeen(17));
        assertEquals("Teen", exercises.returnAdultOrMinorOrTeen(13));
        assertEquals("Minor", exercises.returnAdultOrMinorOrTeen(12));
    }

    @Test
    public void test17_testReturnPizzaCost() {
        assertEquals(9.99, exercises.returnPizzaCost('s', 1), 0.001);
        assertEquals(10.99, exercises.returnPizzaCost('m', 1), 0.001);
        assertEquals(11.99, exercises.returnPizzaCost('s', 3), 0.001);
        assertEquals(10.99, exercises.returnPizzaCost('l', 0), 0.001);
        assertEquals(13.99, exercises.returnPizzaCost('l', 4), 0.001);
    }
}
