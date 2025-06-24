package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.channels.ReadPendingException;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    @GetMapping(path="")
    public List<CatCard> list(){
        return catCardDao.getCatCards();
    }

    @GetMapping(path="/random")
    public CatCard getRandomCatCard(){
        CatCard catCard = new CatCard();

        catCard.setCatFact(catFactService.getFact().getText());
        catCard.setImgUrl(catPicService.getPic().getFile());

        return catCard;
    }


    @GetMapping(path = "/{id}")
    public CatCard getCatCardById(@PathVariable int id){
        CatCard output = null;
        try {
            output = catCardDao.getCatCardById(id);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(output == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return output;
    }

    //Random


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public CatCard create(@Valid @RequestBody CatCard catCard){
        return catCardDao.createCatCard(catCard);
    }

    @PutMapping(path = "/{id}")
    public CatCard updateCatCard(@Valid @RequestBody CatCard catCard, @PathVariable int id){
        CatCard existing = catCardDao.getCatCardById(id);
        if(existing == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try{
            return catCardDao.updateCatCard(catCard);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path="/{id}")
    public void deleteCatCard(@PathVariable int id){
        int rowsAffected = 0;
        try{
            rowsAffected = catCardDao.deleteCatCardById(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(rowsAffected == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
