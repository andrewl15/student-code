package com.techelevator;

public class BuyOutAuction extends Auction{

    private int buyoutPrice;

    public BuyOutAuction(Item itemForSale, int buyoutPrice){
        super(itemForSale);
        this.buyoutPrice = buyoutPrice;
    }

    @Override
    public boolean placeBid(Bid bidMade){
        // check to see if the auction is over (isSold is true)
        if(isSold()){
            return false;
        }
        // if it isn't over
        // then place the bid
        boolean isHighBid = super.placeBid(bidMade);
        // check if the bidamount >= buyout amount
        if(bidMade.getBidAmount() >= buyoutPrice){
            // if it is, end the auction (set isSold to true)
            isSold(true);
        }
        return isHighBid;

    }
}
