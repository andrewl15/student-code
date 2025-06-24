package com.techelevator;

public class Application {
    public static void main(String[] args){
        // declare a variable to hold my Card object
        Card firstCard;
        // instantiate Card class into Card object
        firstCard = new Card("Clubs",2,true);
        System.out.println(firstCard.displayCard());
        // set the suit
//        firstCard.setSuit("Clubs");
        // set the number
//        firstCard.setNumber(2);
        // set the color
//        firstCard.setColor("Black");
        // set the faceupness
        firstCard.setFaceUp(true);
        // write the suit to the screen
        System.out.println(firstCard.getSuit());
        // let's make another
        Card secondCard = new Card("Hearts",4);
//        secondCard.setSuit("Hearts");
//        secondCard.setColor("Purple");
//        secondCard.setNumber(4);
        secondCard.setFaceUp(false);
        System.out.println(secondCard.getSuit());
        // flip the secondCard
        secondCard.flipCard();
        secondCard.flipCard();
        secondCard.flipCard(false);
        System.out.println(firstCard.displayCard());
        System.out.println(secondCard.displayCard());

        // change our card
//        secondCard.setSuit("Diamonds");
//        secondCard.setNumber(8);
        System.out.println(secondCard.displayCard());
    }
}
