package com.techelevator;

import com.techelevator.errors.InvalidData;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    // only one scanner on System.in
    private Scanner keyboard = new Scanner(System.in);
    private List<QuizQuestion> questionList = new ArrayList<>();
    DataFunctions dataFunctions = new QuestionsFromFile();

    public void startMeUp(){
        try{
            // get the questions for the quiz
            questionList = dataFunctions.generateQuestions();
            takeQuiz();
        } catch (InvalidData e) {
            System.out.println("Please come back later, I don't have any questions for you.");
            System.out.println(e.getMessage());
            // TODO log the error
        }
    }
    // only accessible inside the object
    private void takeQuiz(){
        int numberOfCorrectAnswers = 0;

        for(QuizQuestion item : questionList){
            // display the questions
            System.out.println(item.getQuestion());
            // display the answers
            for (int i = 0; i < item.getAnswers().size(); i++) {
                System.out.println((i + 1) + ") " + item.getAnswers().get(i));
            }
            int usersAnswer = -1;
            // loop through asking for answers until they give a valid answer
            do{
                try{
                    // get the answer from the user
                    System.out.print("What is your answer? ");
                    usersAnswer = Integer.parseInt(keyboard.nextLine());
                } catch (Exception e){
                    System.out.println("That is not a valid answer.");
                    // TODO log the error
                }
            }while(usersAnswer < 1 || usersAnswer > item.getAnswers().size());

            // see if they are correct
            // need to adjust for display vs index
            if(item.isCorrectAnswer(usersAnswer - 1)){
                System.out.println("Hooray! You are correct!;");
                numberOfCorrectAnswers++;
            } else {
                System.out.println("whomp whomp. That is incorrect.");
            }
            // keep a tally of correct answers
        }

        // all done with the quiz
        System.out.println("You got " + numberOfCorrectAnswers + " correct out of " + questionList.size() + " questions.");
    }
}
