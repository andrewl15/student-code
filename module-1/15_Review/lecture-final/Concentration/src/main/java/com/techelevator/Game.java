package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    // create an array to hold the list of cards
    // since we know the size
    private Card[] cards;
    private List<Integer> flipped = new ArrayList<>();
    private List<Integer> matched = new ArrayList<>();

    public Game(int numberOfPairs){
        Deck deck = new Deck();
        // make sure numberOfPairs <= numberOfCardsInDeck
        if(numberOfPairs > deck.getTotalNumberOfCards()){
            // better solution on Monday!!
            numberOfPairs = deck.getTotalNumberOfCards();
        }

        // build my list of cards
        // I need two cards for every pair
        int numberOfSlots = numberOfPairs * 2;
        cards = new Card[numberOfSlots];

        // fill in the cards array
        deck.shuffle();

        while(!fullSlots()){

        // get a random card (dealOne)
        Card temp = deck.dealOne();
        // don't forget to flip it face down
        temp.flipCard(false);
        // put it in the array
        int index = generateRandom(numberOfSlots);
        cards[index] = temp;
        // generate a random spot in the array
        // to put the duplicate
        index = generateRandom(numberOfSlots);
        // put in the second card
        cards[index] = temp;

        // rinse and repeat until array is full
        }
    }

    public int maxNumberOfPairs(){
        return cards.length;
    }
    public String displayBoard(){
        String output = "";
        for(int i = 0; i < cards.length; i++){
            if(flipped.contains(i)){
                cards[i].flipCard(true);
            } else {
                cards[i].flipCard(false);
            }
            if(matched.contains(i)){
                output += "MATCHED" + "\n";
            } else {
                output += (i + 1) + ") " + cards[i].displayCard() + "\n";
            }

        }
        return output;
    }

    public boolean chooseSlot(int slot){
        // is this the third card?
        if(flipped.size() >= 2){
            flipped.clear();
        }
        // make sure it isn't already selected
        // make sure it isn't already matched
        if(flipped.contains(slot-1) || matched.contains(slot-1))
        {
            return false;
        }
        // add this index to our flipped
        // user selects 1 - x, we need 0 - x
        flipped.add(slot-1);

        // return true if it does
        // return false if it doesn't
        return isAMatch();
    }

    public boolean gameOver(){
        return matched.size() == cards.length;
    }
    private boolean isAMatch(){
        // have you already picked a card?
        // check if this card matches that card
        if(flipped.size() == 2){
            // check for a match
            // get the two cards by index
            Card first = cards[flipped.get(0)];
            Card second = cards[flipped.get(1)];
            if(first.equals(second)){
                // it's a match!!
                matched.add(flipped.get(0));
                matched.add(flipped.get(1));
                flipped.clear();
                return true;
            }
        }
        return false;
    }
    private int generateRandom(int max){
        Random generator = new Random();
        int slot = -1;
        do{
            slot = generator.nextInt(max);
        }while(cards[slot] != null);

        return slot;
    }

    private boolean fullSlots(){
        for(Card item : cards){
            if(item == null){
                return false;
            }
        }
        return true;
    }

}
