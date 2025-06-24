package com.techelevator.errors;

public class InvalidData extends Exception{
    public InvalidData(){
        super("Sorry, Dave, I can't load the questions right now.");
    }
}
