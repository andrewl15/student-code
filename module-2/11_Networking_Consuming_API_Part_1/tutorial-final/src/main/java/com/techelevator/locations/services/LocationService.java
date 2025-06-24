package com.techelevator.locations.services;

import com.techelevator.locations.model.Location;
import org.springframework.web.client.RestClient;

public class LocationService {

    private static final String API_BASE_URL = "http://localhost:3000/locations";

    private final RestClient restClient = RestClient.create(API_BASE_URL);

    public Location[] getAll() {
        return restClient.get()
                .retrieve()
                .body(Location[].class);
    }

    public Location getOne(int id) {
        return restClient.get()
                .uri("/" + id)
                .retrieve()
                .body(Location.class);
    }

}
