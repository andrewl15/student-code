package com.techelevator.hotels.model;

public class WeatherForecast {

    private String name;
    private Measure temperature;
    private String temperatureUnit;
    private String shortForecast;
    private String detailedForecast;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measure getTemperature() {
        return temperature;
    }

    public void setTemperature(Measure temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    public String getDetailedForecast() {
        return detailedForecast;
    }

    public void setDetailedForecast(String detailedForecast) {
        this.detailedForecast = detailedForecast;
    }

    @Override
    public String toString(){
        return name + ": " + temperature + ". " + shortForecast;
    }

}
