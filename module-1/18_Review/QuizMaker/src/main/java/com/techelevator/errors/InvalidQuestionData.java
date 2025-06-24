package com.techelevator.errors;

import com.techelevator.QuizQuestion;

public class InvalidQuestionData extends Exception {
    public InvalidQuestionData(){
        super("The data is incomplete or unreadable.");
    }
}
