package com.techelevator;

import java.util.Scanner;

class DiscountCalculator {

    /**
     * The main method is the start and end of our program
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Discount Calculator");

        // Prompt the user for a discount amount
        // The answer needs to be saved as a double
        double parseStringToDouble = 0.0;
        int count = 0;
        do{
            if(count > 0) {
                System.out.println("Please enter a discout between between 0 and 1");
            }
            System.out.print("Enter the discount amount (w/out percentage): ");

            String discount = scanner.nextLine();
            count += 1;
            parseStringToDouble = Double.parseDouble(discount) / 100;
        }while(parseStringToDouble < 0 || parseStringToDouble > 1);

        // Prompt the user for a series of prices
        System.out.print("Please provide a series of prices (space separated): ");
        String userPrices = scanner.nextLine();

        String[] priceArray = userPrices.split(" ");

        for(int i = 0; i < priceArray.length; i++){
            double originalPrice = Double.parseDouble(priceArray[i]);

            double amountOff = originalPrice * parseStringToDouble;

            double salePrice = originalPrice - amountOff;

            System.out.println("Original Price: " + originalPrice + " Amount Off: " + amountOff + " Sale Price: " + salePrice);
        }
        scanner.close();
    }

}