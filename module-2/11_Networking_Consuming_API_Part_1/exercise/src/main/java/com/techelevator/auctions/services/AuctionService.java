package com.techelevator.auctions.services;

import com.techelevator.auctions.model.Auction;
import org.apiguardian.api.API;
import org.springframework.web.client.RestClient;

public class AuctionService {

    public static String API_BASE_URL = "http://localhost:3000/auctions";
    private final RestClient restClient = RestClient.create(API_BASE_URL);


    public Auction[] getAllAuctions() {
        // call api here
        return restClient.get().uri("").retrieve().body(Auction[].class);
    }

    public Auction getAuction(int id) {
        // call api here
        return restClient.get().uri("/" + id).retrieve().body(Auction.class);
    }

    public Auction[] getAuctionsMatchingTitle(String title) {
        // call api here
        return restClient.get().uri("?title_like=" + title).retrieve().body(Auction[].class);
    }

    public Auction[] getAuctionsAtOrBelowPrice(double price) {
        // call api here
        return restClient.get().uri("?currentBid_lte=" + price).retrieve().body(Auction[].class);
    }

}
