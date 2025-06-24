package com.techelevator;

public class HomeworkAssignment {
    private int earnedMarks;
    private int possibleMarks;
    private String submitterName;

    //constructor
    public HomeworkAssignment(int possibleMarks, String submitterName){
        this.possibleMarks = possibleMarks;
        this.submitterName = submitterName;
    }

    //getters and setter
    public int getEarnedMarks(){
        return earnedMarks;
    }
    public void setEarnedMarks(int earnedMarks){
        this.earnedMarks = earnedMarks;
    }

    public int getPossibleMarks(){
        return possibleMarks;
    }

    public String getSubmitterName(){
        return submitterName;
    }

    //Derived Instance
    public String getLetterGrade(){
        if((double)earnedMarks / possibleMarks * 100 >= 90){
            return "A";
        }else if((double)earnedMarks / possibleMarks * 100 >= 80){
            return "B";
        }else if((double)earnedMarks / possibleMarks * 100 >= 70){
            return "C";
        }else if((double)earnedMarks / possibleMarks * 100 >= 60){
            return "D";
        }else{
            return "F";
        }
    }
}
