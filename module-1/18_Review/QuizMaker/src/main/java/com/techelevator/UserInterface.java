package com.techelevator;

import com.techelevator.errors.InvalidData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    //only one scanner on sIn
    private Scanner keyboard = new Scanner(System.in);

    private List<QuizQuestion> questionList = new ArrayList<>();
    DataFunctions dataFunctions = new QuestionsFromFile();

    public void startMeUp(){
        try{
            // get the questions for the quiz
            questionList = dataFunctions.generateQuestions();
            takeQuiz();

        } catch (InvalidData e) {
            System.out.println("No questions right now. :(");
            System.out.println(e.getMessage());

            // TODO log the error
        }


    }

    private void takeQuiz(){
        int numberOfCorrectAnswers = 0;

        for(QuizQuestion item: questionList){
            // display the questions
            System.out.println(item.getQuestion());
            //display answers
            for(int i = 0; i < item.getAnswers().size(); i++){
                System.out.println((i + 1) + ") " + item.getAnswers().get(i));
            }
            int usersAnswer = -1;
            // loop through asking for answers until they give us a valid answer
            do{
                try{
                    System.out.print("What is the answer? ");
                    // get the answer from the user
                    usersAnswer = Integer.parseInt(keyboard.nextLine());
                }catch (Exception e){
                    System.out.println("That is not a valid answer");
                    // TODO log the error
                }
            }while(usersAnswer < 1 && usersAnswer > item.getAnswers().size());
            // see if they are correct
            // adjust to display vs index
            if(item.isCorrectAnswer(usersAnswer - 1)){
                System.out.println("Correct!");
                numberOfCorrectAnswers++;
            }else{
                System.out.println("Incorrect :(");
            }
            // keep a tally of the correct answers
        }

        // all done with the quiz
        System.out.println("You got " + numberOfCorrectAnswers + " correct out of " + questionList.size());

    }
}
