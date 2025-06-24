package com.techelevator;

import java.util.Scanner;

public class Fibonacci {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a number: ");
		String stopNumber = input.nextLine();

		int convertedStop = Integer.parseInt(stopNumber);
		if(convertedStop <= 0){
			System.out.println(convertedStop + " -> " + 0 + ", " + 1);
		}else if(convertedStop == 1){
			System.out.println(convertedStop + " -> " + 0 + ", " + 1 + ", " + 1);
		}else{
			int firstPrev = 0;
			int secondPrev = 1;
			System.out.print(convertedStop + " -> " + firstPrev + ", " + secondPrev);
			do{
				System.out.print(", " + (firstPrev + secondPrev));
				int temp = firstPrev;
				firstPrev = secondPrev;
				secondPrev = temp + secondPrev;
			}while(firstPrev + secondPrev <= convertedStop);
		}

	}

}
