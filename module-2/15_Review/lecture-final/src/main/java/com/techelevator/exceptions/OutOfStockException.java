package com.techelevator.exceptions;

public class OutOfStockException extends VendingMachineException {
    public OutOfStockException() {
        super("I can't do that, Dave. That slot is empty");
    }
}
