package com.techelevator;

import java.util.Scanner;

public class LinearConvert {

	public static void main(String[] args) {
		//Please enter the length: 58
		//Is the measurement in (m)eters, or (f)eet? f
		//58f is 17m.
		Scanner input = new Scanner(System.in);
		//Get temperature input and store it in tempInput
		System.out.print("Please enter the length: ");  // prints out a message
		String lengthInput = input.nextLine();
		//Check whether the input is an F or a C
		String lengthType;


		do {
			System.out.print("Is the measurement in (m)meters, or (f)eet? ");  // prints out a message
			lengthType = input.nextLine();
		} while (!lengthType.equals("m") && !lengthType.equals("f")); // Loop until valid input is provided
		//do calculations based on Celsius or Fahrenheit

		//The foot to meter conversion formula is:
		//m = f * 0.3048
		//The meter to foot conversion formula is:
		//
		//f = m * 3.2808399
		if(lengthType.equals("m")){
			int feetCalculation = (int)(Double.parseDouble(lengthInput) * 3.2808399);
			System.out.println(lengthInput + lengthType + " is " + feetCalculation + "f");
		}else{
			int metersCalculation = (int)(Double.parseDouble(lengthInput) * 0.3048);
			System.out.println(lengthInput + lengthType + " is " + metersCalculation + "m");
		}
	}

}
