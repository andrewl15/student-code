package com.techelevator;

public class Elevator {
    private int currentFloor;
    private int numberOfFloors;
    private boolean isDoorOpen;

    //constructor
    public Elevator(int numberOfLevels){
        numberOfFloors = numberOfLevels;
        currentFloor = 1;
    }


    //getters
    public int getCurrentFloor(){
        return currentFloor;
    }
    public int getNumberOfFloors(){
        return numberOfFloors;
    }
    public boolean isDoorOpen(){
        return isDoorOpen;
    }

    //methods
    public void openDoor(){
        isDoorOpen = true;
    }
    public void closeDoor(){
        isDoorOpen = false;
    }
    public void goUp(int desiredFloor){
        if(!isDoorOpen){
            if(desiredFloor <= numberOfFloors && desiredFloor > currentFloor){
                currentFloor = desiredFloor;
            }
        }
    }
    public void goDown(int desiredFloor){
        if(!isDoorOpen){
            if(desiredFloor >= 1 && desiredFloor < currentFloor){
                currentFloor = desiredFloor;
            }
        }
    }
}
