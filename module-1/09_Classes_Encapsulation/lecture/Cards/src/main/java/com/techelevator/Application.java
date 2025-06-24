package com.techelevator;

public class Application {
    public static void main(String[] args){
        //declare a variable to hold my Card object
        Card firstCard;
        //instantiate Car class into Card object
        firstCard = new Card("Clubs", 9, false);
        //set the card
        firstCard.flipCard(true);

        //write the suit to the screen
        System.out.println("First card is " + firstCard.displayCard());

        firstCard.flipCard();

        System.out.println("First card is " + firstCard.displayCard());

        Deck firstDeck = new Deck();

        firstDeck.shuffle();

        Card dealtCard = firstDeck.dealOne();
        System.out.println(dealtCard.displayCard());

        dealtCard = firstDeck.dealOne();
        System.out.println(dealtCard.displayCard());
    }
}
