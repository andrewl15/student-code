package com.techelevator;

import com.techelevator.testing.TestingLibrary;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techelevator.testing.TestingLibrary.*;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ExercisesTest {

	/*
	 * Create and return a Map that contains the following data
	 * of animals and the name of a group of that animal.
	 *
	 * rhino -> crash
	 * giraffe -> tower
	 * elephant -> herd
	 * lion -> pride
	 * crow -> murder
	 * pigeon -> kit
	 * flamingo -> pat
	 * deer -> herd
	 * dog -> pack
	 * crocodile -> float
	 *
	 */
	@Test
	public void exercise01_animalGroupName() {
		TestGroup happyPath = new TestGroup("Returns correct animals and group names");
		String[] expectedKeys = {"rhino", "giraffe", "elephant", "lion", "crow", "pigeon", "flamingo", "deer", "dog", "crocodile"};
		String[] expectedValues = {"crash", "tower", "herd", "pride", "murder", "kit", "pat", "herd", "pack", "float"};
		happyPath.addTest(new TestingLibrary.Test(new Object[]{}, generateMap(expectedKeys, expectedValues)));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(happyPath);

		TestSuite test = new TestSuite(Exercises.class, "animalGroupName", new Class[]{}, testGroups);
		runTestSuite(test);
	}

	/*
	 * Given a Map and a String item number, look for the item number in the Map
	 * and return its value that represents the discount percentage if the item is on sale.
	 *
	 * If the item number isn't in the map, or is empty, or is null, return 0.00 instead.
	 *
	 * For example, the Map might have keys and values such as:
	 * "KITCHEN4001" -> 0.20
	 * "GARAGE1070" -> 0.15
	 * "LIVINGROOM"	-> 0.10
	 * "KITCHEN6073" -> 0.40
	 *
	 * The item number should be case-insensitive so "kitchen4001", "Kitchen4001", and "KITCHEN4001"
	 * should all return 0.20.
	 *
	 * isItOnSale({"KITCHEN4001": 0.20, "GARAGE1070": 0.15}, "kitchen4001") → 0.20
	 * isItOnSale({"LIVINGROOM": 0.10, "KITCHEN6073": 0.40}, "") → 0.00
	 * isItOnSale({"BEDROOM3434": 0.60, "GARAGE1070": 0.15}, "GARAGE1070") → 0.15
	 * isItOnSale({"KITCHEN4001": 0.20, "BATH0073": 0.15}, "spaceship9999") → 0.00
	 *
	 */
	@Test
	public void exercise02_isItOnSale() {
		Map<String, Double> itemsOnSale = new HashMap<>();
		itemsOnSale.put("KITCHEN4001", 0.20);
		itemsOnSale.put("GARAGE1070", 0.15);
		itemsOnSale.put("LIVINGROOM", 0.10);
		itemsOnSale.put("KITCHEN6073", 0.40);
		itemsOnSale.put("BEDROOM3434", 0.60);
		itemsOnSale.put("BATH0073", 0.15);
		itemsOnSale.put("DEN5309", 0.25);

		TestGroup happyPath = new TestGroup("Returns correct group discount for all expected SKUs");
		happyPath.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "KITCHEN4001"}, 0.20));
		happyPath.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "GARAGE1070"}, 0.15));
		happyPath.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "LIVINGROOM"}, 0.10));
		happyPath.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "KITCHEN6073"}, 0.40));
		happyPath.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "BEDROOM3434"}, 0.60));
		happyPath.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "BATH0073"}, 0.15));
		happyPath.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "DEN5309"}, 0.25));

		TestGroup caseInsensitiveTests = new TestGroup("Is not case sensitive for the SKU");
		caseInsensitiveTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "kitchen4001"}, 0.20));
		caseInsensitiveTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "Garage1070"}, 0.15));
		caseInsensitiveTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "baTH0073"}, 0.15));
		caseInsensitiveTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "DeN5309"}, 0.25));

		TestGroup unknownTests = new TestGroup("Returns 0.00 for SKUs not on sale");
		unknownTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "spaceship9999"}, 0.00));
		unknownTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, "fakeSKU"}, 0.00));

		TestGroup nullEmptyTests = new TestGroup("Returns 0.00 for null or empty strings");
		String nullStr = null;
		nullEmptyTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, ""}, 0.00));
		nullEmptyTests.addTest(new TestingLibrary.Test(new Object[]{itemsOnSale, nullStr}, 0.00));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(happyPath);
		testGroups.add(caseInsensitiveTests);
		testGroups.add(unknownTests);
		testGroups.add(nullEmptyTests);

		TestSuite test = new TestSuite(Exercises.class, "isItOnSale", new Class[]{Map.class, String.class}, testGroups);
		runTestSuite(test);
	}

	/*
	 * Modify and return the given map as follows: if "Peter" has more than 0 money, transfer half of it to "Paul",
	 * but only if Paul has less than $10.
	 *
	 * Note, monetary amounts are specified in cents: penny=1, nickel=5, ... $1=100, ... $10=1000, ...
	 *
	 * robPeterToPayPaul({"Peter": 2000, "Paul": 99}) → {"Peter": 1000, "Paul": 1099}
	 * robPeterToPayPaul({"Peter": 2000, "Paul": 30000}) → {"Peter": 2000, "Paul": 30000}
	 *
	 */
	@Test
	public void exercise03_robPeterToPayPaul() {
		TestGroup peterGivesHalf = new TestGroup("Peter gives Paul half his money");
		peterGivesHalf.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(2000, 99)},
				peterPaulMap(1000, 1099)));
		peterGivesHalf.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(2345, 500)},
				peterPaulMap(1173, 1672)));
		peterGivesHalf.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(5000, 999)},
				peterPaulMap(2500, 3499)));
		peterGivesHalf.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(101, 500)},
				peterPaulMap(51, 550)));

		TestGroup paulOver10 = new TestGroup("Peter gives no money because Paul has more than $10");
		paulOver10.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(2000, 30000)},
				peterPaulMap(2000, 30000)));
		paulOver10.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(5000, 1000)},
				peterPaulMap(5000, 1000)));

		TestGroup peterNoMoney = new TestGroup("Peter gives no money because he has none");
		peterNoMoney.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(0, 5000)},
				peterPaulMap(0, 5000)));
		peterNoMoney.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(1, 5000)},
				peterPaulMap(1, 5000)));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(peterGivesHalf);
		testGroups.add(peterNoMoney);
		testGroups.add(paulOver10);

		TestSuite test = new TestSuite(Exercises.class, "robPeterToPayPaul", new Class[]{Map.class}, testGroups);
		runTestSuite(test);
	}

	private Map<Object, Object> peterPaulMap(int peterMoney, int paulMoney) {
		Object[] keys = {"Peter", "Paul"};
		Object[] values = {peterMoney, paulMoney};
		return generateMap(keys, values);
	}

	/*
	 * Modify and return the given Map as follows: if "Peter" has $50 or more, AND "Paul" has $100 or more,
	 * then create a new "Partnership" worth a combined contribution of a quarter of each partner's
	 * current worth.
	 *
	 * peterPaulPartnership({"Peter": 50000, "Paul": 100000}) → {"Peter": 37500, "Paul": 75000, "Partnership": 37500}
	 * peterPaulPartnership({"Peter": 3333, "Paul": 1234567890}) → {"Peter": 3333, "Paul": 1234567890}
	 *
	 */
	@Test
	public void exercise04_peterPaulPartnership() {
		TestGroup funded = new TestGroup("Partnership funded");
		funded.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(5000, 10000)},
				peterPaulPartnershipMap(3750, 7500, 3750)));
		funded.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(15000, 110000)},
				peterPaulPartnershipMap(11250, 82500, 31250)));

		TestGroup notFunded = new TestGroup("Partnership not funded");
		notFunded.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(3333, 1234567890)},
				peterPaulMap(3333, 1234567890)));
		notFunded.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(4999, 1234567890)},
				peterPaulMap(4999, 1234567890)));
		notFunded.addTest(new TestingLibrary.Test(new Object[]{peterPaulMap(5000, 9999)},
				peterPaulMap(5000, 9999)));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(funded);
		testGroups.add(notFunded);

		TestSuite test = new TestSuite(Exercises.class, "peterPaulPartnership", new Class[]{Map.class}, testGroups);
		runTestSuite(test);
	}

	private Map<Object, Object> peterPaulPartnershipMap(int peterMoney, int paulMoney, int partnership) {
		Object[] keys = {"Peter", "Paul", "Partnership"};
		Object[] values = {peterMoney, paulMoney, partnership};
		return generateMap(keys, values);
	}

	/*
	 * Given an array of non-empty strings, return a Map<String, String> where, for every String in the array,
	 * there is an entry whose key is the first character of the string.
	 *
	 * The value of the entry is the last character of the String. If multiple Strings start with the same letter, then the
	 * value for that key should be the later String's last character.
	 *
	 * beginningAndEnding(["code", "bug"]) → {"b": "g", "c": "e"}
	 * beginningAndEnding(["code", "bug", "cat"]) → {"b": "g", "c": "t"}
	 * beginningAndEnding(["muddy", "good", "moat", "good", "night"]) → {"g": "d", "m": "t", "n": "t"}
	 */
	@Test
	public void exercise05_beginningAndEnding() {
		TestGroup basicTests = new TestGroup("Returns Map of beginnings and last ending");
		Map<Object, Object> expected = generateMap(new String[]{"b", "c"}, new String[]{"g", "e"});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] {"code", "bug"}}, expected));

		expected = generateMap(new String[]{"b", "c"}, new String[]{"g", "t"});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "code", "bug", "cat" }}, expected));

		expected = generateMap(new String[]{"m"}, new String[]{"n"});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "man", "moon", "main" }}, expected));

		expected = generateMap(new String[]{"m", "g", "n"}, new String[]{"t", "d", "t"});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "muddy", "good", "moat", "good", "night" }}, expected));

		TestGroup nullTests = new TestGroup("Returns empty Map for empty array");
		nullTests.addTest(new TestingLibrary.Test(new Object[]{new String[] {}}, new HashMap<>()));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(basicTests);
		testGroups.add(nullTests);

		TestSuite test = new TestSuite(Exercises.class, "beginningAndEnding", new Class[]{String[].class}, testGroups);
		runTestSuite(test);
	}

	/*
	 * Given an array of strings, return a Map<String, Integer> with a key for each different string, with the value the
	 * number of times that string appears in the array.
	 *
	 * ** A CLASSIC **
	 *
	 * wordCount(["a", "b", "a", "c", "b"]) → {"b": 2, "c": 1, "a": 2}
	 * wordCount([]) → {}
	 * wordCount(["c", "b", "a"]) → {"b": 1, "c": 1, "a": 1}
	 *
	 */
	@Test
	public void exercise06_wordCount() {
		TestGroup basicTests = new TestGroup("Returns Map of string counts");
		Map<Object, Object> expected = generateMap(new String[]{"a", "b", "c"}, new Integer[]{2, 2, 1});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "a", "b", "a", "c", "b" }}, expected));

		expected = generateMap(new String[]{"a", "b", "c"}, new Integer[]{1, 1, 1});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "c", "b", "a" }}, expected));

		expected = generateMap(new String[]{"ba", "black", "sheep"}, new Integer[]{2, 1, 1});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "ba", "ba", "black", "sheep" }}, expected));

		expected = generateMap(new String[]{"ba", "black", "sheep"}, new Integer[]{4, 2, 2});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "ba", "ba", "black", "sheep", "ba", "ba", "black", "sheep" }}, expected));

		expected = generateMap(new String[]{"apple", "banana", "carrot", "dill"}, new Integer[]{4, 3, 1, 2});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "apple", "apple", "banana", "apple", "carrot", "banana", "dill", "dill", "banana", "apple" }}, expected));

		expected = generateMap(new String[]{"apple"}, new Integer[]{6});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "apple", "apple", "apple", "apple", "apple", "apple" }}, expected));

		TestGroup nullTests = new TestGroup("Returns empty Map for empty array");
		nullTests.addTest(new TestingLibrary.Test(new Object[]{new String[] {}}, new HashMap<>()));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(basicTests);
		testGroups.add(nullTests);

		TestSuite test = new TestSuite(Exercises.class, "wordCount", new Class[]{String[].class}, testGroups);
		runTestSuite(test);
	}

	/*
	 * Given an array of int values, return a Map<Integer, Integer> with a key for each int, with the value the
	 * number of times that int appears in the array.
	 *
	 * ** The lesser known cousin of the classic wordCount **
	 *
	 * integerCount([1, 99, 63, 1, 55, 77, 63, 99, 63, 44]) → {1: 1, 44: 1, 55: 1, 63: 3, 77: 1, 99:2}
	 * integerCount([107, 33, 107, 33, 33, 33, 106, 107]) → {33: 4, 106: 1, 107: 3}
	 * integerCount([]) → {}
	 *
	 */
	@Test
	public void exercise07_integerCount() {
		TestGroup basicTests = new TestGroup("Returns Map of int counts");
		Map<Object, Object> expected = generateMap(new Object[]{1, 44, 55, 63, 77, 99}, new Object[]{2, 1, 1, 3, 1, 2});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new int[] { 1, 99, 63, 1, 55, 77, 63, 99, 63, 44 }}, expected));

		expected = generateMap(new Object[]{33, 106, 107}, new Object[]{4, 1, 3});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new int[] { 107, 33, 107, 33, 33, 33, 106, 107 }}, expected));

		TestGroup nullTests = new TestGroup("Returns empty Map for empty array");
		nullTests.addTest(new TestingLibrary.Test(new Object[]{new int[] {}}, new HashMap<>()));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(basicTests);
		testGroups.add(nullTests);

		TestSuite test = new TestSuite(Exercises.class, "integerCount", new Class[]{int[].class}, testGroups);
		runTestSuite(test);
	}

	/*
	 * Given an array of strings, return a Map<String, Boolean> where each different string is a key and value
	 * is true only if that string appears 2 or more times in the array.
	 *
	 * wordMultiple(["a", "b", "a", "c", "b"]) → {"b": true, "c": false, "a": true}
	 * wordMultiple(["c", "b", "a"]) → {"b": false, "c": false, "a": false}
	 * wordMultiple(["c", "c", "c", "c"]) → {"c": true}
	 *
	 */
	@Test
	public void exercise08_wordMultiple() {
		TestGroup basicTests = new TestGroup("Returns a Map indicating if the strings occur 2 or more times");
		Map<Object, Object> expected = generateMap(new Object[]{"apple", "banana", "carrot"}, new Object[]{true, true, false});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "apple", "banana", "apple", "carrot", "banana" }}, expected));

		expected = generateMap(new Object[]{"a", "b", "c"}, new Object[]{false, false, false});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "c", "b", "a" }}, expected));

		expected = generateMap(new Object[]{"c"}, new Object[]{true});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "c", "c", "c", "c" }}, expected));

		expected = generateMap(new Object[]{"cat", "dog"}, new Object[]{true, true});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "cat", "dog", "dog", "cat" }}, expected));

		TestGroup nullTests = new TestGroup("Returns empty Map for empty array");
		nullTests.addTest(new TestingLibrary.Test(new Object[]{new String[] {}}, new HashMap<>()));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(basicTests);
		testGroups.add(nullTests);

		TestSuite test = new TestSuite(Exercises.class, "wordMultiple", new Class[]{String[].class}, testGroups);
		runTestSuite(test);
	}

	/*
	 * Given two maps, Map<String, Integer>, merge the two into a new map, Map<String, Integer> where keys in Map2,
	 * and their Integer values, are added to the Integer values of matching keys in Map1. Return the new map.
	 *
	 * Unmatched keys and their Integer values in Map2 are simply added to Map1.
	 *
	 * consolidateInventory({"SKU1": 100, "SKU2": 53, "SKU3": 44} {"SKU2":11, "SKU4": 5})
	 * 	 → {"SKU1": 100, "SKU2": 64, "SKU3": 44, "SKU4": 5}
	 *
	 */
	@Test
	public void exercise09_consolidateInventory() {
		TestGroup basicTests = new TestGroup("Returns consolidated inventory Map");

		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("SKU1", 100);
		inventory1.put("SKU2", 53);
		inventory1.put("SKU3", 44);

		Map<String, Integer> inventory2 = new HashMap<>();
		inventory2.put("SKU2", 11);
		inventory2.put("SKU4", 5);

		Map<Object, Object> expected = generateMap(new Object[]{"SKU1", "SKU2", "SKU3", "SKU4"}, new Object[]{100, 64, 44, 5});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{inventory1, inventory2}, expected));

		inventory1 = new HashMap<>();
		inventory1.put("SKU_4", 0);
		inventory1.put("SKU_23", 53);
		inventory1.put("SKU_39", 66);
		inventory1.put("SKU_X", 8);

		inventory2 = new HashMap<>();
		inventory2.put("SKU_4", 68);
		inventory2.put("SKU_23", 33);
		inventory2.put("SKU_50", 444);
		inventory2.put("SKU_X", 1);

		expected = generateMap(new Object[]{"SKU_4", "SKU_23", "SKU_39", "SKU_50", "SKU_X"}, new Object[]{68, 86, 66, 444, 9});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{inventory1, inventory2}, expected));

		TestGroup nullTests = new TestGroup("Handles empty inventories");
		inventory1 = new HashMap<>();
		inventory1.put("Lorem", 11);
		inventory1.put("Ipsum", 22);
		inventory1.put("Dolor", 33);
		inventory1.put("Sit", 44);
		inventory1.put("Amet", 55);

		nullTests.addTest(new TestingLibrary.Test(new Object[]{inventory1, new HashMap<>()}, inventory1));
		nullTests.addTest(new TestingLibrary.Test(new Object[]{new HashMap<>(), inventory1}, inventory1));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(basicTests);
		testGroups.add(nullTests);

		TestSuite test = new TestSuite(Exercises.class, "consolidateInventory", new Class[]{Map.class, Map.class}, testGroups);
		runTestSuite(test);
	}

	/*
	 * Just when you thought it was safe to get back in the water --- last2Revisited!!!!
	 *
	 * Given an array of Strings, for each String, its last2 count is the number of times that a subString length 2
	 * appears in the String and also as the last 2 chars of the String.
	 *
	 * We don't count the end subString, so "hixxxhi" has a last2 count of 1, but the subString may overlap a prior
	 * position by one.  For instance, "xxxx" has a count of 2: one pair at position 0, and the second at position 1.
	 * The third pair at position 2 is the end subString, which we don't count.
	 *
	 * Return a Map<String, Integer> where the keys are the Strings from the array and the values are the last2 counts.
	 *
	 * last2Revisited(["hixxhi", "xaxxaxaxx", "axxxaaxx"]) → {"hixxhi": 1, "xaxxaxaxx": 1, "axxxaaxx": 2}
	 *
	 */
	@Test
	public void exercise10_last2Revisited() {
		TestGroup basicTests = new TestGroup("Returns Map of last2 counts");
		Map<Object, Object> expected = generateMap(new Object[]{"hixxhi", "xaxxaxaxx", "axxxaaxx"}, new Object[]{1, 1, 2});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "hixxhi", "xaxxaxaxx", "axxxaaxx" }}, expected));

		expected = generateMap(new Object[]{"banana", "kiwi", "Hahahahaha"}, new Object[]{1, 0, 3});
		basicTests.addTest(new TestingLibrary.Test(new Object[]{new String[] { "banana", "kiwi", "Hahahahaha" }}, expected));

		TestGroup nullTests = new TestGroup("Returns empty Map for empty array");
		nullTests.addTest(new TestingLibrary.Test(new Object[]{new String[] {}}, new HashMap<>()));

		List<TestGroup> testGroups = new ArrayList<>();
		testGroups.add(basicTests);
		testGroups.add(nullTests);

		TestSuite test = new TestSuite(Exercises.class, "last2Revisited", new Class[]{String[].class}, testGroups);
		runTestSuite(test);
	}

	private Map<Object, Object> generateMap(Object[] keys, Object[] values) {
		if (keys.length != values.length) {
			fail("Keys and values lengths not equal");
		}
		Map<Object, Object> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		return map;
	}

}
