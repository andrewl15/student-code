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
public class TelevisionTest {

	@BeforeAll
	public static void televisionVerifyClassDefinition() {
		Class klass = Television.class;

		// Verify not abstract
		assertFalse(Modifier.isAbstract(klass.getModifiers()), "Television class must not be abstract. Remove the 'abstract' modifier on Television.");

		// Verify constructor(s)
		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "Television class does not have the required constructor()");

		// Verify attributes
		EncapsulationTestHelpers.verifyClassField(Television.class, "isOn", Boolean.TYPE, true, false);
		EncapsulationTestHelpers.verifyClassField(Television.class, "currentChannel", Integer.TYPE, true, false);
		EncapsulationTestHelpers.verifyClassField(Television.class, "currentVolume", Integer.TYPE, true, false);
	}

	@Test
	public void televisionConstructor() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method isOn = SafeReflection.getMethod(klass, "isOn");
			Method getCurrentChannel = SafeReflection.getMethod(klass, "getCurrentChannel");
			Method getCurrentVolume = SafeReflection.getMethod(klass, "getCurrentVolume");
			assertFalse((boolean) isOn.invoke(television), "A new TV should be turned off by default.");
			assertEquals(3, (int) getCurrentChannel.invoke(television),
					"A new TV should have currentChannel set to 3 by default.");
			assertEquals(2, (int) getCurrentVolume.invoke(television),
					"A new TV should have currentVolume set to 2 by default.");
		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionTurnOnOffTests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method isOn = SafeReflection.getMethod(klass, "isOn");
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method turnOff = SafeReflection.getMethod(klass, "turnOff");
			Method getCurrentChannel = SafeReflection.getMethod(klass, "getCurrentChannel");
			Method getCurrentVolume = SafeReflection.getMethod(klass, "getCurrentVolume");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(turnOff, "turnOff method is missing");
			assertNotNull(getCurrentChannel, "getCurrentChannel method is missing");
			assertNotNull(getCurrentVolume, "getCurrentVolume method is missing");

			turnOn.invoke(television);
			assertTrue((boolean) isOn.invoke(television), "The TV was turned on. isOn should now be true.");
			assertEquals(3, (int) getCurrentChannel.invoke(television),
					"The TV was turned on. currentChannel should now be 3.");
			assertEquals(2, (int) getCurrentVolume.invoke(television),
					"The TV was turned on. currentVolume should now be 2.");

			turnOff.invoke(television);
			assertFalse((boolean) isOn.invoke(television), "The TV was turned off. isOn should now be false.");
		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionChangeChannelTests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method changeChannel = SafeReflection.getMethod(klass, "changeChannel", Integer.TYPE);
			Method getCurrentChannel = SafeReflection.getMethod(klass, "getCurrentChannel");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(changeChannel, "changeChannel method is missing");

			changeChannel.invoke(television, new Object[] { 5 });
			assertEquals(3, (int) getCurrentChannel.invoke(television),
					"The TV was not turned on. currentChannel should not have changed.");

			turnOn.invoke(television);
			changeChannel.invoke(television, new Object[] { 5 });
			assertEquals(5, (int) getCurrentChannel.invoke(television),
					"The TV is turned on. The channel was changed to 5. currentChannel should now be 5.");
		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionChannelUpTests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method getCurrentChannel = SafeReflection.getMethod(klass, "getCurrentChannel");
			Method channelUp = SafeReflection.getMethod(klass, "channelUp");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(getCurrentChannel, "getCurrentChannel method is missing");
			assertNotNull(channelUp, "channelUp method is missing");

			channelUp.invoke(television);
			assertEquals(3, (int) getCurrentChannel.invoke(television),
					"channelUp should only change if the TV has been turned on.");

			turnOn.invoke(television);
			channelUp.invoke(television);
			assertEquals(4, (int) getCurrentChannel.invoke(television),
					"channelUp should have incremented currentChannel by 1.");
		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionChannelUpPast18Tests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method changeChannel = SafeReflection.getMethod(klass, "changeChannel", Integer.TYPE);
			Method getCurrentChannel = SafeReflection.getMethod(klass, "getCurrentChannel");
			Method channelUp = SafeReflection.getMethod(klass, "channelUp");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(changeChannel, "changeChannel method is missing");
			assertNotNull(channelUp, "channelUp method is missing");

			turnOn.invoke(television);
			changeChannel.invoke(television, new Object[] { 17 });

			channelUp.invoke(television);
			assertEquals(18, (int) getCurrentChannel.invoke(television),
					"channelUp should have incremented currentChannel by 1.");

			channelUp.invoke(television);
			assertEquals(3, (int) getCurrentChannel.invoke(television),
					"channelUp should not let currentChannel go past 18. Reset to 3.");

		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionChannelDownTests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method changeChannel = SafeReflection.getMethod(klass, "changeChannel", Integer.TYPE);
			Method getCurrentChannel = SafeReflection.getMethod(klass, "getCurrentChannel");
			Method channelDown = SafeReflection.getMethod(klass, "channelDown");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(changeChannel, "changeChannel method is missing");
			assertNotNull(channelDown, "channelDown method is missing");

			channelDown.invoke(television);
			assertEquals(3, (int) getCurrentChannel.invoke(television),
					"channelDown should only change if the TV has been turned on.");

			turnOn.invoke(television);
			changeChannel.invoke(television, new Object[] { 5 });
			channelDown.invoke(television);
			assertEquals(4, (int) getCurrentChannel.invoke(television),
					"channelDown should have decremented currentChannel by 1.");
		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionChannelDownPast3Tests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method changeChannel = SafeReflection.getMethod(klass, "changeChannel", Integer.TYPE);
			Method getCurrentChannel = SafeReflection.getMethod(klass, "getCurrentChannel");
			Method channelDown = SafeReflection.getMethod(klass, "channelDown");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(changeChannel, "changeChannel method is missing");
			assertNotNull(channelDown, "channelDown method is missing");

			turnOn.invoke(television);
			changeChannel.invoke(television, new Object[] { 4 });

			channelDown.invoke(television);
			assertEquals(3, (int) getCurrentChannel.invoke(television),
					"channelDown should have decremented currentChannel by 1.");

			channelDown.invoke(television);
			assertEquals(18, (int) getCurrentChannel.invoke(television),
					"channelDown should not let currentChannel go past 3. Reset to 18.");

		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionVolumeRaiseTests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {
			Television television = constructor.newInstance();
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method getCurrentVolume = SafeReflection.getMethod(klass, "getCurrentVolume");
			Method raiseVolume = SafeReflection.getMethod(klass, "raiseVolume");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(raiseVolume, "raiseVolume method is missing");

			raiseVolume.invoke(television);
			assertEquals(2, (int) getCurrentVolume.invoke(television),
					"currentVolume should only be increased if the TV is on.");

			turnOn.invoke(television);
			raiseVolume.invoke(television);
			assertEquals(3, (int) getCurrentVolume.invoke(television),
					"raiseVolume should have incremented current volume to 3.");

			raiseVolume.invoke(television);
			assertEquals(4, (int) getCurrentVolume.invoke(television),
					"raiseVolume should have incremented current volume to 4.");

			raiseVolume.invoke(television); // 5
			raiseVolume.invoke(television); // 6
			raiseVolume.invoke(television); // 7
			raiseVolume.invoke(television); // 8
			raiseVolume.invoke(television); // 9
			raiseVolume.invoke(television); // 10

			assertEquals(10, (int) getCurrentVolume.invoke(television),
					"raiseVolume should have incremented current volume to 10.");

			raiseVolume.invoke(television); // 10 (max)
			assertEquals(10, (int) getCurrentVolume.invoke(television),
					"raiseVolume should not let current volume go above 10.");

		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}

	@Test
	public void televisionVolumeLowerTests() {
		Class klass = Television.class;

		Constructor<Television> constructor = SafeReflection.getConstructor(klass);
		assertNotNull(constructor, "You do not have the required constructor()");
		try {

			Television television = constructor.newInstance();
			Method turnOn = SafeReflection.getMethod(klass, "turnOn");
			Method getCurrentVolume = SafeReflection.getMethod(klass, "getCurrentVolume");
			Method lowerVolume = SafeReflection.getMethod(klass, "lowerVolume");

			assertNotNull(turnOn, "turnOn method is missing");
			assertNotNull(lowerVolume, "lowerVolume method is missing");

			lowerVolume.invoke(television);
			assertEquals(2, (int) getCurrentVolume.invoke(television),
					"currentVolume should only be decremented if the TV is on.");

			turnOn.invoke(television);
			lowerVolume.invoke(television);
			assertEquals(1, (int) getCurrentVolume.invoke(television),
					"lowerVolume should have decremented current volume to 1.");

			lowerVolume.invoke(television);
			assertEquals(0, (int) getCurrentVolume.invoke(television),
					"lowerVolume should have decremented current volume to 0.");

			lowerVolume.invoke(television);
			assertEquals(0, (int) getCurrentVolume.invoke(television),
					"lowerVolume should not let current volume go below 0.");

		} catch (Exception e) {
			fail("An unknown error occurred with Television class: " + e.getMessage());
		}
	}
}
