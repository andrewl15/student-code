package com.techelevator.auctions.services;

import com.techelevator.auctions.model.Auction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AuctionServiceTest {

    private static ProcessBuilder processBuilder;
    private static Process process;

    private static AuctionService sut;
    private static final String apiBaseUrlTest = "http://localhost:3001/auctions";
    private static final String apiBaseUrlInvalid = "http://localhost:3002/auctions";

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

        AuctionService.API_BASE_URL = apiBaseUrlTest;
        sut = new AuctionService();

        if (!pingServer(AuctionService.API_BASE_URL, 10, 500)) { // time-out after ~5 seconds
            throw new RuntimeException("Unable to connect to server: " + AuctionService.API_BASE_URL + System.lineSeparator() + "Make sure you've run `npm install` in the `server` folder and can run json-server.");
        }
    }

    @AfterAll
    public static void afterAll() {
        process.destroyForcibly();
    }

    @BeforeEach
    public void setupEach() {
        AuctionService.API_BASE_URL = apiBaseUrlTest;
    }

    @Test
    public void add_should_handle_success_and_comm_failure() {

        // Successful add() should return the new auction
        Auction newAuction = new Auction(0, "a", "b", "c", 99.99);
        Auction actualAuction = sut.add(newAuction);
        assertNotNull(actualAuction, "New auction returned is null.");
        assertTrue(actualAuction.getId() > 0, "New auction id is still 0.");

        // add() experiencing communication failure (ResourceAccessException) should return null
        AuctionService.API_BASE_URL = apiBaseUrlInvalid;
        AuctionService sutBaseUrlInvalid = new AuctionService();

        actualAuction = sutBaseUrlInvalid.add(new Auction());
        assertNull(actualAuction, "add() did not return null after experiencing ResourceAccessException.");
    }

    @Test
    public void update_should_handle_success_client_failure_and_comm_failure() {

        // Successful update() should return true
        Auction updateAuction = new Auction(1, "q", "w", "e", 11.11);
        boolean result = sut.update(updateAuction);
        assertTrue(result, "update() did not return true after successfully updating auction.");

        // update() client query error (RestClientResponseException) should return false
        updateAuction = new Auction(9, "q", "w", "e", 11.11); // Auction 9 does not exist.
        result = sut.update(updateAuction);
        assertFalse(result, "update() did not return false after experiencing RestClientResponseException.");

        // update() experiencing communication failure (ResourceAccessException) should return false
        AuctionService.API_BASE_URL = apiBaseUrlInvalid;
        AuctionService sutBaseUrlInvalid = new AuctionService();

        updateAuction = new Auction(1, "r", "x", "f", 12.12);
        result = sutBaseUrlInvalid.update(updateAuction);
        assertFalse(result, "update() did not return false after experiencing ResourceAccessException.");
    }

    @Test
    public void delete_should_handle_success_client_failure_and_comm_failure() {

        // Successful delete() should return true
        boolean result = sut.delete(2);
        assertTrue(result, "delete() did not return true after successfully deleting auction.");

        // delete() client query error (RestClientResponseException) should return false
        result = sut.delete(2); // Auction 2 was deleted above
        assertFalse(result, "delete() did not return false after experiencing RestClientResponseException.");

        // delete() experiencing communication failure (ResourceAccessException) should return false
        AuctionService.API_BASE_URL = apiBaseUrlInvalid;
        AuctionService sutBaseUrlInvalid = new AuctionService();

        result = sutBaseUrlInvalid.delete(1);
        assertFalse(result, "delete() did not return false after experiencing ResourceAccessException.");
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
