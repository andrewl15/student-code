package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<CatCard> getAllCards(){
        try{
            return catCardDao.getCatCards();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.GET)
    public CatCard getCardById(@PathVariable int id){
        CatCard output = null;
        try{
            output = catCardDao.getCatCardById(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(output == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return output;
    }
    @RequestMapping(path="/random",method= RequestMethod.GET)
    public CatCard getRandomCarCard(){
        // holding variable for the return
        CatCard output = new CatCard();
        // get a cat fact from the service
        CatFact fact = catFactService.getFact();
        // add that to output
        output.setCatFact(fact.getText());
        // get a cat pic from the service
        CatPic pic = catPicService.getPic();
        // add that to output
        output.setImgUrl(pic.getFile());
        // return output
        return output;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="",method=RequestMethod.POST)
    public CatCard addNewCard(@Valid @RequestBody CatCard incoming){
        try{
            return catCardDao.createCatCard(incoming);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public CatCard updateCatCard(@PathVariable int id, @Valid @RequestBody CatCard input){
        CatCard existing = catCardDao.getCatCardById(id);
        if(existing == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try{
            return catCardDao.updateCatCard(input);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path="/{id}")
    public void deleteExistingCard(@PathVariable int id){
        int rowsAffected = 0;
        try{
            rowsAffected = catCardDao.deleteCatCardById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(rowsAffected == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
