package com.techelevator;

import com.techelevator.errors.InvalidData;
import com.techelevator.errors.InvalidQuestionData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionsFromFile implements DataFunctions {
    // put these here in case they change down the road
    // and make my code a little easier to read
    private final String ANSWER_MARKER = "*";
    private final String REGEX_DIVIDER = "\\|";
    private final String INPUT_FILE = "test_quiz.txt";
    @Override
    public List<QuizQuestion> generateQuestions() throws InvalidData {
        // set up a holding variable for our output
        List<QuizQuestion> output = new ArrayList<>();
        // open the file for reading
        File questionData = new File(INPUT_FILE);
        try(Scanner fileReader = new Scanner(questionData)){
            while(fileReader.hasNext()){
                // read a line from the file
                String line = fileReader.nextLine();
                // parse it based on the delimiter
                String[] lineParts = line.split(REGEX_DIVIDER);
                // create a string for the questions
                String question = lineParts[0];
                // create a list for the answers
                List<String> tempAnswers = new ArrayList<>();
                int correctAnswer = -1;
                for(int i = 1; i < lineParts.length;i++){
                    // take off any leading/trailing spaces
                    String temp = lineParts[i].trim();
                    // get the right answer index
                    if(temp.endsWith(ANSWER_MARKER)){
                        correctAnswer = i - 1;
                        temp = temp.substring(0,temp.length()-1);
                    }
                    tempAnswers.add(temp);
                }
                // wrap this in a try/catch so if there is an error, we don't have a bad question
                // but we continue to process the file
                try{
                    // create a question object
                    output.add(new QuizQuestion(question,tempAnswers,correctAnswer));
                    // add that object to the holder
                } catch (InvalidQuestionData e){
                    // TODO log the error
                    Log.writeLog("Can't create question from this data: \n\tQuestion: " + question
                       + "\n\tNumber Of Answers: " + tempAnswers.size());
                }

            }

        } catch (FileNotFoundException e){
            // TODO log the error
            Log.writeLog("Can't locate the file " + INPUT_FILE);
            // throw a custom exception to keep our code loosely coupled
            throw new InvalidData();
        }

        return output;
    }
}
