package com.techelevator.exceptions;

/// <summary>
/// This exception is thrown if the user doesn't have enough funds to buy the item in the slot.
/// </summary>
public class InsufficientFundsException extends VendingMachineException{
    public InsufficientFundsException(){
        super("Feed me, Seymour! Money, that is.");
    }
}
