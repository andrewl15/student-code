package com.techelevator;

public class Lecture {
    /*
    1. This method is named returnNotOne and it returns an int. Change
    it so that it returns something other than a 1.
    */
    public int returnNotOne() {
        int length = 10;
        return 2;

    }

    /*
    2. This method is named returnNotHalf and it returns a double. Change
    it so that it returns something other than a 0.5.
    */
    public double returnNotHalf() {
        return 1.5;
    }

    /*
    3. This method needs to return a String. Fix it to return a valid String.
    */
    public String returnName() {
        String output = "Henry Edwards";
        return output;
    }

    /*
    4. This method currently returns an int. Change it so that it returns a double.
    */
    public double returnDoubleOfTwo() {
        return 2.0;
    }

    /*
    5. This method should return the language that you're learning. Change
    it so that it does that.
    */
    public String returnNameOfLanguage() {
        return "Java";
    }

    /*
    6. This method uses an if statement to define what to return. Have it
    return true if the if statement passes.
    */
    public boolean returnTrueFromIf() {
        if (true) {
            return true;
        }

        return true;
    }

    /*
    7. This method uses an if to check to make sure that one is equal
    to one. Make sure it returns true when one equals one.
    */
    public boolean returnTrueWhenOneEqualsOne() {
        if (1 == 1) {
            return true;
        }

        return false;
    }

    /*
    8. This method checks a parameter passed to the method to see if it's
    greater than 5 and returns true if it is.
    */
    public boolean returnTrueWhenGreaterThanFive(int number) {
        if (number > 5) {
           return true;
        } else {
            return false;
        }
        // return false;
    }

    /*
    9. If you think about it, we really don't need the if statement above.
    How can we rewrite exercise 8 to have only one line of code?
    */
    public boolean returnTrueWhenGreaterThanFiveInOneLine(int number) {
        return number > 5; // What can we put here that returns a boolean that we want?
    }

    /*
    10. This method will take a number and do the following things to it:
    * If addThree is true, we'll add three to that number
    * If addFive is true, we'll add five to that number
    * We'll then return the result
    */
    public int returnNumberAfterAddThreeAndAddFive(int number, boolean addThree, boolean addFive) {
        if (addThree) {
            number = number + 3;
        }

        if (addFive) {
            number += 5;
        }

        return number;
    }

    /*
    11. Write an if statement that returns "Fizz" if the parameter is 3 and returns an empty String for anything else.
    */
    public String returnFizzIfThree(int number) {
        // check to see if the number is 3
        if(number == 3){
            // if it is, return Fizz
            return "Fizz";
        }

        // if it isn't, return empty string ("")
        return "";
    }

    public String returnFizzIfThreeSingleReturn(int number) {
        // create a holding variable for the result
        String result = "";
        // check to see if the number is 3
        if(number == 3){
            // if it is, return Fizz
            result = "Fizz";
        }
        // if it isn't, return the default value ("")

        return result;
    }

    /*
    12. Now write the above using the Ternary operator ?:. If you're not sure what this is, you can Google it.
    */
    public String returnFizzIfThreeUsingTernary(int number) {
        // boolean expression ? true return : false return
        return number == 3 ? "Fizz" : "";
    }

    /*
    13. Write an if/else statement that returns "Fizz" if the parameter is 3, "Buzz" if the parameter is 5 and an empty String for anything else.
    */
    public String returnFizzOrBuzzOrNothing(int number) {
        // holding variable for the result
        String output = "";
        // check if number is 3
        if(number == 3){
            // if it is, set variable to Fizz
            output = "Fizz";
        } // if it isn't, check to see if it is 5
        else if(number == 5){
            // if it is a 5, set variable Buzz
            output = "Buzz";
        }


        // if it is neither, return an empty string
        return output;
    }

    /*
    14. Write an if statement that checks if the parameter number is either equal to or greater than 18.
        Return "Adult" if it is or "Minor" if it's not.
    */
    public String returnAdultOrMinor(int number) {
        if (number >= 18) {
            return "Adult";
        } else {
            return "Minor";
        }
    }

    /*
    15. Now, do it again with a different boolean operation.
    */
    public String returnAdultOrMinorAgain(int number) {
        if (number > 17) {  // (!(number < 18 ))
            return "Adult";
        } else {
            return "Minor";
        }
    }

    /*
    16. Return as above, but also return "Teen" if the number is between 13 and 17 inclusive.
    */
    public String returnAdultOrMinorOrTeen(int number) {
        if (number >= 18) {
            return "Adult";
        } else if (number >= 13) {
            return "Teen";
        } else {
            return "Minor";
        }
    }

    /*
     *  17. Pizza prices are calculated as follows:
     *  Small cheese pizzas are 8.99, medium cheese are 9.99, and large cheese are 10.99
     *  Toppings are 1.00 each if 3 or fewer are ordered
     *  Toppings are 0.75 each if more than 3 are ordered
     *
     *  Return the price of a pizza based on its size ('s', 'm' or 'l') and number of toppings
     */

    // Declare constants for readability and to enforce DRY (Don't Repeat Yourself)
    // Note: Constants are normally declared at the top of Java classes but placed here for
    //       easy reference
    public final double SMALL = 8.99;
    public final double MEDIUM = 9.99;
    public final double LARGE = 10.99;
    public final double UNDER_3_TOPPINGS = 1.00;
    public final double OVER_3_TOPPINGS = 0.75;

    public double returnPizzaCost(char size, int numberOfToppings) {
        // You can declare variables in methods.
        // Declare a variable to hold the total cost of the pizza.
        double totalCost = 0.0;
        // check the size of the pizza, and add the appropriate value to the total cost
        if(size == 's'){
            totalCost += SMALL;
        } else if(size == 'm'){
            totalCost += MEDIUM;
        } else {
            totalCost += LARGE;
        }
        // check to see how many toppings are ordered

        double priceOfToppings = 0.0;

        if(numberOfToppings <= 3){
            // 3 or less toppings, each is $1
            priceOfToppings = numberOfToppings * UNDER_3_TOPPINGS;
            // add that to the total
            totalCost += priceOfToppings;
        } else {
            // more that 3 each is $0.75
            priceOfToppings = numberOfToppings * OVER_3_TOPPINGS;
            // add that to the total
            totalCost += priceOfToppings;
        }

//        double toppingCost = OVER_3_TOPPINGS;
//        if(numberOfToppings <= 3){
//            // 3 or less toppings, each is $1
//            toppingCost = UNDER_3_TOPPINGS;
//        }
//        // more that 3 each is $0.75
//        priceOfToppings = numberOfToppings * toppingCost;
//        // add that to the total
//        totalCost += priceOfToppings;

        return totalCost;
    }

}
