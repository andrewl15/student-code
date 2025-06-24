package com.techelevator;

public class ReserveAuction extends Auction{

    // if your bid doesn't meet the reserve, I don't care
    private int reservePrice;

    public ReserveAuction(Item itemForSale, int reservePrice){
        // call the super (Auction) constructor
        // HAS TO BE THE FIRST LINE IN THE SUBCLASS'S CONSTRUCTOR!!
        super(itemForSale);
        this.reservePrice = reservePrice;
    }

    @Override
    public boolean placeBid(Bid bidMade){
        if(bidMade.getBidAmount() >= reservePrice){
            return super.placeBid(bidMade);
        }
        return false;
    }
}
