package com.techelevator.locations.services;

import com.techelevator.locations.model.Location;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

public class LocationService {

    private static final String API_BASE_URL = "http://localhost:3000/locations";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    public Location add(Location newLocation) {
        //Step Three: Add a location with POST
        return null;
    }

    public boolean update(Location updatedLocation) {
        //Step Four: Modify a location with PUT
        return false;
    }

    public boolean delete(int id) {
        //Step Five: Delete a location with DELETE
        return false;
    }

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

}
