package com.techelevator.locations.services;

import com.techelevator.locations.model.Location;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

public class LocationService {

    private static final String API_BASE_URL = "http://localhost:8080/locations";
    private RestClient restClient = RestClient.create(API_BASE_URL);

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Location[] getAll() {

        Location[] locations = null;
        try {
            locations = restClient.get()
                    .retrieve()
                    .body(Location[].class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return locations;
    }

    public Location getOne(int id) {

        Location location = null;
        try {
            location = restClient.get()
                    .uri("/" + id)
                    .retrieve()
                    .body(Location.class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return location;
    }

    public Location add(Location newLocation) {

        Location returnedLocation = null;
        try {
            returnedLocation = restClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newLocation)
                    .retrieve()
                    .body(Location.class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedLocation;
    }

    public boolean update(Location updatedLocation) {

        boolean success = false;
        try {
            restClient.put()
                    .uri("/" + updatedLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedLocation)
                    .retrieve()
                    .toBodilessEntity();

            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public boolean delete(int id) {
        boolean success = false;
        try {
            restClient.delete()
                    .uri("/" + id)
                    .retrieve()
                    .toBodilessEntity();

            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

}
