package com.techelevator.errors;

public class InvalidQuestionData extends Exception {
    public InvalidQuestionData(){
        super("The data is incomplete or unreadable.");
    }
}
