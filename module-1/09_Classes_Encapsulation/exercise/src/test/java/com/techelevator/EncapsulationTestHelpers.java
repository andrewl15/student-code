package com.techelevator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class EncapsulationTestHelpers {
    public static void verifyClassField(Class klass, String fieldName, Class fieldType, Boolean publicGetter, Boolean publicSetter) {
        String className = klass.getSimpleName();

        Field field = SafeReflection.getField(klass, fieldName);
        if (field == null) {
            fail(className + " class must have the attribute: " + fieldName + ".");
        } else {
            // Verify field is private and correct type
            int modifiers = field.getModifiers();
            assertTrue(Modifier.isPrivate(modifiers), fieldName + " in class " + className + " must have private access.");
            assertEquals(field.getType(), fieldType, fieldName + " in class " + className + " must have type: " + fieldType.getSimpleName() + ".");

            // Verify getter/setter
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            if (fieldType == Boolean.TYPE) {
                getterName = fieldName;
            }

            if (publicGetter) {
                verifyClassGetter(klass, getterName, fieldType);
            } else {
                Method method = SafeReflection.getMethod(klass, getterName);
                assertNull(method, className + " class must not have a " + getterName + "() method.");
            }

            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            if (publicSetter) {
                verifyClassSetter(klass, setterName, fieldType);
            } else {
                Method method = SafeReflection.getMethod(klass, setterName);
                assertNull(method, className + " class must not have a " + setterName + "() method.");
            }
        }
    }

    public static void verifyDerivedField(Class klass, String fieldName, Class fieldType) {
        String className = klass.getSimpleName();

        Field field = SafeReflection.getField(klass, fieldName);
        if (field != null) {
            // Verify no declared field
            fail(className + " class must not have a declared instance variable for derived attribute: " + fieldName + ".");
        } else {

            // Verify public getter but no setter
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            if (fieldType == Boolean.TYPE) {
                getterName = fieldName;
            }
            verifyClassGetter(klass, getterName, fieldType);

            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = SafeReflection.getMethod(klass, setterName, fieldType);
            assertNull(method, className + " class must not have a " + setterName + "(" + fieldType.getSimpleName() + ") method.");
        }
    }

    public static void verifyClassGetter(Class klass, String getterName, Class returnType) {
        String className = klass.getSimpleName();
        Method method = SafeReflection.getMethod(klass, getterName);
        assertNotNull(method, className + " class must have a " + getterName + "() method.");
        assertSame(method.getReturnType(), returnType,
                className + " class " + getterName + "() method must return type: " + returnType.getSimpleName() + ".");
    }

    public static void verifyClassSetter(Class klass, String setterName, Class fieldType) {
        String className = klass.getSimpleName();
        Method method = SafeReflection.getMethod(klass, setterName, fieldType);
        assertNotNull(method, className + " class must have a " + setterName + "(" + fieldType.getSimpleName() + ") method.");
        assertSame(method.getReturnType(), void.class, className + " class " + setterName + "() method must return type: void.");
    }
}
