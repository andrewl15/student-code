package com.techelevator.auctions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.auctions.dao.AuctionDao;
import com.techelevator.auctions.dao.MemoryAuctionDao;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuctionController.class)
@AutoConfigureMockMvc(print=MockMvcPrint.NONE)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AuctionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private AuctionDao dao;

    public AuctionControllerTest() {
        dao = new MemoryAuctionDao();
    }

    @Test
    public void listShouldReturnCorrectCount() throws Exception {
        int count = dao.getAuctions().size();
        MvcResult mvcResult = mvc.perform(get("/auctions")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");
        List<Auction> searchResults = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(count, searchResults.size(), "Expected result size to be " + count);
    }

    @Test
    public void getShouldReturnSingleAuction() throws Exception {
        List<Auction> auctions = dao.getAuctions();
        Auction first = auctions.get(0);

        MvcResult mvcResult = mvc.perform(get("/auctions/" + first.getId())).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");

        Auction returnedAuction = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Auction>() {});
        assertNotNull(returnedAuction, "Expected an auction to be returned but was null");
        assertEquals(first.getId(), returnedAuction.getId(), "Expected id did not match returned id");
        assertEquals(first.getTitle(), returnedAuction.getTitle(), "Expected title did not match returned title");
        assertEquals(first.getDescription(), returnedAuction.getDescription(), "Expected description did not match returned description");
        assertEquals(first.getUser(), returnedAuction.getUser(), "Expected user did not match returned user");
        assertEquals(first.currentBidToString(), returnedAuction.currentBidToString(), "Expected currentBid did not match returned currentBid");
    }

    @Test
    public void getInvalidIdShouldReturnNothing() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/auctions/99")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");
        assertEquals("", mvcResult.getResponse().getContentAsString(), "Expected empty response for invalid auction id.");
    }

    @Test
    public void createShouldThrowExceptionWhenRequestBodyDoesntExist() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/auctions").contentType(MediaType.APPLICATION_JSON)).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(400, mvcResult.getResponse().getStatus(), "Expected Bad Request status 400 when POST request is missing the body.");
    }

    @Test
    public void createShouldAddNewAuction() throws Exception {
        Auction auction = new Auction(
                "Standing Desk",
                "Stand up desk to help you stretch your legs during the day.",
                "Johnnie34",
                350.00);

        MvcResult mvcResult = mvc.perform(post("/auctions").contentType(MediaType.APPLICATION_JSON).content(toJson(auction))).andReturn();

        verifyRouteExists(mvcResult);
        int status = mvcResult.getResponse().getStatus();
        assertTrue(status == 200 || status == 201, "Expected successful status");

        Auction returnedAuction = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Auction>() {});
        assertNotNull(returnedAuction, "Expected an auction to be returned but received null");
        assertEquals(8, returnedAuction.getId(), "Expected id did not match returned id");
        assertEquals(auction.getTitle(), returnedAuction.getTitle(), "Expected title did not match returned title");
        assertEquals(auction.getDescription(), returnedAuction.getDescription(), "Expected description did not match returned description");
        assertEquals(auction.getUser(), returnedAuction.getUser(), "Expected user did not match returned user");
        assertEquals(auction.getCurrentBid(), returnedAuction.getCurrentBid(), 0.001, "Expected currentBid did not match returned currentBid");
    }

    @Test
    public void searchByTitleShouldReturnList() throws Exception {
        List<Auction> allAuctions = dao.getAuctions();
        List<Auction> expected = new ArrayList<>();
        expected.add(allAuctions.get(1));
        expected.add(allAuctions.get(6));

        MvcResult mvcResult = mvc.perform(get("/auctions?title_like=watch")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");

        List<Auction> searchResults = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(expected.size(), searchResults.size(), "Expected count of auctions did not match returned count");
        assertEquals(expected.get(0).getTitle(), searchResults.get(0).getTitle(), "Expected title of first auction did not match returned value");
        assertEquals(expected.get(1).getTitle(), searchResults.get(1).getTitle(), "Expected title of second auction did not match returned value");
    }

    @Test
    public void searchByTitleExpectNone() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/auctions?title_like=abcsdfsdf")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");

        List<Auction> searchResults = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(0, searchResults.size(), "Expected no auctions but received more than zero");
    }

    @Test
    public void searchByPriceShouldReturnList() throws Exception {
        List<Auction> allAuctions = dao.getAuctions();
        List<Auction> expected = new ArrayList<>();
        expected.add(allAuctions.get(3));

        MvcResult mvcResult = mvc.perform(get("/auctions?currentBid_lte=70")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");

        List<Auction> searchResults = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(expected.size(), searchResults.size(), "Expected count of auctions did not match returned count");
        assertEquals(expected.get(0).getTitle(), searchResults.get(0).getTitle(), "Expected title did not match returned title");
        assertEquals(expected.get(0).getDescription(), searchResults.get(0).getDescription(), "Expected description did not match returned description");
        assertEquals(expected.get(0).getCurrentBid(), searchResults.get(0).getCurrentBid(), 0.001, "Expected currentBid did not match returned currentBid");
    }

    @Test
    public void searchByPriceExpectNone() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/auctions?currentBid_lte=1")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");

        List<Auction> searchResults = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(0, searchResults.size(), "Expected no auctions but received more than zero");
    }

    @Test
    public void searchByTitleAndPriceExpectOne() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/auctions?title_like=Watch&currentBid_lte=200")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");

        List<Auction> searchResults = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(1, searchResults.size(), "Expected one auction but received a different amount");
    }

    @Test
    public void searchByTitleAndPriceExpectNone() throws Exception {
        MvcResult mvcResult = mvc.perform(
                get("/auctions?title_like=Watch&currentBid_lte=50")).andReturn();

        verifyRouteExists(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus(), "Expected successful status");

        List<Auction> searchResults = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(0, searchResults.size(), "Expected no auctions but received more than zero");
    }

    private String toJson(Auction auction) throws JsonProcessingException {
        return mapper.writeValueAsString(auction);
    }

    private void verifyRouteExists(MvcResult mvcResult) {
        int status = mvcResult.getResponse().getStatus();
        if (status == 404 || status == 405) {
            fail(mvcResult.getRequest().getMethod() + " route " + mvcResult.getRequest().getPathInfo() + " was not found. " +
                    "Response status: " + status + ". Verify RequestMappings are correct.");
        }
    }
}
