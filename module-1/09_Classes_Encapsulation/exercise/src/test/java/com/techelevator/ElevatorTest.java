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
public class ElevatorTest {

	@BeforeAll
	public static void elevatorVerifyClassDefinition() {
		Class klass = Elevator.class;

		// Verify not abstract
		assertFalse(Modifier.isAbstract(klass.getModifiers()), "Elevator class must not be abstract. Remove the 'abstract' modifier on Elevator.");

		// Verify constructor(s)
		Constructor<Elevator> constructor = SafeReflection.getConstructor(klass, Integer.TYPE);
		assertNotNull(constructor, "Elevator class does not have the required constructor(int)");

		// Verify attributes
		EncapsulationTestHelpers.verifyClassField(Elevator.class, "currentFloor", Integer.TYPE, true, false);
		EncapsulationTestHelpers.verifyClassField(Elevator.class, "numberOfFloors", Integer.TYPE, true, false);
		EncapsulationTestHelpers.verifyClassField(Elevator.class, "isDoorOpen", Boolean.TYPE, true, false);
	}

	@Test
	public void elevatorConstructor() {
		Class klass = Elevator.class;

		Constructor<Elevator> constructor = SafeReflection.getConstructor(klass, Integer.TYPE);
		assertNotNull(constructor, "You do not have the required constructor(int)");
		try {
			Elevator elevator = constructor.newInstance(3);
			Method getCurrentFloor = SafeReflection.getMethod(klass, "getCurrentFloor");
			Method getNumberOfFloors = SafeReflection.getMethod(klass, "getNumberOfFloors");
			Method isDoorOpen = SafeReflection.getMethod(klass, "isDoorOpen");

			assertEquals(1, (int) getCurrentFloor.invoke(elevator), "getCurrentFloor for a new Elevator should return 1.");
			assertEquals(3, (int) getNumberOfFloors.invoke(elevator),
					"getNumberOfFloors should be equal to the argument passed into the constructor.");
			assertFalse((boolean) isDoorOpen.invoke(elevator), "The door should be closed for a new Elevator.");
		} catch (Exception e) {
			fail("An unknown error occurred with Elevator class: " + e.getMessage());
		}
	}

	@Test
	public void elevatorOpenDoorTests() {
		Class klass = Elevator.class;

		Constructor<Elevator> constructor = SafeReflection.getConstructor(klass, Integer.TYPE);
		assertNotNull(constructor, "You do not have the required constructor(int)");
		try {
			Elevator elevator = constructor.newInstance(3);
			Method openDoor = SafeReflection.getMethod(klass, "openDoor");
			Method closeDoor = SafeReflection.getMethod(klass, "closeDoor");
			Method isDoorOpen = SafeReflection.getMethod(klass, "isDoorOpen");

			assertNotNull(openDoor, "openDoor method is missing");

			openDoor.invoke(elevator);
			assertTrue((boolean) isDoorOpen.invoke(elevator), "The door should be open after calling openDoor.");

			assertNotNull(closeDoor, "closeDoor method is missing");

			closeDoor.invoke(elevator);
			assertFalse((boolean) isDoorOpen.invoke(elevator), "The door should be closed after calling closeDoor.");
		} catch (Exception e) {
			fail("An unknown error occurred with Elevator class: " + e.getMessage());
		}
	}

	@Test
	public void elevatorGoUpAndDownTests() {
		Class klass = Elevator.class;

		Constructor<Elevator> constructor = SafeReflection.getConstructor(klass, Integer.TYPE);
		assertNotNull(constructor, "You do not have the required constructor(int)");
		try {
			Elevator elevator = constructor.newInstance(5);
			Method openDoor = SafeReflection.getMethod(klass, "openDoor");
			Method closeDoor = SafeReflection.getMethod(klass, "closeDoor");
			Method goUp = SafeReflection.getMethod(klass, "goUp", Integer.TYPE);
			Method goDown = SafeReflection.getMethod(klass, "goDown", Integer.TYPE);
			Method getCurrentFloor = SafeReflection.getMethod(klass, "getCurrentFloor");
			Method isDoorOpen = SafeReflection.getMethod(klass, "isDoorOpen");

			assertNotNull(openDoor, "openDoor method is missing");
			assertNotNull(closeDoor, "closeDoor method is missing");
			assertNotNull(goUp, "goUp method is missing");

			// Newly instantiated elevator is on floor 1 and door is closed
			assertEquals(1, (int) getCurrentFloor.invoke(elevator),
					"Newly instantiated elevator is not on floor 1.");
			assertFalse((boolean) isDoorOpen.invoke(elevator), "Newly instantiated elevator's door is not closed.");

			// Move up to floor 2
			goUp.invoke(elevator, new Object[] { 2 });
			assertEquals(2, (int) getCurrentFloor.invoke(elevator),
					"The elevator did not go up to floor 2.");

			// Open the door
			openDoor.invoke(elevator);
			assertTrue((boolean) isDoorOpen.invoke(elevator), "The elevator door did not open.");

			// Attempt to move up to floor 3 with door open
			goUp.invoke(elevator, new Object[] { 3 });
			assertEquals(2, (int) getCurrentFloor.invoke(elevator),
					"The elevator moved from floor 2 to floor 3 with the door open.");

			// Close the door
			closeDoor.invoke(elevator);
			assertFalse((boolean) isDoorOpen.invoke(elevator), "The elevator door did not close.");

			// Move up to floor 4
			goUp.invoke(elevator, new Object[] { 4 });
			assertEquals(4, (int) getCurrentFloor.invoke(elevator),
					"The elevator did not move up to floor 4.");

			// Attempt to move "up" to floor 3 from floor 4
			goUp.invoke(elevator, new Object[] { 3 });
			assertEquals(4, (int) getCurrentFloor.invoke(elevator),
					"The elevator moved \"up\" to floor 3 from floor 4.");

			// Move up to the top floor
			goUp.invoke(elevator, new Object[] { 5 });
			assertEquals(5, (int) getCurrentFloor.invoke(elevator),
					"The elevator did not move up to the top floor.");

			// Attempt to move past the top floor
			goUp.invoke(elevator, new Object[] { 6 });
			assertEquals(5, (int) getCurrentFloor.invoke(elevator), "The elevator went past the top floor.");
			
			assertNotNull(goDown, "goDown method is missing");

			// Move down to floor 3 from floor 5
			goDown.invoke(elevator, new Object[] { 3 });
			assertEquals(3, (int) getCurrentFloor.invoke(elevator),
					"The elevator did not move down to the floor 3.");

			// Open the door
			openDoor.invoke(elevator);
			assertTrue((boolean) isDoorOpen.invoke(elevator), "The elevator door did not open.");

			// Attempt to move down to floor 2 with door open
			goDown.invoke(elevator, new Object[] { 2 });
			assertTrue(3 == (int) getCurrentFloor.invoke(elevator),
					"The elevator moved from floor 3 to floor 2 with the door open.");

			// Close the door
			closeDoor.invoke(elevator);
			assertFalse((boolean) isDoorOpen.invoke(elevator), "The elevator door did not close.");

			// Move down to the floor 2
			goDown.invoke(elevator, new Object[] { 2 });
			assertEquals(2, (int) getCurrentFloor.invoke(elevator),
					"The elevator did not move down to floor 2.");

			// Attempt to move "down" from floor 2 to floor 3
			goDown.invoke(elevator, new Object[] { 3 });
			assertEquals(2, (int) getCurrentFloor.invoke(elevator),
					"The elevator moved \"down\" to floor 3 from floor 2.");

			// Move down to the bottom floor
			goDown.invoke(elevator, new Object[] { 1 });
			assertEquals(1, (int) getCurrentFloor.invoke(elevator),
					"The elevator did not move down to the bottom floor.");

			// Attempt to move below the bottom floor
			goDown.invoke(elevator, new Object[] { 0 });
			assertEquals(1, (int) getCurrentFloor.invoke(elevator), "The elevator went below the bottom floor.");

		} catch (Exception e) {
			fail("An unknown error occurred with Elevator class: " + e.getMessage());
		}
	}
}
