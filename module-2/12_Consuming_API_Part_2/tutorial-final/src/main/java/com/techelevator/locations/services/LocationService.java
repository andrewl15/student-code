package com.techelevator.locations.services;

import com.techelevator.locations.model.Location;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class LocationService {

    private static final String API_BASE_URL = "http://localhost:3000/locations";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    public Location[] getAll() {

        Location[] locations = null;
        try {
            locations = restClient.get()
                    .retrieve()
                    .body(Location[].class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
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

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return location;
    }

    public Location add(Location newLocation) {

        Location returnedLocation = null;
        try {
            returnedLocation = restClient.post()
                    .contentType(APPLICATION_JSON)
                    .body(newLocation)
                    .retrieve()
                    .body(Location.class);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusCode().value() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedLocation;
    }

    public boolean update(Location updatedLocation) {

        boolean success = false;
        try {
            restClient.put()
                    .uri("/" + updatedLocation.getId())
                    .contentType(APPLICATION_JSON)
                    .body(updatedLocation)
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

    public boolean delete(int id) {

        boolean success = false;
        try {
            restClient.delete()
                    .uri("/" + id)
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

}
