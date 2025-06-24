package com.techelevator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class LectureTest {

    Lecture exercises = new Lecture();

    @Test
    public void test01_testReturnArray() {
        assertArrayEquals(new int[] { 80, 8080, 443 }, exercises.returnArray());
    }

    @Test
    public void test02_testReturnFirstElement() {
        assertEquals(80, exercises.returnFirstElement(), "That's not the first element in that array");
    }

    @Test
    public void test03_testReturnLastElement() {
        assertEquals(443, exercises.returnLastElement(), "That's not the last element");
    }

    @Test
    public void test04_testReturnFirstElementOfParam() {
        assertEquals(5, exercises.returnFirstElementOfParam(new int[] { 5, 10, 15 }), "That's not the first element from {5, 10, 15}");
        assertEquals(10, exercises.returnFirstElementOfParam(new int[] { 10, 20, 30, 40, 50 }), "That's not the first element from {10, 20, 30, 40, 50}");
    }

    @Test
    public void test05_testReturnLastElementOfParam() {
        assertEquals(15, exercises.returnLastElementOfParam(new int[] { 5, 10, 15 }), "That's not the last element from {5, 10, 15}");
        assertEquals(50, exercises.returnLastElementOfParam(new int[] { 10, 20, 30, 40, 50 }), "That's not the last element from {10, 20, 30, 40, 50}");
    }

    @Test
    public void test06_testReturnVariableFromBlock() {
        assertEquals(15, exercises.returnVariableFromBlock(3));
    }

    @Test
    public void test07_testReturnOperationInBlock() {
        assertTrue(exercises.returnOperationInBlock(), "Not true yet!");
    }

    @Test
    public void test08_testReturnVariableInScope() {
        assertEquals(5.0, exercises.returnInScopeVariable(), 0.001, "Not that one, try again.");
    }

    @Test
    public void test09_testReturnCounterFromLoop() {
        assertTrue(exercises.returnCounterFromLoop(), "Not true yet!");
    }

    @Test
    public void test10_testReturnCorrectCount() {
        assertTrue(exercises.returnCorrectCount(), "Not Correct Yet!");
    }

    @Test
    public void test11_testReturnCorrectCountTimes() {
        assertTrue(exercises.returnCountCorrectTimes(), "Not Correct Yet!");
    }

    @Test
    public void test12_testReturnSumEveryOtherNumber() {
        assertTrue(exercises.returnSumEveryOtherNumber(),"Not Correct Yet!");
    }
}
