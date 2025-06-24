package com.techelevator;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        String nameOne = "Henry Edwards";
        String nameTwo = "Henry Edwards";

        System.out.println(nameOne + " equals " + nameTwo);
        System.out.println(nameOne == nameTwo);
        System.out.println(nameOne.equals(nameTwo));

        String[] superheroes = new String[4];
        superheroes[0] = "Iron Man";
        superheroes[1] = "Black Widow";
        superheroes[2] = "Thor";
        superheroes[3] = "Captain Marvel";

        System.out.println("superheroes[2]: " + superheroes[2]);

        System.out.println("Press enter to continue....");
        userInput.nextLine();

        String[] superheroCopy = superheroes;
        superheroCopy[2] = "Dr. Strange";
        System.out.println("superheroes[2]: " + superheroes[2]);
        System.out.println("superheroCopy[2]: " + superheroCopy[2]);
        System.out.println(superheroes == superheroCopy);

        System.out.println("Press enter to continue....");
        userInput.nextLine();

        int number = 1;
        int numberCopy = number;
        numberCopy = 2;

        System.out.println("number: " + number);
        System.out.println("numberCopy: " + numberCopy);

        System.out.println("Press enter to continue....");
        userInput.nextLine();

    }

}
