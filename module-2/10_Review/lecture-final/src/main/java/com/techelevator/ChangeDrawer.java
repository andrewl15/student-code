package com.techelevator;

import com.techelevator.exceptions.InsufficientFundsException;

import java.math.BigDecimal;

/// <summary>
/// This class will calculate the change required in the smallest number of coins
/// </summary>
public class ChangeDrawer {
    /// <summary>
    /// Local variables to hold the number of nickels, dimes, and quarters.
    /// </summary>
    private int nickels;
    private int dimes;
    private int quarters;
    private BigDecimal totalChange;

    /// <summary>
    /// Public getters for the number of nickels, dimes, and quarters.
    /// </summary>
    public int getNickels() {
        return nickels;
    }

    public int getDimes() {
        return dimes;
    }

    public int getQuarters() {
        return quarters;
    }

    /// <summary>
    /// The is a wrapper method for the overloaded MakeChange.
    /// </summary>
    /// <param name="amountInDollars">Take an amount as decimal</param>
    public void makeChange(BigDecimal amountInDollars) {
        // convert the parameter to cents and an int. Math is nicer with ints. Call the private MakeChange
        processChange(amountInDollars.multiply(new BigDecimal(100)).intValue());
    }

    /// <summary>
    /// Private method that calculates coinage from an amount in pennies
    /// </summary>
    /// <param name="amountInCents">Amount in pennies</param>
    private void processChange(int amountInCents) {
        // Integer math!!! In this case, it works great to give us how many quarters are in the amount
        quarters = amountInCents / 25;
        // Decrement the parameter amount by the value of the quarters
        amountInCents -= quarters * 25;
        // Repeat that process for dimes and nickels
        dimes = amountInCents / 10;
        amountInCents -= dimes * 10;
        nickels = amountInCents / 5;
    }
    /// <summary>
    /// Override the ToString method so I can call Console.WriteLine on the object itself
    /// </summary>
    /// <returns>The string to print to the console</returns>
    public String toString()
    {
        return "Nickels: " + nickels + " | Dimes: " + dimes + " | Quarters: " + quarters;
    }

}
