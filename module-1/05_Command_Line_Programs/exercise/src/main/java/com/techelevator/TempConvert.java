package com.techelevator;

import java.util.Scanner;

public class TempConvert {

	public static void main(String[] args) {
		//Please enter the temperature: 58
		//Is the temperature in (C)elsius, or (F)ahrenheit? F
		//58F is 14C.
		Scanner input = new Scanner(System.in);
		//Get temperature input and store it in tempInput
		System.out.print("Please enter the temperature: ");  // prints out a message
		String tempInput = input.nextLine();
		//Check whether the input is an F or a C
		String tempType;
		do {
			System.out.print("Is the temperature in (C)elsius, or (F)ahrenheit? ");  // prints out a message
			tempType = input.nextLine();
		} while (!tempType.equals("C") && !tempType.equals("F")); // Loop until valid input is provided
		//do calculations based on Celsius or Fahrenheit
		if(tempType.equals("C")){
			int temperatureFahrenheit = (int)(Integer.parseInt(tempInput) * 1.8 + 32);
			System.out.println(tempInput + tempType + " is " + temperatureFahrenheit + "F");
		}else{
			int temperatureCelsius = (int)((Integer.parseInt(tempInput) - 32) / 1.8);
			System.out.println(tempInput + tempType + " is " + temperatureCelsius + "C");
		}
	}

}
