package com.techelevator.hotels.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Forecast {

    private String updated;
    @JsonProperty("forecast")
    private WeatherForecast weatherForecast;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    @Override
    public String toString(){
        return weatherForecast.toString();
    }
}
