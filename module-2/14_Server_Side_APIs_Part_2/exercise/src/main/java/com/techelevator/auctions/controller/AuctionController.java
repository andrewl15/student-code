package com.techelevator.auctions.controller;

import java.util.List;

import com.techelevator.auctions.exception.DaoException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.auctions.dao.AuctionDao;
import com.techelevator.auctions.model.Auction;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionDao auctionDao;

    public AuctionController(AuctionDao auctionDao) {
        this.auctionDao = auctionDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Auction> list(@RequestParam(defaultValue = "") String title_like, @RequestParam(defaultValue = "0") double currentBid_lte) {

        if (!title_like.equals("")) {
            return auctionDao.getAuctionsByTitle(title_like);
        }
        if (currentBid_lte > 0) {
            return auctionDao.getAuctionsByMaxBid(currentBid_lte);
        }

        return auctionDao.getAuctions();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Auction get(@PathVariable int id) {
        Auction auction = auctionDao.getAuctionById(id);
        if (auction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction Not Found");
        } else {
            return auctionDao.getAuctionById(id);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Auction create(@Valid @RequestBody Auction auction) {
        return auctionDao.createAuction(auction);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Auction updateAuction(@Valid @RequestBody Auction auction, @PathVariable int id){
        try{
            auction.setId(id);
            return auctionDao.updateAuction(auction);
        } catch(DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteAuction(@PathVariable int id){
        int numDeleted = auctionDao.deleteAuctionById(id);
        if(numDeleted == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
