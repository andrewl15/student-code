package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SquareWallTest
 */
public class SquareWallTest {

    private static Class<?> square;

    @BeforeAll
    public static void setup() {
        try {
            square = Class.forName("com.techelevator.SquareWall");
        } catch (ClassNotFoundException e) {
            fail("com.techelevator.SquareWall class not found.");
        }
    }

    @Test
    public void testIsAWall() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Constructor<?> constructor = square.getConstructor(String.class, String.class, int.class);
            Object sut = constructor.newInstance("TEST", "TESTCOLOR", 1);
            assertNotNull(sut, "SquareWall could not be instantiated.");

            // walk the class hierarchy looking for Wall
            List<String> superClasses = new ArrayList<>();
            Class<?> clazz = sut.getClass();

            // ensure SquareWall doesn't extend Wall directly, must extend RectangleWall
            assertNotEquals("com.techelevator.Wall", clazz.getSuperclass().getTypeName(), "SquareWall must not extend Wall directly.");

            // Object has no superClass, so that's where the loop stops
            while (!clazz.getName().equals("java.lang.Object")) {
                superClasses.add(clazz.getSuperclass().getTypeName());
                clazz = clazz.getSuperclass();
            }

            assertTrue(superClasses.contains("com.techelevator.Wall"), "SquareWall must extend Wall indirectly through RectangleWall.");
        } catch (NoSuchMethodException e) {
            fail("SquareWall must have a three arg constructor: public SquareWall(String, String, int)");
        }
    }

    @Test
    public void testIsARectangleWall() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            Constructor<?> constructor = square.getConstructor(String.class, String.class, int.class);
            Object sut = constructor.newInstance("TEST", "TESTCOLOR", 1);
            assertNotNull(sut, "SquareWall could not be instantiated.");
            assertEquals("com.techelevator.RectangleWall", sut.getClass().getSuperclass().getTypeName(),
                    "SquareWall must extend RectangleWall.");
        } catch (NoSuchMethodException e) {
            fail("SquareWall must have a three arg constructor: public SquareWall(String, String, int)");
        }
    }

    @Test
    public void testHasNoUnneededPropertiesOrMethods() {
        try {
            Field[] fields = square.getDeclaredFields();
            if (fields.length > 0) {
                fail("SquareWall must not contain any properties.");
            }

            Method[] methods = square.getDeclaredMethods();
            if (methods.length > 1 || (methods.length == 1 && !methods[0].getName().equals("toString"))) {
                fail("SquareWall must not contain methods besides the constructor and toString()");
            }
        } catch (SecurityException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetArea() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = square.getConstructor(String.class, String.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 3);
            assertNotNull(sut, "SquareWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("SquareWall constructor not found.");
        }

        try {
            assertEquals(9, sut.getClass().getMethod("getArea").invoke(sut),
                    "getArea() did not return the expected value for a 3x3 square");
        } catch (NoSuchMethodException e) {
            fail("SquareWall must contain method: getArea()");
        }
    }

    @Test
    public void testGetString() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object sut = null;
        try {
            Constructor<?> constructor = square.getConstructor(String.class, String.class, int.class);
            sut = constructor.newInstance("TEST", "TESTCOLOR", 3);
            assertNotNull(sut, "SquareWall could not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("SquareWall constructor not found.");
        }

        try {
            assertEquals("TEST (3x3) square", sut.getClass().getMethod("toString").invoke(sut),
                    "toString() did not return the expected value for a 3x3 square");
        } catch (NoSuchMethodException e) {
            fail("SquareWall must contain method: toString()");
        }
    }
}
