package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class WallTest {

    private static Class<?> wall;

    @BeforeAll
    public static void setup() {
        try {
            wall = Class.forName("com.techelevator.Wall");
        } catch (ClassNotFoundException e) {
            fail("com.techelevator.Wall class not found.");
        }
    }

    @Test
    public void wallIsAbstractClass() {
        assertTrue(Modifier.isAbstract(wall.getModifiers()), "Wall must be an abstract class.");
    }

    @Test
    public void hasFieldName() {
        try {
            Field name = wall.getDeclaredField("name");
            // must be a private field
            assertTrue(Modifier.isPrivate(name.getModifiers()), "Wall must have a private field named name.");
            // must be type String
            assertEquals("java.lang.String", name.getType().getTypeName(), "name must be a String.");
            // must have a getter
            assertTrue(hasGetter(name), "name must have a getter.");
        } catch (NoSuchFieldException e) {
            fail("Wall class must contain field: name");
        }
    }

    @Test
    public void hasFieldColor() {
        try {
            Field color = wall.getDeclaredField("color");
            // must be a private field
            assertTrue(Modifier.isPrivate(color.getModifiers()), "Wall must have a private field named color.");
            // must be type String
            assertEquals("java.lang.String", color.getType().getTypeName(), "color must be a String.");
            // must have a getter
            assertTrue(hasGetter(color), "color must have a getter.");
        } catch (NoSuchFieldException e) {
            fail("Wall class must contain field: color");
        }
    }

    @Test
    public void hasTwoArgConstructor() {
        try {
            wall.getConstructor(String.class, String.class);
        } catch (NoSuchMethodException e) {
            fail("Wall must have a two arg constructor: public Wall(String name, String color)");
        }
    }

    @Test
    public void hasAbstractMethodGetArea() {
        try {
            Method getArea = wall.getMethod("getArea");
            assertTrue(Modifier.isPublic(getArea.getModifiers()), "getArea() must be a public method.");
            assertTrue(Modifier.isAbstract(getArea.getModifiers()), "getArea() must be an abstract method.");
            assertEquals(int.class, getArea.getReturnType(), "getArea() must return an int.");
        } catch (NoSuchMethodException e) {
            fail("Wall must contain abstract method: getArea()");
        }
    }

    private boolean hasGetter(Field field) {
        return hasGetterOrSetter(field, "get");
    }

    private boolean hasGetterOrSetter(Field field, String prefix) {
        Method[] methods = wall.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))) {
                return true;
            }
        }
        return false;
    }
}
