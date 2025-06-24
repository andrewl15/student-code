package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class HomeworkAssignmentTest {

	@BeforeAll
	public static void homeworkAssignmentVerifyClassDefinition() {

		Class klass = HomeworkAssignment.class;

		// Verify not abstract
		assertFalse(Modifier.isAbstract(klass.getModifiers()), "HomeworkAssignment class must not be abstract. Remove the 'abstract' modifier on HomeworkAssignment.");

		// Verify constructor(s)
		Constructor<HomeworkAssignment> constructor = SafeReflection.getConstructor(klass, Integer.TYPE, String.class);
		assertNotNull(constructor, "HomeworkAssignment class does not have the required constructor(int, String)");

		// Verify attributes
		EncapsulationTestHelpers.verifyClassField(HomeworkAssignment.class, "earnedMarks", Integer.TYPE, true, true);
		EncapsulationTestHelpers.verifyClassField(HomeworkAssignment.class, "possibleMarks", Integer.TYPE, true, false);
		EncapsulationTestHelpers.verifyClassField(HomeworkAssignment.class, "submitterName", String.class, true, false);
		EncapsulationTestHelpers.verifyDerivedField(HomeworkAssignment.class, "letterGrade", String.class);
	}

	@Test
	public void homeworkAssignmentConstructor() {
		Class klass = HomeworkAssignment.class;

		Constructor<HomeworkAssignment> constructor = SafeReflection.getConstructor(klass, Integer.TYPE, String.class);
		assertNotNull(constructor, "You do not have the required constructor(int, String)");
		try {
			HomeworkAssignment homeworkAssignment = constructor.newInstance(100, "T. Tester");
			Method method = SafeReflection.getMethod(klass, "getPossibleMarks");
			assertEquals(100, (int) method.invoke(homeworkAssignment),
					"Passed 100 into constructor and expected possibleMarks equal 100");
			method = SafeReflection.getMethod(klass, "getSubmitterName");
			assertEquals("T. Tester", (String) method.invoke(homeworkAssignment),
					"Passed 'T. Tester' into constructor and expected submitterName equal `T. Tester`");
		} catch (Exception e) {
			fail("An unknown error occurred with HomeworkAssignment class: " + e.getMessage());
		}
	}

	@Test
	public void homeworkAssignmentLetterGradeTests() {
		Class klass = HomeworkAssignment.class;

		Constructor<HomeworkAssignment> constructor = SafeReflection.getConstructor(klass, Integer.TYPE, String.class);
		assertNotNull(constructor, "You do not have the required constructor(int, String)");
		try {
			final int POSSIBLE_MARKS = 200;
			HomeworkAssignment homeworkAssignment = constructor.newInstance(POSSIBLE_MARKS, "T. Tester");
			Method getLetterGrade = SafeReflection.getMethod(klass, "getLetterGrade");
			Method setEarnedMarks = SafeReflection.getMethod(klass, "setEarnedMarks", Integer.TYPE);

			int[] testsForGradeA = { 100, 91, 90 };
			int[] testsForGradeB = { 89, 81, 80 };
			int[] testsForGradeC = { 79, 71, 70 };
			int[] testsForGradeD = { 69, 61, 60 };
			int[] testsForGradeF = { 59, 50, 23, 1, 0 };

			Map<String, int[]> testSuite = new HashMap<>();
			testSuite.put("A", testsForGradeA);
			testSuite.put("B", testsForGradeB);
			testSuite.put("C", testsForGradeC);
			testSuite.put("D", testsForGradeD);
			testSuite.put("F", testsForGradeF);

			for (Map.Entry<String, int[]> test : testSuite.entrySet())
			{
				String letterGrade = test.getKey();
				int[] testsForGrade = test.getValue();
				for (int earnedMarks : testsForGrade)
				{
					setEarnedMarks.invoke(homeworkAssignment, earnedMarks * 2);
					assertEquals(letterGrade, getLetterGrade.invoke(homeworkAssignment), "Expected " + letterGrade + " for score of " + (earnedMarks * 2) + " out of " + POSSIBLE_MARKS + " (" + earnedMarks + "%)");
				}
			}
		} catch (Exception e) {
			fail("An unknown error occurred with HomeworkAssignment class: " + e.getMessage());
		}
	}
}
