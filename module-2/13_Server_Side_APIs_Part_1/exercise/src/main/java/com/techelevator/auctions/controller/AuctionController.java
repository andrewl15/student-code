package com.techelevator.auctions.controller;

import com.techelevator.auctions.dao.AuctionDao;
import com.techelevator.auctions.dao.MemoryAuctionDao;
import com.techelevator.auctions.model.Auction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionDao auctionDao;

    public AuctionController() {
        this.auctionDao = new MemoryAuctionDao();
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Auction> getAuctions(
            @RequestParam(required = false, defaultValue = "") String title_like,
            @RequestParam(required = false, defaultValue = "0") double currentBid_lte){
        if(!title_like.isEmpty() && currentBid_lte >0){
            return auctionDao.getAuctionsByTitleAndMaxBid(title_like, currentBid_lte);
        } else if (!title_like.isEmpty()) {
            return auctionDao.getAuctionsByTitle(title_like);
        } else if (currentBid_lte > 0) {
            return auctionDao.getAuctionsByMaxBid(currentBid_lte);
        }else {
            return auctionDao.getAuctions();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Auction getAuctionById(@PathVariable int id){
        return auctionDao.getAuctionById(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Auction createAuction(@RequestBody Auction auction){
        return auctionDao.createAuction(auction);
    }

}
