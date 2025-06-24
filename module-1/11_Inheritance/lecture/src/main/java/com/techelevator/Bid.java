package com.techelevator;

public class Bid {
    private int bidAmount;
    private String bidder;

    //constructor
    public Bid(int bidAmount, String bidder){
        this.bidAmount = bidAmount;
        this.bidder = bidder;
    }

    //getters and setters
    public int getBidAmount(){
        return bidAmount;
    }
    public String getBidder(){
        return bidder;
    }


}
