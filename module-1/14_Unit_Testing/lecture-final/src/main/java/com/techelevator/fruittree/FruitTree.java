package com.techelevator.fruittree;

public class FruitTree {
    private final String typeOfFruit;
    private int piecesOfFruitLeft;

    public FruitTree(String typeOfFruit, int startingPiecesOfFruit) {
        if(startingPiecesOfFruit < 0){
            startingPiecesOfFruit = 0;
        }
        this.piecesOfFruitLeft = startingPiecesOfFruit;
        this.typeOfFruit = typeOfFruit;
    }

    public String getTypeOfFruit() {
        return typeOfFruit;
    }

    public int getPiecesOfFruitLeft() {
        return piecesOfFruitLeft;
    }

    public boolean pickFruit(int numberOfPiecesToRemove) {
        if (piecesOfFruitLeft - numberOfPiecesToRemove >= 0) {
            piecesOfFruitLeft -= numberOfPiecesToRemove;
            return true;
        } else {
            return false;
        }
    }
}
