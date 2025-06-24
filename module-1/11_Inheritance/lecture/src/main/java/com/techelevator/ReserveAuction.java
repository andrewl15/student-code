package com.techelevator;

public class ReserveAuction extends Auction{
    private int reservePrice;
    public ReserveAuction(Item itemForSale, int reservePrice){
        //call the super (Auction constructor)
        super(itemForSale);
        this.reservePrice = reservePrice;
    }

    @Override
    public boolean placeBid(Bid bid){
        if(bid.getBidAmount() >= reservePrice){
            return super.placeBid(bid);
        }
        return false;
    }
}
