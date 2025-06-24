package com.techelevator;

public class Lecture {

	public static void main(String[] args) {
		
		/*
		1. Create a variable to hold an int and call it numberOfExercises.
			Then set it to 26.
		*/
		int numberOfExercises; // <== variable declaration
		numberOfExercises = 26;
		System.out.println(numberOfExercises);

		/*
		2. Create a variable to hold a double and call it half.
			Set it to 0.5.
		*/
		double half = 0.5; // <== declaration and initialization
		System.out.println(half);

		/*
		3. Create a variable to hold a String and call it name.
			Set it to "TechElevator".
		*/
		String name;
		name = "TechElevator";
		System.out.println(name);

		/*
		4. Create a variable called seasonsOfFirefly and set it to 1.
		*/
		int seasonsOfFirefly = 1;

		System.out.println(seasonsOfFirefly);

		/*
		5. Create a variable called myFavoriteLanguage and set it to "Java".
		*/
		String myFavoriteLanguage;
		myFavoriteLanguage = "Java";
		System.out.println(myFavoriteLanguage);

		/*
		6. Create a *constant* called PI and set it to 3.14159.
		*/
		final double PI = 3.14159;
		final double PI_TRUE_VALUE = 3.14159;
		System.out.println(PI);

		/*
		7. Create and set a variable that holds your name.
		*/
		// String name = "Henry Edwards"; <= can't redeclare a variable of the same name
		name = "Henry Edwards";
		String yourName = "Henry Edwards";

		System.out.println(name);
		System.out.println(yourName);
		/*
		8. Create and set a variable that holds the number of buttons on your mouse.
		*/
		int numberOfButtons = 4;
		/*
		9. Create and set a variable that holds the percentage of battery left on
		your phone.
		*/
		String percentageOfBattery = "100%";
		System.out.println(percentageOfBattery);
		/*
		10. Create an int variable that holds the difference between 121 and 27.
		*/
		int firstNumber = 121;
		int secondNumber = 27;
		int difference = firstNumber - secondNumber;
		System.out.println(difference);
		/*
		11. Create a double that holds the addition of 12.3 and 32.1.
		*/
		double result = 12.3 + 32.1;
		System.out.println(result);
		/*
		12. Create a String that holds your full name.
		*/
		String fullName = "Henry Edwards";
		/*
		13. Create a String that holds the word "Hello, " concatenated onto your
		name from above.
		*/
		String greeting = "Hello, " + fullName;
		System.out.println(greeting);
		/*
		14. Add " Esquire" onto the end of your full name and save it back to
		the same variable.
		*/
		greeting = greeting + " Esquire";
		System.out.println(greeting);
		/*
		15. Now do the same as exercise 14, but use the += operator.
		*/
		greeting += " Junior";
		System.out.println(greeting);
		/*
		16. Create a variable to hold "Saw" and add a 2 onto the end of it.
		*/
		String movie = "Saw";
		movie = movie + 2;
		System.out.println(movie);
		/*
		17. Add a 0 onto the end of the variable from exercise 16.
		*/
		movie += 0;
		System.out.println(movie);

		int fifteen = 15;
		double overFifteen = 150;
		overFifteen = fifteen;
		fifteen = (int)overFifteen;

		float halfAgain = 0.5F;
		/*
		18. What is 4.4 divided by 2.2?
		*/
		System.out.println(4.4 / 2.2);
		/*
		19. What is 5.4 divided by 2?
		*/
		double firstNineteen = 5.4;
		int secondNineteen = 2;
		System.out.println(firstNineteen/secondNineteen);
		/*
		20. What is 5 divided by 2?
		*/
		System.out.println("5/2 = " + 5 / 2);
		/*
		21. What is 5.0 divided by 2?
		*/
		System.out.println("5.0/2 = " + 5.0 / 2);
		/*
		22. What is 66.6 divided by 100? Is the answer you get right or wrong?
		*/
		System.out.println(66.6 / 100);
		/*
		23. If I divide 5 by 2, what's my remainder?
		*/
		System.out.println(5 % 2);
		/*
		24. What is 1,000,000,000 * 3?
		*/
		long startingValue = 1_000_000_000;
		int multiplier = 3;
		long answer = startingValue * multiplier;
		System.out.println(answer);
		/*
		25. Create a variable that holds a boolean called isDoneWithExercises and
		set it to false.
		*/
		boolean isDoneWithExercises = false;
		/*
		26. Now set isDoneWithExercise to true.
		*/
		isDoneWithExercises = true;
		
	}

}
