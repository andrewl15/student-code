package com.techelevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Deck {
    private int totalNumberOfCards;
    private List<Card> allCards = new ArrayList<>();
    private final String[] allSuits = {"Hearts", "Clubs", "Diamond", "Spades"};
    private final int[] allNumbers = {1,2,3,4,5,6,7,8,9,10,11,12,13};
    public static String[] ranks =
            {"Joker","Ace","Two","Three","Four","Five","Six",
                    "Seven","Eight","Nine","Ten","Jack","Queen","King"};
    //return a copy of the list, not the reference
    public Card[] getAllCards(){
        return allCards.toArray(new Card[0]);
    }

    private Map<Integer, String> rank;
    public int getTotalNumberOfCards(){
        return  allCards.size();
    }


    //deal one card
    public Card dealOne(){
        Card temp = allCards.get(0);
        //remove the card from the deck
        allCards.remove(temp);
        return temp;
    }

    public void shuffle(){
        Collections.shuffle(allCards);
    }
}
