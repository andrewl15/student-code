package com.techelevator;

import com.techelevator.errors.InvalidData;

import java.util.List;

public interface DataFunctions {
    List<QuizQuestion> generateQuestions() throws InvalidData;
}
