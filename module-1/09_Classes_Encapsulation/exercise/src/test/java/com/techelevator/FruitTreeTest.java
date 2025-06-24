package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class FruitTreeTest {

	@BeforeAll
	public static void fruitTreeVerifyClassDefinition() {
		Class klass = FruitTree.class;

		// Verify not abstract
		assertFalse(Modifier.isAbstract(klass.getModifiers()), "FruitTree class must not be abstract. Remove the 'abstract' modifier on FruitTree.");

		// Verify constructor(s)
		Constructor<FruitTree> constructor = SafeReflection.getConstructor(klass, String.class, Integer.TYPE);
		assertNotNull(constructor, "FruitTree class does not have the required constructor(String, int)");

		// Verify attributes
		EncapsulationTestHelpers.verifyClassField(FruitTree.class, "typeOfFruit", String.class, true, false);
		EncapsulationTestHelpers.verifyClassField(FruitTree.class, "piecesOfFruitLeft", Integer.TYPE, true, false);
	}

	@Test
	public void fruitTreeConstructorTest() {
		Class klass = FruitTree.class;

		Constructor<FruitTree> constructor = SafeReflection.getConstructor(klass, String.class, Integer.TYPE);
		assertNotNull(constructor, "You do not have the required constructor(String, int)");
		try {
			FruitTree fruitTree = constructor.newInstance("Apple", 97);
			Method getPiecesOfFruitLeft = SafeReflection.getMethod(klass, "getPiecesOfFruitLeft");
			Method getTypeOfFruit = SafeReflection.getMethod(klass, "getTypeOfFruit");

			assertEquals("Apple", (String) getTypeOfFruit.invoke(fruitTree),
					"Passed Apple into constructor and expected typeOfFruit equal Apple");
			assertEquals(97, (int) getPiecesOfFruitLeft.invoke(fruitTree),
					"Passed 97 into constructor and expected piecesOfFruitLeft equal 97");
		} catch (Exception e) {
			fail("An unknown error occurred with FruitTree class: " + e.getMessage());
		}
	}

	@Test
	public void fruitTreePickFruitTests() {
		Class klass = FruitTree.class;

		Constructor<FruitTree> constructor = SafeReflection.getConstructor(klass, String.class, Integer.TYPE);
		assertNotNull(constructor, "You do not have the required constructor(String, int)");
		try {
			FruitTree fruitTree = constructor.newInstance("Apple", 3);
			Method pickFruit = SafeReflection.getMethod(klass, "pickFruit", Integer.TYPE);
			Method getPiecesOfFruitLeft = SafeReflection.getMethod(klass, "getPiecesOfFruitLeft");

			assertNotNull(pickFruit, "pickFruit method is missing");

			assertTrue((boolean) pickFruit.invoke(fruitTree, new Object[] { 2 }),
					"pickFruit(int) should return TRUE when pieces remain");
			assertEquals(1, (int) getPiecesOfFruitLeft.invoke(fruitTree),
					"Tree started with 3 pieces of fruit. 2 were picked, 1 should be remaining");

			assertTrue((boolean) pickFruit.invoke(fruitTree, new Object[] { 1 }),
					"pickFruit(int) should return TRUE when pieces remain");
			assertEquals(0, (int) getPiecesOfFruitLeft.invoke(fruitTree),
					"Tree started with 1 piece of fruit. 1 were picked, 0 should be remaining");

			assertFalse((boolean) pickFruit.invoke(fruitTree, new Object[] { 1 }),
					"pickFruit(int) should return FALSE when not enough remaining pieces of fruit.");
			assertEquals(0, (int) getPiecesOfFruitLeft.invoke(fruitTree),
					"Tree had 0 pieces of fruit. 1 was not picked, 0 should be remaining");
		} catch (Exception e) {
			fail("An unknown error occurred with FruitTree class: " + e.getMessage());
		}
	}

}
