package com.techelevator.exceptions;

public class InvalidDollarAmount extends VendingMachineException {
    public InvalidDollarAmount(){
        super("Please, only positive whole dollar amounts");
    }
}
