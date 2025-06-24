package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TriangleWallTest
 */
public class TriangleWallTest {

    private static Class<?> triangle;

    @BeforeAll
    public static void setup() {
        try {
            triangle = Class.forName("com.techelevator.TriangleWall");
        } catch (ClassNotFoundException e) {
            fail("com.techelevator.TriangleWall class not found.");
        }
    }

    @Test
    public void testCreateTriangleWall() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = triangle.getConstructor(String.class, String.class, int.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 1, 2);
            assertNotNull(sut, "TriangleWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must have a four arg constructor: public TriangleWall(String, String, int, int)");
        }

        try {
            assertEquals("TEST", sut.getClass().getMethod("getName").invoke(sut), "getName() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must contain method: getName()");
        }

        try {
            assertEquals("TESTCOLOR", sut.getClass().getMethod("getColor").invoke(sut), "getColor() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must contain method: getColor()");
        }

        try {
            assertEquals(1, sut.getClass().getMethod("getBase").invoke(sut), "getBase() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must contain method: getBase()");
        }

        try {
            assertEquals(2, sut.getClass().getMethod("getHeight").invoke(sut), "getHeight() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must contain method: getHeight()");
        }
    }

    @Test
    public void testIsAWall() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Constructor<?> constructor = triangle.getConstructor(String.class, String.class, int.class, int.class);
            Object sut = constructor.newInstance("TEST", "TESTCOLOR", 1, 2);
            assertNotNull(sut, "TriangleWall could not be instantiated.");
            assertEquals("com.techelevator.Wall", sut.getClass().getSuperclass().getTypeName(),
                    "TriangleWall must extend Wall.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must have a four arg constructor: public TriangleWall(String, String, int, int)");
        }
    }

    @Test
    public void testGetArea() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = triangle.getConstructor(String.class, String.class, int.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 2, 3);
            assertNotNull(sut, "TriangleWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall constructor not found.");
        }

        try {
            assertEquals(3, sut.getClass().getMethod("getArea").invoke(sut),
                    "getArea() did not return the expected value for a 2x3 triangle.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must contain method: getArea()");
        }
    }

    @Test
    public void testGetAreaRoundsDown() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = triangle.getConstructor(String.class, String.class, int.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 3, 3);
            assertNotNull(sut, "TriangleWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall constructor not found.");
        }

        try {
            assertEquals(4, sut.getClass().getMethod("getArea").invoke(sut),
                    "getArea() did not return the expected value for a 3x3 triangle.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must contain method: getArea()");
        }
    }

    @Test
    public void testGetString() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = triangle.getConstructor(String.class, String.class, int.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 1, 3);
            assertNotNull(sut, "TriangleWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall constructor not found.");
        }

        try {
            assertEquals("TEST (1x3) triangle", sut.getClass().getMethod("toString").invoke(sut),
                    "toString() did not return the expected value for a 1x3 triangle.");
        } catch (NoSuchMethodException e) {
            fail("TriangleWall must contain method: toString()");
        }
    }
}
