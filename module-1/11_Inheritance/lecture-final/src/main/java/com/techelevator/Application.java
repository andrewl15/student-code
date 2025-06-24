package com.techelevator;

public class Application {

    public static void main(String[] args) {

        // Create a new general auction
        System.out.println("Starting a general auction");
        System.out.println("-----------------");

        // instantiate the item to sell
        Item itemUpForAuction = new Item("Coffee Mug","Henry Edwards");
        // instantiate the auction
        Auction generalAuction = new Auction(itemUpForAuction);
        // let the bidding begin!!
        Bid firstBid = new Bid("Harry",100);
        // place the bid
        if(generalAuction.placeBid(firstBid)){
            System.out.println("Congratulations " + firstBid.getBidder() + ", you are the high bidder!");
        } else {
            System.out.println("Sorry, you aren't the high bidder");
        }

        generalAuction.placeBid(new Bid("Ron",150));
        generalAuction.placeBid(new Bid("Hermione",125));
        generalAuction.placeBid(new Bid("Draco",175));

        System.out.println("The winner is: " + generalAuction.getCurrentHighBid().getBidder());

        System.out.println("Starting a reserve auction");
        System.out.println("-----------------");
        ReserveAuction reserveAuction = new ReserveAuction(new Item("Calculator","Tom"),510);

        reserveAuction.placeBid(new Bid("Snape",500));
        reserveAuction.placeBid(new Bid("Hagrid",750));
        System.out.println("The winner is " + reserveAuction.getCurrentHighBid().getBidder() + " for " + reserveAuction.getCurrentHighBid().getBidAmount());


        System.out.println("Starting a buyout auction");
        System.out.println("-----------------");
        BuyOutAuction buyOutAuction = new BuyOutAuction(new Item("Golf Towel"),125);
        buyOutAuction.placeBid(new Bid("Dumbledore",110));
        buyOutAuction.placeBid(new Bid("Lucius",135));
        buyOutAuction.placeBid(new Bid("Luna",1000));
    }
}
