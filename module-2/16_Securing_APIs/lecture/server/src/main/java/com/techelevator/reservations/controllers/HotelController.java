package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.HotelDao;
import com.techelevator.reservations.dao.MemoryHotelDao;
import com.techelevator.reservations.dao.MemoryReservationDao;
import com.techelevator.reservations.dao.ReservationDao;
import com.techelevator.reservations.exception.DaoException;
import com.techelevator.reservations.model.Hotel;
import com.techelevator.reservations.model.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@RestController
public class HotelController {

    private HotelDao hotelDao;
    private ReservationDao reservationDao;

    public HotelController() {
        this.hotelDao = new MemoryHotelDao();
        this.reservationDao = new MemoryReservationDao(hotelDao);
    }

     /**
     * /hotels
     * /hotels?state=ohio
     * /hotels?state=ohio&city=cleveland
     *
     * @param state the state to filter by
     * @param city  the city to filter by
     * @return a list of hotels that match the city & state
     */
     @PreAuthorize("permitAll")
     @RequestMapping(path = "/hotels", method = RequestMethod.GET)
     public List<Hotel> list(@RequestParam(required = false) String state, @RequestParam(required = false) String city) {
         return hotelDao.getHotelsByStateAndCity(state, city);
     }


    /**
     * Return hotel information
     *
     * @param id the id of the hotel
     * @return all info for a given hotel
     */
    @RequestMapping(path = "/hotels/{id}", method = RequestMethod.GET)
    public Hotel get(@PathVariable int id) {
        Hotel hotel = hotelDao.getHotelById(id);
        if (hotel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found.");
        } else {
            return hotel;
        }
    }

    /**
     * Returns all reservations in the system
     *
     * @return all reservations
     */
    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    public List<Reservation> listReservations() {
        return reservationDao.getReservations();
    }

    /**
     * Get a reservation by its id
     *
     * @param id
     * @return a single reservation
     */
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.GET)
    public Reservation getReservation(@PathVariable int id) {
        Reservation reservation = reservationDao.getReservationById(id);
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
        } else {
            return reservation;
        }
    }

    /**
     * List of reservations by hotel
     *
     * @param hotelId
     * @return all reservations for a given hotel
     */
    @RequestMapping(path = "/hotels/{id}/reservations", method = RequestMethod.GET)
    public List<Reservation> listReservationsByHotel(@PathVariable("id") int hotelId) {
        List<Reservation> reservations = reservationDao.getReservationsByHotel(hotelId);
        if (reservations == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found.");
        } else {
            return reservations;
        }
    }

    /**
     * Create a new reservation for a given hotel
     *
     * @param reservation
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/reservations", method = RequestMethod.POST)
    public Reservation addReservation(@Valid @RequestBody Reservation reservation) {
        return reservationDao.createReservation(reservation);
    }

    /**
     * Updates a reservation
     *
     * @param reservation
     * @param id
     * @return the updated Reservation
     */
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.PUT)
    public Reservation update(@Valid @RequestBody Reservation reservation, @PathVariable int id) {
        // The id on the path takes precedence over the id in the request body, if any
        reservation.setId(id);
        try {
            Reservation updatedReservation = reservationDao.updateReservation(reservation);
            return updatedReservation;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
        }
    }

    /**
     * Delete a reservation by id
     *
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        reservationDao.deleteReservationById(id);
    }

    /**
     * Used to log operations
     *
     * @param operation
     * @param reservation
     * @param username
     */
    private void auditLog(String operation, int reservation, String username) {
        System.out.println(
                "User: " + username + " performed the operation: " + operation + " on reservation: " + reservation);
    }

}
