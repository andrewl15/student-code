package com.techelevator.exceptions;
    // Base class for vending machine specific errors
    // All custom classes are going to inherit from this class so try/catch only
    // has to look at one type
public class VendingMachineException extends Exception {
    public VendingMachineException(String message) {
        super(message);
    }
}
