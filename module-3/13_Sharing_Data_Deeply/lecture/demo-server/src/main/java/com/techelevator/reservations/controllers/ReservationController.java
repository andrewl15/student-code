package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.ReservationDao;
import com.techelevator.reservations.exception.DaoException;
import com.techelevator.reservations.model.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
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
     * @param id the id of the reservation
     * @return a single reservation
     */
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.GET)
    public Reservation getReservation(@PathVariable int id) {
        try {
            Reservation reservation = reservationDao.getReservationById(id);
            if (reservation == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
            } else {
                return reservation;
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Create a new reservation for a given hotel
     *
     * @param reservation the reservation data
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/reservations", method = RequestMethod.POST)
    public Reservation addReservation(@Valid @RequestBody Reservation reservation) {
        try {
            return reservationDao.createReservation(reservation);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Update a reservation
     *
     * @param reservation the reservation data
     * @param id the id of the reservation
     * @return the updated Reservation
     */
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.PUT)
    public Reservation updateReservation(@Valid @RequestBody Reservation reservation, @PathVariable int id) {
        try {
            reservation.setId(id);
            Reservation updatedReservation = reservationDao.updateReservation(reservation);
            if (updatedReservation == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
            } else {
                return updatedReservation;
            }
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Delete a reservation by id
     *
     * @param id the id of the reservation
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.DELETE)
    public void deleteReservation(@PathVariable int id) {
        try {
            reservationDao.deleteReservationById(id);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
