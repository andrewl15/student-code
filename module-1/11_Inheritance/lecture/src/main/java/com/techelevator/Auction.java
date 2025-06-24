package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    private Bid currentHighBid;
    private Item itemForSale;
    private boolean isSold;
    private List<Bid> allBids;

    public Auction(Item itemForSale){
        this.itemForSale = itemForSale;
        this.allBids = new ArrayList<>();
        currentHighBid = new Bid(0, "None");
    }
    public Bid getCurrentHighBid(){
        return currentHighBid;
    }
    public Item getItemForSale(){
        return itemForSale;
    }
    public boolean isSold(){
        return isSold;
    }
    public void isSold(boolean isSold){
        this.isSold = isSold;
    }


    /**
     * Take a bid object and return true if highest bid
     * @param bid the bid made on the item
     * @return true if it is the highest bid
     */
    public boolean placeBid(Bid bid){
        //store the bid in allbids
        allBids.add(bid);
        //check to see if it is the highest bid
        if(bid.getBidAmount() > currentHighBid.getBidAmount()){
            //if it is remember the new high bid
            currentHighBid = bid;
            //then return true
            return true;
        }else{
            //if it isnt retrurn false
            return false;
        }
    }
}
