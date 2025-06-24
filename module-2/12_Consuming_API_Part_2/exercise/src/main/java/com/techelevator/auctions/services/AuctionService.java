package com.techelevator.auctions.services;

import com.techelevator.auctions.model.Auction;
import com.techelevator.util.BasicLogger;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

public class AuctionService {

    public static String API_BASE_URL = "http://localhost:3000/auctions";
    private RestClient restClient = RestClient.create(API_BASE_URL);


    public Auction add(Auction newAuction) {
        // place code here
        Auction output = null;
        try{
            output = restClient.post()
                    .uri("")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newAuction)
                    .retrieve()
                    .body(Auction.class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }

    public boolean update(Auction updatedAuction) {
        // place code here
        boolean success = false;
        try{
            restClient.put()
                    .uri("/" + updatedAuction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedAuction)
                    .retrieve()
                    .body(Auction.class);
            success = true;
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public boolean delete(int auctionId) {
        // place code here
        boolean success = false;
        try{
            restClient.delete()
                    .uri("/" + auctionId)
                    .retrieve()
                    .toBodilessEntity();
            success = true;
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public Auction[] getAllAuctions() {

        Auction[] auctions = null;
        try {
            auctions = restClient.get()
                    .retrieve()
                    .body(Auction[].class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auctions;
    }

    public Auction getAuction(int id) {

        Auction auction = null;
        try {
            auction = restClient.get()
                    .uri("/" + id)
                    .retrieve()
                    .body(Auction.class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auction;
    }

    public Auction[] getAuctionsMatchingTitle(String title) {

        Auction[] auctions = null;
        try {
            auctions = restClient.get()
                    .uri("?title_like=" + title)
                    .retrieve()
                    .body(Auction[].class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auctions;
    }

    public Auction[] getAuctionsAtOrBelowPrice(double price) {

        Auction[] auctions = null;
        try {
            auctions = restClient.get()
                    .uri("?currentBid_lte=" + price)
                    .retrieve()
                    .body(Auction[].class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auctions;
    }

}
