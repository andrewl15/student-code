package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.HotelDao;
import com.techelevator.reservations.exception.DaoException;
import com.techelevator.reservations.model.Hotel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
public class HotelController {

    private final HotelDao hotelDao;

	public HotelController(HotelDao hotelDao) {
		this.hotelDao = hotelDao;
	}

    @RequestMapping(path = "/hotels", method = RequestMethod.GET)
    public List<Hotel> list() {
        return hotelDao.getHotels();
    }

	@RequestMapping(path = "/hotels/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("isAuthenticated()")
	public void deleteHotel(@PathVariable int id) {
		try {
			hotelDao.deleteHotelById(id);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}


}
