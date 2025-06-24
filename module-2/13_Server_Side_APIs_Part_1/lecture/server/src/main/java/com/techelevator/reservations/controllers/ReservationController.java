package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.MemoryHotelDao;
import com.techelevator.reservations.dao.MemoryReservationDao;
import com.techelevator.reservations.dao.ReservationDao;
import com.techelevator.reservations.model.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private ReservationDao reservationDao;

    public ReservationController(){
        reservationDao = new MemoryReservationDao(new MemoryHotelDao());
    }

    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    public List<Reservation> getAllReservations(){
        return reservationDao.getReservations();
    }

    @RequestMapping(path="/reservations/{resID}", method = RequestMethod.GET)
    public Reservation getReservationByID(@PathVariable int resID){
        return reservationDao.getReservationById(resID);
    }

    @RequestMapping(path="/hotels/{id}/reservations", method = RequestMethod.GET)
    public List<Reservation> getReservationByHotelID(@PathVariable int id){
        return reservationDao.getReservationsByHotel(id);
    }

    @RequestMapping(path="/reservations", method = RequestMethod.POST)
    public Reservation createReservation(@RequestBody Reservation resIn){
        return reservationDao.createReservation(resIn);
    }
}
