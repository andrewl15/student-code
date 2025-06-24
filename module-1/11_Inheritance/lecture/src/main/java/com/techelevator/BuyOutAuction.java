package com.techelevator;

public class BuyOutAuction extends Auction{
    private int buyoutPrice;

    public BuyOutAuction(Item itemForSale, int buyoutPrice){
        super(itemForSale);
        this.buyoutPrice = buyoutPrice;
    }

    @Override
    public boolean placeBid(Bid bid){
        //check to see if the auction is over
        if(isSold()){
            return false;
        }
        //if it isnt over

        //then place the bid
        boolean isHighBid = super.placeBid(bid);
        if(bid.getBidAmount() >= buyoutPrice){
            //if it is, end the auction
            super.isSold(true);
        }
        return isHighBid;
        //check if the bid amount >= buyout amount

    }
}
