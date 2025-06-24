package com.techelevator.hotels.services;

import com.techelevator.hotels.model.Hotel;
import com.techelevator.hotels.model.Reservation;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

public class HotelService {

    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Create a new reservation in the hotel reservation system
     */
    public Reservation addReservation(Reservation newReservation) {
        Reservation returnedReservation = null;

        try{
            returnedReservation = restClient.post()
                    .uri("/reservations")
                    .header("Authorization","Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newReservation)
                    .retrieve()
                    .body(Reservation.class);
        } catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }

        return returnedReservation;
    }

    /**
     * Updates an existing reservation by replacing the old one with a new reservation
     */
    public boolean updateReservation(Reservation updatedReservation) {
        boolean success = false;
        try {
            restClient.put()
                    .uri("/reservations/" + updatedReservation.getId())
                    .header("Authorization", "Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedReservation)
                    .retrieve()
                    .toBodilessEntity();

            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    /**
     * Delete an existing reservation
     */
    public boolean deleteReservation(int id) {

        boolean success = false;
        try {
            restClient.delete()
                    .uri("/reservations/" + id)
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .toBodilessEntity();

            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    /**
     * List all hotels in the system
     */
    public Hotel[] listHotels() {

        Hotel[] hotels = null;
        try {
            hotels = restClient.get()
                    .uri("/hotels")
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .body(Hotel[].class);

        } catch (RestClientResponseException | ResourceAccessException e) {
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
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .body(Reservation[].class);

        } catch (RestClientResponseException | ResourceAccessException e) {
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
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .body(Reservation[].class);

        } catch (RestClientResponseException | ResourceAccessException e) {
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
                    .header("Authorization", "Bearer " + authToken)
                    .retrieve()
                    .body(Reservation.class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return reservation;
    }

}
