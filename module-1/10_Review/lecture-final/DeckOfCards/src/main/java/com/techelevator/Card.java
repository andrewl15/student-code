package com.techelevator;

public class Card {
    // properties for our Card
    private String suit;
    private int number;
    // remove the variable since this will be a derived property
//    private String color;
    private boolean isFaceUp;

    // custom constructor

    /**
     * Constructor for Card
     * @param suit a string representing the suit
     * @param number an int representing the number/value
     */
    public Card(String suit, int number){
        this.suit = suit;
        this.number = number;
    }
    // an overloaded constructor
    public Card(String suit, int number, boolean faceUp){
        this.suit = suit;
        this.number = number;
        this.isFaceUp =faceUp;
    }
    // getters and setters
    public String getSuit(){
        return suit;
    }
    // derived property
    public String getColor() {
        // a little data validation
        if(suit != null){
            if(suit.equals("Hearts") || suit.equals("Diamonds")){
                return "Red";
            } else {
                return "Black";
            }
        } else {
            return null;
        }
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    // this function is handled by the method flipCard
//    public void setFaceUp(boolean faceUp) {
//        isFaceUp = faceUp;
//    }

    public int getNumber() {
        return number;
    }


    // write a method to display the card

    /**
     * Use this method to display the number and suit of the card
     * Only displays if card is face up.
     * @return string representing the card
     */
    public String displayCard(){
        // check to see if it is faceUp
        if(isFaceUp){
            return Deck.ranks[number] + " of " + suit + " which is " + getColor();
        } else {
            return "###";
        }

    }

    /**
     * Use to flip the card from faceUp to faceDown or faceDown to faceUp
     */
    public void flipCard(){
//        if(isFaceUp){
//            isFaceUp = false;
//        } else {
//            isFaceUp = true;
//        }
        isFaceUp = !isFaceUp;
    }

    // overload the flipCard
    public void flipCard(boolean faceUp){
        isFaceUp = faceUp;
    }
}
