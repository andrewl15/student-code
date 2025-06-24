package com.techelevator.auctions.services;

import com.techelevator.auctions.model.Auction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionServiceTest {

    private static final Auction[] expectedAuctions = {
            new Auction(1, "Zero", "Zero Auction", "User0", 0.0),
            new Auction(2, "One", "One Auction", "User1", 1.1)
    };
    private static final Auction expectedAuction = expectedAuctions[0];

    private static ProcessBuilder processBuilder;
    private static Process process;

    private static AuctionService sut;

    @BeforeAll
    public static void setup() {

        processBuilder = new ProcessBuilder();
        // Windows: node server\\node_modules\\json-server\\lib\\cli\\bin.js server\\data-test.js --host=127.0.0.1 --port=3001
        // Linux:   node server/node_modules/json-server/lib/cli/bin.js server/data-test.js --host=127.0.0.1 --port=3001
        String command = "node";
        String cli = "server" + File.separator + "node_modules" + File.separator + "json-server" +
                File.separator + "lib" + File.separator + "cli" + File.separator + "bin.js";
        String data = "server" + File.separator + "data-test.js";
        String host = "--host=127.0.0.1";
        String port = "--port=3001";
        processBuilder.command(command, cli, data, host, port);
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AuctionService.API_BASE_URL = "http://localhost:3001/auctions";
        sut = new AuctionService();

        if (!pingServer(AuctionService.API_BASE_URL, 10, 500)) { // time-out after ~5 seconds
            throw new RuntimeException("Unable to connect to server: " + AuctionService.API_BASE_URL + System.lineSeparator() + "Make sure you've run `npm install` in the `server` folder and can run json-server.");
        }
    }

    @AfterAll
    public static void afterAll() {
        process.destroyForcibly();
    }

    @Test
    public void getAllAuctions_returns_all_auctions() {

        // Arrange
        // Act
        Auction[] actualAuctions = sut.getAllAuctions();

        // Assert
        assertEquals(expectedAuctions.length, actualAuctions.length, "The number of auctions returned is not the expected value.");
        for (int i = 0; i < actualAuctions.length; i++) {
            assertAuctionsMatch(expectedAuctions[i], actualAuctions[i], "The auction returned is not the same as expected.");
        }
    }

    @Test
    public void getAuction_returns_auction_with_specified_id() {

        // Arrange
        // Act
        Auction actualAuction = sut.getAuction(1);

        // Assert
        assertAuctionsMatch(expectedAuction, actualAuction, "The auction returned is not the same as expected.");
    }

    @Test
    public void getAuctionsMatchingTitle_returns_correct_auctions() {
        // Arrange
        // Act
        Auction[] actualAuctions = sut.getAuctionsMatchingTitle("Zero");

        // Assert
        assertEquals(1, actualAuctions.length, "The number of auctions returned is not the expected value.");
        assertAuctionsMatch(expectedAuctions[0], actualAuctions[0], "The auction returned is not the same as expected.");
    }

    @Test
    public void getAuctionsAtOrBelowPrice_returns_correct_auctions() {
        // Arrange
        // Act
        Auction[] actualAuctions = sut.getAuctionsAtOrBelowPrice(1.00);

        // Assert
        assertEquals(1, actualAuctions.length, "The number of auctions returned is not the expected value.");
        assertAuctionsMatch(expectedAuctions[0], actualAuctions[0], "The auction returned is not the same as expected.");
    }

    private void assertAuctionsMatch(Auction expected, Auction actual, String message) {
        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getTitle(), actual.getTitle(), message);
        assertEquals(expected.getDescription(), actual.getDescription(), message);
        assertEquals(expected.getUser(), actual.getUser(), message);
        assertEquals(expected.getCurrentBid(), actual.getCurrentBid(), 0.01, message);
    }

    private static boolean pingServer(String baseApiUrl, int maxTries, long waitInterval) {

        RestTemplate restTemplate = new RestTemplate();

        int tryCount = 0;
        while (tryCount < maxTries) {
            try {
                tryCount++;
                try {
                    restTemplate.headForHeaders(baseApiUrl);
                    return true;
                } catch (RestClientException e) {
                    // Just eat the exception and try the request again.
                }
                Thread.sleep(waitInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
