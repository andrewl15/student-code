package com.techelevator.hotels.services;

import com.techelevator.hotels.model.Forecast;
import com.techelevator.hotels.model.Hotel;
import com.techelevator.hotels.model.Review;
import org.springframework.web.client.RestClient;

public class HotelService {

    private static final String API_BASE_URL = "http://localhost:3000";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    public Hotel[] listHotels() {
        return restClient.get().uri("/hotels").retrieve().body(Hotel[].class);
    }

    public Review[] listReviews() {
        return restClient.get().uri("/reviews").retrieve().body(Review[].class);
    }

    public Hotel getHotelById(int id) {
        return restClient.get().uri("/hotels/" + id).retrieve().body(Hotel.class);
    }

    public Review[] getReviewsByHotelId(int hotelId) {
        return restClient.get().uri("/hotels/" + hotelId + "/reviews").retrieve().body(Review[].class);
    }

    public Hotel[] getHotelsByStarRating(int stars) {
        return restClient.get().uri("hotels?stars=" + stars).retrieve().body(Hotel[].class);
    }

    public Forecast getWithCustomQuery(){
        return RestClient.create().get()
                .uri("https://teapi.netlify.app/api/weather/geo/41.5194,-81.6421/forecast")
                .retrieve().body(Forecast.class);
    }

}
