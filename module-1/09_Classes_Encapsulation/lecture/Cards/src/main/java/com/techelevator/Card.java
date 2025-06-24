package com.techelevator;

public class Card {
    //properties for our card
    private String suit;
    private int number;
    private boolean isFaceUp;

    //constructor
    public Card(String suit, int number){
        this.suit = suit;
        this.number = number;
    }
    //overloaded constructor
    public Card(String suit, int number, boolean faceUp){
        this.suit = suit;
        this.number = number;
        this.isFaceUp = faceUp;
    }

    // getters and setter
    public String getSuit(){
        return suit;
    }
    //derived property
    public String getColor(){
        if(suit != null) {
            if (suit.equals("Hearts") || suit.equals("Diamonds")) {
                return "Red";
            } else {
                return "Black";
            }
        }else{
            return null;
        }
    }

    public int getNumber(){
        return number;
    }

    public boolean isFaceUp(){
        return isFaceUp;
    }

//    public void setFaceUp(boolean faceUp){
//        isFaceUp = true;
//    }

    public String displayCard(){
        if(isFaceUp){
            return Deck.ranks[number] + " of " + suit + " the color is " + getColor();
        }else{
            return "Set down";
        }

    }

    public void flipCard(){
        if(isFaceUp){
            isFaceUp = false;
        }else{
            isFaceUp = true;
        }
    }
    //overload flip
    public void flipCard(boolean faceUp){
        isFaceUp = faceUp;
    }
}
