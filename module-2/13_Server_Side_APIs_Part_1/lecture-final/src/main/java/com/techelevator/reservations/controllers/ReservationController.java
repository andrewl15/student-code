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

    // We want to expose all reservations
    @RequestMapping(path ="/reservations",method= RequestMethod.GET)
    public List<Reservation> getAllReservations(){
        return reservationDao.getReservations();
    }

    // Let's return a specific reservation
    @RequestMapping(path="/reservations/{resID}", method=RequestMethod.GET)
    public Reservation getReservationByID(int resID){
        return reservationDao.getReservationById(resID);
    }

    @RequestMapping(path="/reservations",method=RequestMethod.POST)
    public Reservation createReservation(@RequestBody Reservation resIn){
        return reservationDao.createReservation(resIn);
    }

}
