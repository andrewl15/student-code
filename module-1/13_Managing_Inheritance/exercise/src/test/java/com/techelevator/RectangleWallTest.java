package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RectangleWallTest
 */
public class RectangleWallTest {

    private static Class<?> rectangle;

    @BeforeAll
    public static void setup() {
        try {
            rectangle = Class.forName("com.techelevator.RectangleWall");
        } catch (ClassNotFoundException e) {
            fail("com.techelevator.RectangleWall class not found.");
        }
    }

    @Test
    public void testCreateRectangleWall() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = rectangle.getConstructor(String.class, String.class, int.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 1, 2);
            assertNotNull(sut, "RectangleWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must have a four arg constructor: public RectangleWall(String, String, int, int)");
        }

        try {
            assertEquals("TEST", sut.getClass().getMethod("getName").invoke(sut), "getName() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must contain method: getName()");
        }

        try {
            assertEquals("TESTCOLOR", sut.getClass().getMethod("getColor").invoke(sut), "getColor() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must contain method: getColor()");
        }

        try {
            assertEquals(1, sut.getClass().getMethod("getLength").invoke(sut), "getLength() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must contain method: getLength()");
        }

        try {
            assertEquals(2, sut.getClass().getMethod("getHeight").invoke(sut), "getHeight() did not return expected value.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must contain method: getHeight()");
        }
    }

    @Test
    public void testIsAWall() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Constructor<?> constructor = rectangle.getConstructor(String.class, String.class, int.class, int.class);
            Object sut = constructor.newInstance("TEST", "TESTCOLOR", 1, 2);
            assertNotNull(sut, "RectangleWall could not be instantiated.");
            assertEquals("com.techelevator.Wall", sut.getClass().getSuperclass().getTypeName(),
                    "RectangleWall must extend Wall.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must have a four arg constructor: public RectangleWall(String, String, int, int)");
        }
    }

    @Test
    public void testGetArea() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = rectangle.getConstructor(String.class, String.class, int.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 1, 3);
            assertNotNull(sut, "RectangleWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall constructor not found.");
        }

        try {
            assertEquals(3, sut.getClass().getMethod("getArea").invoke(sut),
                    "getArea() did not return the expected value for a 1x3 rectangle.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must contain method: getArea()");
        }
    }

    @Test
    public void testGetString() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = rectangle.getConstructor(String.class, String.class, int.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 1, 3);
            assertNotNull(sut, "RectangleWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall constructor not found.");
        }

        try {
            assertEquals("TEST (1x3) rectangle", sut.getClass().getMethod("toString").invoke(sut),
                    "toString() did not return the expected value for a 1x3 rectangle.");
        } catch (NoSuchMethodException e) {
            fail("RectangleWall must contain method: toString()");
        }
    }

}
