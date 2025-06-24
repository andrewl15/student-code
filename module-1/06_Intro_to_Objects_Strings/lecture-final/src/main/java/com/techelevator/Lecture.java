package com.techelevator;

public class Lecture {

	public static void main(String[] args) {

		System.out.println("************************************");
		System.out.println("****** MAKING A STRING OBJECT ******");
		System.out.println("************************************");

		/* The String class gets special treatment in the Java language.  One
		 * example of this is that there is a literal representation of a
		 * String (i.e. characters appearing between two double quotes.  This
		 * is not the case for most classes */

		/* create an new instance of String using a literal */
		String weather = "Sunny and cool";
		System.out.println(weather);
		System.out.println(weather.length());

		// don't have to do all this instantiation with the String object.
		String greeting = new String("Hello World");
		System.out.println(greeting);

		// the literal string is also a String object
		System.out.println("Hello Mars".length());

		System.out.println();
		System.out.println("******************************");
		System.out.println("****** MEMBER METHODS ******");
		System.out.println("******************************");
		System.out.println();

		// the return is lost without a variable to catch it
		// greeting remains unchanged
		greeting.toLowerCase();
		System.out.println(greeting);
		String yelling = greeting.toUpperCase();
		System.out.println(yelling);
		System.out.println(greeting);

		System.out.println("**************** charAt *******************");
		char firstLetter = greeting.charAt(0);
		char lastLetter = greeting.charAt(greeting.length() - 1);
		System.out.println(firstLetter + " " + lastLetter);

		System.out.println();

		System.out.println("***************** substring ********************");
		// give me all the characters starting at the the index specified by the first number
		// up to but not including the character at the index specified by the second number
		String partOfWeather = weather.substring(2,8);
		System.out.println(partOfWeather);
		for (int i = 0; i < weather.length(); i++) {
			System.out.println(i + " -- " + weather.charAt(i));
		}

		// give me all the characters starting at the the index specified by the number to the end of the string
		String partOfGreeting = greeting.substring(5);
		System.out.println(partOfGreeting);

		System.out.println("************************* contains ***********************");
		boolean hasSunny = weather.contains("Sunny");
		if(hasSunny){
			System.out.println("Hooray, I'm going golfing!!");
		} else {
			System.out.println("Guess I'll stay in and clean the garage");
		}

		System.out.println("********************* starts with / ends with *****************");
		boolean greetWorld = greeting.endsWith("rld");
		if(greetWorld){
			System.out.println("Yes it does");
		}
		boolean weatherStarting = weather.startsWith("S");
		System.out.println(weatherStarting);

		System.out.println("******************** index of *******************");
		int startOfCool = weather.indexOf("cool");
		System.out.println("Cool is at index: " + startOfCool);
		// method chaining
		//                    vvvvvvvvvvvvv  returns a String object
		startOfCool = weather.toUpperCase().indexOf("COOL");
		System.out.println("New index: " + startOfCool);

		System.out.println("******************* replace ****************");
		String funnyWeather = weather.replace("n","m");
		System.out.println(funnyWeather);
		System.out.println(weather);

		System.out.println("********************* immutability ****************");
		String base = "This is one string";
		base = "This another string";

		/* Other commonly used methods:
		 *
		 * endsWith
		 * startsWith
		 * indexOf
		 * lastIndexOf
		 * length
		 * substring
		 * toLowerCase
		 * toUpperCase
		 * trim
		 */

		System.out.println();
		System.out.println("**********************");
		System.out.println("****** EQUALITY ******");
		System.out.println("**********************");
		System.out.println();

        char[] helloArray = new char[] { 'H', 'e', 'l', 'l', 'o' };
        String hello1 = new String(helloArray);
        String hello2 = new String(helloArray);

		/* Double equals will compare to see if the two variables, hello1 and
		 * hello2 point to the same object in memory. Are they the same object? */
		if (hello1 == hello2) {
			System.out.println("They are equal!");
		} else {
			System.out.println(hello1 + " is not equal to " + hello2);
		}

		String hello3 = hello1;
		if (hello1 == hello3) {
			System.out.println("hello1 is the same reference as hello3");
		}

		/* So, to compare the values of two objects, we need to use the equals method.
		 * Every object type has an equals method */
		if (hello1.equals(hello2)) {
			System.out.println("They are equal!");
		} else {
			System.out.println(hello1 + " is not equal to " + hello2);
		}

		String name = "Henry M Edwards";
		// get the last name
		int indexOfSpace = name.lastIndexOf(" ");
		if(indexOfSpace >= 0){
			System.out.println(name.substring(indexOfSpace + 1));
		}
	}
}
