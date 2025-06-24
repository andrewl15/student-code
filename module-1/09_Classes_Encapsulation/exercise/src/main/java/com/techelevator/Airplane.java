package com.techelevator;

public class Airplane {
    private String planeNumber;
    private int totalFirstClassSeats;
    private int bookedFirstClassSeats;
    private int totalCoachSeats;
    private int bookedCoachSeats;

    //constructor
    public Airplane(String planeNumber, int totalFirstClassSeats, int totalCoachSeats){
        this.planeNumber = planeNumber;
        this.totalFirstClassSeats = totalFirstClassSeats;
        this.totalCoachSeats = totalCoachSeats;
    }

    //getters
    public String getPlaneNumber() {
        return planeNumber;
    }
    public int getTotalFirstClassSeats(){
        return totalFirstClassSeats;
    }
    public int getBookedFirstClassSeats(){
        return bookedFirstClassSeats;
    }
    public int getTotalCoachSeats(){
        return totalCoachSeats;
    }
    public int getBookedCoachSeats(){
        return bookedCoachSeats;
    }

    //Derived Instance
    public int getAvailableFirstClassSeats(){
        return totalFirstClassSeats - bookedFirstClassSeats;
    }
    public int getAvailableCoachSeats(){
        return totalCoachSeats - bookedCoachSeats;
    }

    //methods
    public boolean reserveSeats(boolean forFirstClass, int totalNumberOfSeats){
        if(forFirstClass){
            if(bookedFirstClassSeats + totalNumberOfSeats <= totalFirstClassSeats){
                bookedFirstClassSeats += totalNumberOfSeats;
                return true;
            }else{
                return false;
            }
        }else{
            if(bookedCoachSeats + totalNumberOfSeats <= totalCoachSeats){
                bookedCoachSeats += totalNumberOfSeats;
                return true;
            }
            else{
                return false;
            }
        }
    }
}

