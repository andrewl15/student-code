package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    //we should have an array because we know the size that we want
    private Card[] cards;
    private List<Integer> flipped = new ArrayList<>();
    private List<Integer> matched = new ArrayList<>();
    public Game(int numberOfPairs){
        Deck deck = new Deck();
        //build my list of cards
        //make sure numberOfPairs <= 52
        if(numberOfPairs > deck.getTotalNumberOfCards()){
            //better solution on Monday
            numberOfPairs = deck.getTotalNumberOfCards();
        }

        //build my list of cards
        //I need two cards for every other pair
        int numberOfSlots = numberOfPairs * 2;
        cards = new Card[numberOfSlots];

        //fill in the cards array
        deck.shuffle();

        while(!fullSlots()) {
            //get a random card
            Card temp = deck.dealOne();
            //flip the card face down
            temp.flipCard(false);
            //put it in the array and generate a random spot in the array for the duplicate
            int index = generateRandom(numberOfSlots);
            cards[index] = temp;
            index = generateRandom(numberOfSlots);

            //put in the second card and rinse and repeat until full
            cards[index] = temp;
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
            }else{
                cards[i].flipCard(false);
            }
            if(matched.contains(i)){
                output += "MATCHED" + "\n";
            }
            else{
                output += (i + 1) + ") " + cards[i].displayCard() + "\n";
            }
        }
        return output;
    }
    public boolean chooseSlot(int slot){
        if(flipped.size() >= 2){
            flipped.clear();
        }
        //make sure it is not already selected
        if(flipped.contains(slot - 1)){
            System.out.println("You cannot choose the same card!");
            return false;
        }
        //add this index to our flipped
        flipped.add(slot - 1);
        return isAMatch();
    }
    public boolean gameOver(){
        return matched.size() == cards.length;
    }
    public boolean isAMatch(){
        //have you already picked a card?

        if(flipped.size() == 2){
            Card first = cards[flipped.get(0)];
            Card second = cards[flipped.get(1)];
            if(first.equals(second)){
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
        int slot;
        do{
            slot = generator.nextInt(max);

        }while(cards[slot] != null);
        return slot;
    }
    private boolean fullSlots(){
        for(Card card: cards){
            if(card == null){
                return false;
            }
        }
        return true;
    }
}
