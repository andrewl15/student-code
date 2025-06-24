package com.techelevator.fruittree;

public class FruitTree {
    private final String typeOfFruit;
    private int piecesOfFruitLeft;

    public FruitTree(String typeOfFruit, int startingPiecesOfFruit) {
        this.typeOfFruit = typeOfFruit;
        this.piecesOfFruitLeft = Math.max(startingPiecesOfFruit, 0); // Ensure non-negative value
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
