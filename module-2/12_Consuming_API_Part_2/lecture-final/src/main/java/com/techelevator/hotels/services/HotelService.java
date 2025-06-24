package com.techelevator.hotels.services;

import com.techelevator.hotels.model.Hotel;
import com.techelevator.hotels.model.Reservation;
import com.techelevator.util.BasicLogger;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;


public class HotelService {

    private static final String API_BASE_URL = "http://localhost:3000";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    /**
     * Create a new reservation in the hotel reservation system
     */
    public Reservation addReservation(Reservation newReservation) {
        // TODO: Implement method
        Reservation output = null;
        // change the method to post
        // add the payload/request body
        // call our server
        // check for errors
        try{
            output = restClient.post()
                    .uri("/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newReservation)
                    .retrieve()
                    .body(Reservation.class);


        }catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }

    /**
     * Updates an existing reservation by replacing the old one with a new
     * reservation
     */
    public boolean updateReservation(Reservation updatedReservation) {
        // TODO: Implement method
        // set up a return variable
        boolean success = false;
        try{
            restClient.put()
                    .uri("/reservations/" + updatedReservation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedReservation)
                    .retrieve()
                    .toBodilessEntity();
            success = true;
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    /**
     * Delete an existing reservation
     */
    public boolean deleteReservation(int id) {
        // TODO: Implement method
        try{
            restClient.delete()
                    .uri("/reservations/" + id)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return false;
    }

    /* DON'T MODIFY ANY METHODS BELOW */

    /**
     * List all hotels in the system
     */
    public Hotel[] listHotels() {

        Hotel[] hotels = null;
        try {
            hotels = restClient.get()
                    .uri("/hotels")
                    .retrieve()
                    .body(Hotel[].class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return hotels;
    }

    /**
     * List all reservations in the hotel reservation system
     */
    public Reservation[] listReservations() {

        Reservation[] reservations = null;
        try {
            reservations = restClient.get()
                    .uri("/reservations")
                    .retrieve()
                    .body(Reservation[].class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return reservations;
    }

    /**
     * List all reservations by hotel id.
     */
    public Reservation[] listReservationsByHotel(int hotelId) {

        Reservation[] reservations = null;
        try {
            reservations = restClient.get()
                    .uri("/hotels/" + hotelId + "/reservations")
                    .retrieve()
                    .body(Reservation[].class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return reservations;
    }

    /**
     * Find a single reservation by the reservationId
     */
    public Reservation getReservation(int reservationId) {

        Reservation reservation = null;
        try {
            reservation = restClient.get()
                    .uri("/reservations/" + reservationId)
                    .retrieve()
                    .body(Reservation.class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return reservation;
    }

}
