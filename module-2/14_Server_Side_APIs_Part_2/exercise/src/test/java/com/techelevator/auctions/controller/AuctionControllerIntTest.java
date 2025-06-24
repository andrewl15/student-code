package com.techelevator.auctions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.auctions.model.Auction;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuctionController.class)
@AutoConfigureMockMvc(print=MockMvcPrint.NONE)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AuctionControllerIntTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @Test
    public void create_ValidAuction_ShouldAddNewAuction() throws Exception {
        final Auction auction = new Auction(
                "Standing Desk",
                "Stand up desk to help you stretch your legs during the day.",
                "Johnnie34",
                350.00);

        MvcResult mvcResult = mvc.perform(post("/auctions").contentType(MediaType.APPLICATION_JSON).content(toJson(auction))).andReturn();

        verifyRouteExists(mvcResult);
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status, "Expected CREATED status");

        Auction returnedAuction = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(returnedAuction, "Expected an auction to be returned but received null");
        assertTrue(returnedAuction.getId() != 0, "Expected id to not equal 0");
        assertEquals(auction.getTitle(), returnedAuction.getTitle(), "Expected title did not match returned title");
        assertEquals(auction.getDescription(), returnedAuction.getDescription(), "Expected description did not match returned description");
        assertEquals(auction.getUser(), returnedAuction.getUser(), "Expected user did not match returned user");
        assertEquals(auction.getCurrentBid(), returnedAuction.getCurrentBid(), 0.001, "Expected currentBid did not match returned currentBid");
    }

    @Test
    public void create_InvalidAuction_ShouldNotBeCreated() throws Exception {
        final Auction auction = new Auction(
                "",
                "",
                "",
                0);

        MvcResult mvcResult = mvc.perform(post("/auctions").contentType(MediaType.APPLICATION_JSON).content(toJson(auction))).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(400, mvcResult.getResponse().getStatus(), "Expected Bad Request status 400 when POST request contains invalid auction.");
    }

    @Test
    public void update_ValidAuction_ShouldUpdateExistingAuction() throws Exception {
        Auction auction = new Auction(1,
                "Bell Computer Monitor",
                "4K LCD monitor from Bell Computers, HDMI & DisplayPort",
                "Queenie34",
                100.39);

        auction.setTitle("MY_NEW_TITLE");

        MvcResult mvcResult = mvc.perform(put("/auctions/" + auction.getId()).contentType(MediaType.APPLICATION_JSON).content(toJson(auction))).andReturn();

        verifyRouteExists(mvcResult);
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status, "Expected OK status");

        Auction returnedAuction = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(returnedAuction, "Expected an auction to be returned but received null");
        assertEquals(auction.getId(), returnedAuction.getId(), "Expected id did not match returned id");
        assertEquals(auction.getTitle(), returnedAuction.getTitle(), "Expected title did not match returned title");
        assertEquals(auction.getDescription(), returnedAuction.getDescription(), "Expected description did not match returned description");
        assertEquals(auction.getUser(), returnedAuction.getUser(), "Expected user did not match returned user");
        assertEquals(auction.getCurrentBid(), returnedAuction.getCurrentBid(), 0.001, "Expected currentBid did not match returned currentBid");
    }

    @Test
    public void update_InvalidAuctionShouldNotBeUpdated() throws Exception {
        Auction auction = new Auction(1,
                "Bell Computer Monitor",
                "4K LCD monitor from Bell Computers, HDMI & DisplayPort",
                "Queenie34",
                100.39);
        auction.setTitle("");

        MvcResult mvcResult = mvc.perform(put("/auctions/" + auction.getId()).contentType(MediaType.APPLICATION_JSON).content(toJson(auction))).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(400, mvcResult.getResponse().getStatus(), "Expected Bad Request status 400 when PUT request contains invalid auction.");
    }

    @Test
    public void update_InvalidAuctionId_ShouldReturnNotFound() throws Exception {
        Auction auction = new Auction(1,
                "Bell Computer Monitor",
                "4K LCD monitor from Bell Computers, HDMI & DisplayPort",
                "Queenie34",
                100.39);

        MvcResult mvcResult = mvc.perform(put("/auctions/99").contentType(MediaType.APPLICATION_JSON).content(toJson(auction))).andReturn();
        // Can't check RequestMappings using verifyRouteExists() because this expects a 404
        assertEquals(404, mvcResult.getResponse().getStatus(), "Expected NOT FOUND status 404 when PUT request has invalid auction id.");
    }

    @Test
    public void delete_ShouldReturnNoContent() throws Exception {

        MvcResult mvcResult = mvc.perform(delete("/auctions/5")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(204, mvcResult.getResponse().getStatus(), "Expected NO CONTENT status");

        MvcResult afterResult = mvc.perform(get("/auctions/5")).andReturn();
        assertEquals(404, afterResult.getResponse().getStatus(), "Expected auction to be deleted but can still be retrieved");
    }

    private String toJson(Auction auction) throws JsonProcessingException {
        return mapper.writeValueAsString(auction);
    }

    private void verifyRouteExists(MvcResult mvcResult) {
        int status = mvcResult.getResponse().getStatus();
        if (status == 404 || status == 405) {
            fail(mvcResult.getRequest().getMethod()+ " route " + mvcResult.getRequest().getPathInfo() + " was not found. " +
                    "Response status: " + status + ". Verify RequestMappings are correct.");
        }
    }
}
