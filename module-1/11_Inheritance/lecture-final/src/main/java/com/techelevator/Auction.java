package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    private Bid currentHighBid;
    private Item itemForSale;
    private boolean isSold;
    private List<Bid> allBids = new ArrayList<>();

    public Auction(Item itemToSell){
        itemForSale = itemToSell;
        currentHighBid = new Bid("None",0);
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

    protected void isSold(boolean isItSold){
        isSold = isItSold;
    }
    /**
     * Take a bid object and return true if highest bid
     * @param bidMade the bid made on the item
     * @return true if highest bid
     */
    public boolean placeBid(Bid bidMade){
        // store the bid in allbids
        allBids.add(bidMade);
        // check to see if it is the highest bid
        if(currentHighBid.getBidAmount() < bidMade.getBidAmount()){
            // if it is, remember the new high bid.
            // then return true
            currentHighBid = bidMade;
            return true;
        }
        // if it is, return false
        return false;
    }
}
