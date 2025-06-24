package com.techelevator;

import com.techelevator.errors.InvalidQuestionData;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestion {
    // rethink this as it ties us to the file structure
//    private final String ANSWER_MARKER = "*";
    private String question;
    private List<String> answers = new ArrayList<>();
    private int correctAnswer = -1;

    // let's return a copy
    public List<String> getAnswers() {
        // passing the answers into the ArrayList constructor makes a copy
        return new ArrayList<>(answers);
    }

    public String getQuestion() {
        return question;
    }
    // custom constructor so we don't have a blank question
    public QuizQuestion(String question, List<String> answers, int correctAnswer) throws InvalidQuestionData {
        // data validation
        if(question == null || answers == null || correctAnswer < 0){
            // bad data was passed in
            throw new InvalidQuestionData();
        }
        // this data validation must come after the null check
        if(question.isEmpty() || question.isBlank() || answers.size() == 0){
            // bad data was passed in
            throw new InvalidQuestionData();
        }
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrectAnswer(int selectedAnswer){
        return correctAnswer == selectedAnswer;
    }
}
