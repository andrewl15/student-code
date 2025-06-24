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
		String weather = "Sunny and Cool";
		System.out.println(weather);
		System.out.println("The length of " + weather + " is " + weather.length());
		
		System.out.println();
		System.out.println("******************************");
		System.out.println("****** MEMBER METHODS ******");
		System.out.println("******************************");
		System.out.println();

		String yelling = weather.toUpperCase();
		System.out.println(yelling);
		System.out.println(weather);

		char firstLetter = weather.charAt(0);
		char lastLetter = weather.charAt(weather.length() - 1);
		System.out.println(firstLetter + " " + lastLetter);

		String partOfWeather = weather.substring(2, 8);
		System.out.println(partOfWeather);
		for(int i = 0; i < weather.length(); i++){
			System.out.println(i + " = " + weather.charAt(i));
		}

		String part2ofWeather = weather.substring(5);
		System.out.println(part2ofWeather);

		boolean ifContains = weather.contains("and");
		System.out.println(ifContains);

		System.out.println(weather.startsWith("S"));
		System.out.println(weather.endsWith("L"));


		System.out.println(weather.indexOf("Cool"));

		System.out.println(weather.toUpperCase().contains("COOL"));
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

	}
}
