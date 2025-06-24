package com.techelevator;

public class Application {

    public static void main(String[] args) {

        // Create a new general auction
        System.out.println("Starting a general auction");
        System.out.println("-----------------");

        //instantiate the item to sell;
        Item itemUpForAuction = new Item("Master Chief Helmet", "Henry Ford");
        //instantiate the auction
        Auction generalAuction = new Auction(itemUpForAuction);
        //let the bidding start
        Bid firstBid = new Bid(500, "Andrew");
        //place the bid
        if(generalAuction.placeBid(firstBid)){
            System.out.println("Congratulations, you are the high bidder!");
        }else{
            System.out.println("Sorry, you are under the current high bidder");
        }

        generalAuction.placeBid(new Bid(510, "Harry"));
        generalAuction.placeBid(new Bid(550, "Ron"));
        generalAuction.placeBid(new Bid(400,  "Draco"));

        System.out.println("The Winner is: " + generalAuction.getCurrentHighBid().getBidder());

        System.out.println("Starting a reserve auction");
        System.out.println("--------------------------------");
        ReserveAuction reserveAuction = new ReserveAuction(new Item("Calculator", "Tom"), 500);
        reserveAuction.placeBid(new Bid(200, "Hagrid"));
        reserveAuction.placeBid(new Bid(250, "Snape"));

        System.out.println("The winner is: " + reserveAuction.getCurrentHighBid().getBidder());

        System.out.println("Starting a buyout auction");
        System.out.println("--------------------------------");
        BuyOutAuction buyOutAuction = new BuyOutAuction(new Item("Golf Towel"), 120);
        buyOutAuction.placeBid(new Bid(135, "Lucius"));
    }
}
