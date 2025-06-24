package com.techelevator.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RTNValidator {
	
	private static final int[] CHECKSUM_WEIGHTS = new int[] { 3, 7, 1, 3, 7, 1, 3, 7, 1 };

	public static void main(String[] args) throws FileNotFoundException {

		printApplicationBanner();
		
		File inputFile = getInputFileFromUser();
        if (inputFile != null) {
			// make sure I can process the file without errors
			try{
				try (Scanner fileScanner = new Scanner(inputFile)) {
					while (fileScanner.hasNextLine()) {
						String line = fileScanner.nextLine();
						try{
							// make sure I can process individual numbers
							String rtn = line.substring(0, 9);

							if (checksumIsValid(rtn) == false) {
								System.out.println(line);
							}
						} catch (Exception e){
							System.out.println("That number doesn't compute. " + line);
						}

					}

				}
			} catch (Exception e){
				System.out.println("Ooops, something went wrong.");
				System.out.println(e.getMessage());
			}

        }
	}

	private static void printApplicationBanner() {
		System.out.println("******************");
		System.out.println("RTN Validator 9000");
		System.out.println("******************");
		System.out.println();
	}

	private static File getInputFileFromUser() {
		Scanner userInput = new Scanner(System.in);
		System.out.print("Please enter path to input file >>> ");
		String path = userInput.nextLine();
		
		File inputFile = new File(path);
		if (inputFile.exists() == false) { // checks for the existence of a file
            System.out.println(path + " does not exist");
            return null;
		} else if (inputFile.isFile() == false) {
            System.out.println(path + " is not a file");
            return null;
		}
		return inputFile;
	}

	private static boolean checksumIsValid(String routingNumber) {
		int checksum = 0;
		for (int i = 0; i < 9; i++) {
			int digit = Integer.parseInt(routingNumber.substring(i, i+1));
			checksum += digit * CHECKSUM_WEIGHTS[i];
		}
		return checksum % 10 == 0;
	}
}
