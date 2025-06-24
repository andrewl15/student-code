package com.techelevator;

import com.techelevator.errors.InvalidQuestionData;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestion {
  //  private final String ANSWER_MARKER = "*";
    private String question;
    private List<String> answers = new ArrayList<>();
    private int correctAnswer = -1;

    public QuizQuestion(String question, List<String> answers, int correctAnswer) throws InvalidQuestionData {
        if(question == null || answers == null || correctAnswer < 0){
            //bad data was passed in
            throw new InvalidQuestionData();
        }
        if(question.isEmpty() || question.isBlank() || answers.size() == 0){
            //bad data passed in
            throw new InvalidQuestionData();
        }
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrectAnswer(int selectedAnswer){
        return correctAnswer == selectedAnswer;
    }

    //lets return a copy of the answers
    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return new ArrayList<>(answers);
    }
}
