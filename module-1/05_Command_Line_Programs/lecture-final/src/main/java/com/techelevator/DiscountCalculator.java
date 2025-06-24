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
        // keep prompting them as long as the discount is not between 0 and 1
        double discountAsDouble = 0.0;
        do {
            System.out.print("Enter the discount amount (w/out percentage): ");
            // grabs the info from the keyboard and returns it as a String
            String userDiscount = scanner.nextLine();
            // The answer needs to be saved as a double
            discountAsDouble = Double.parseDouble(userDiscount) / 100;
        }while(discountAsDouble < 0 || discountAsDouble > 1 );


        // Prompt the user for a series of prices
        System.out.print("Please provide a series of prices (space separated): ");
        String userPrices = scanner.nextLine();

        // split the string of userPrices on a space and get back an array of Strings
        String[] priceArray = userPrices.split(" ");

        // apply discount to the user prices
        for(int i = 0; i < priceArray.length; i++){
            // get the price and convert to a double
            double originalPrice = Double.parseDouble(priceArray[i]);

            // calculate the amount off
            double amountOff = originalPrice * discountAsDouble;

            // calculate the sales price
            double salePrice = originalPrice - amountOff;

            // print it to the console
            System.out.println("Original price: " + originalPrice + " Amount off: " + amountOff + " Sale price: " + salePrice);
        }

        scanner.close();

    }

}