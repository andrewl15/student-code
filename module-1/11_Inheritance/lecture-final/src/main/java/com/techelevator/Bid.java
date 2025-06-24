package com.techelevator;

public class Bid {
    private int bidAmount;
    private String bidder;

    public Bid(String bidder, int bidAmount){
        this.bidAmount = bidAmount;
        this.bidder = bidder;
    }

    public int getBidAmount(){
        return bidAmount;
    }

    public String getBidder(){
        return bidder;
    }
}
