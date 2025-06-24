package com.techelevator.auctions.services;

import com.techelevator.auctions.model.Auction;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

public class AuctionService {

    public static final String API_BASE_URL = "http://localhost:8080/auctions";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Auction[] getAllAuctions() {

        Auction[] auctions = null;
        try {
            auctions = restClient.get()
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .body(Auction[].class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auctions;
    }

    public Auction getAuction(int id) {

        Auction auction = null;
        try {
            auction = restClient.get()
                    .uri("/" + id)
                    .header("Authorization","Bearer "+ authToken)
                    .retrieve()
                    .body(Auction.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auction;
    }

    public Auction[] getAuctionsMatchingTitle(String title) {

        Auction[] auctions = null;
        try {
            auctions = restClient.get()
                    .uri("?title_like=" + title)
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .body(Auction[].class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auctions;
    }

    public Auction[] getAuctionsAtOrBelowPrice(double price) {

        Auction[] auctions = null;
        try {
            auctions = restClient.get()
                    .uri("?currentBid_lte=" + price)
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .body(Auction[].class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return auctions;
    }

    public Auction add(Auction newAuction) {

        Auction returnedAuction = null;
        try {
            returnedAuction = restClient.post()
                    .header("Authorization", "Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newAuction)
                    .retrieve()
                    .body(Auction.class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedAuction;
    }

    public boolean update(Auction updatedAuction) {

        boolean success = false;
        try {
            restClient.put()
                    .uri("/" + updatedAuction.getId())
                    .header("Authorization", "Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedAuction)
                    .retrieve()
                    .toBodilessEntity();

            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public boolean delete(int auctionId) {

        boolean success = false;
        try {
            restClient.delete()
                    .uri("/" + auctionId)
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .toBodilessEntity();

            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

}
