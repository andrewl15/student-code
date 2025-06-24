package com.techelevator.exceptions;

public class InvalidSlotIDException extends VendingMachineException {
    public InvalidSlotIDException() {
        super("Hmmm, that slot doesn't register. Maybe try a different slot?");
    }
}
