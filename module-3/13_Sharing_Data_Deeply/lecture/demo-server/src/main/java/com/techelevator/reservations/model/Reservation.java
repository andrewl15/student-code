package com.techelevator.reservations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Reservation {

    private int id;
    @Min( value = 1, message = "The field 'hotelId' is required.")
    private int hotelId;
    @JsonProperty(required = false)
    private String hotelName;
    @NotBlank( message = "The field 'fullName' is required.")
    private String fullName;
    @NotBlank( message = "The field 'checkInDate' is required.")
    private String checkInDate;
    @NotBlank( message = "The field 'checkOutDate' is required.")
    private String checkOutDate;
    @Min( value = 1, message = "The minimum number of guests is 1")
    @Max( value = 5, message = "The maximum number of guests is 5")
    private int guests;

    public Reservation(int id, int hotelId, String fullName, String checkInDate, String checkOutDate, int guests) {
        this.id = id;
        this.hotelId = hotelId;
        this.fullName = fullName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getcheckInDate() {
        return checkInDate;
    }

    public void setcheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getcheckOutDate() {
        return checkOutDate;
    }

    public void setcheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return "\n--------------------------------------------" +
                "\n Reservation Details" +
                "\n--------------------------------------------" +
                "\n Id: " + id +
                "\n Hotel Id: " + hotelId +
                "\n Full Name: " + fullName +
                "\n Checkin Date: " + checkInDate +
                "\n Checkout Date: " + checkOutDate +
                "\n Guests: " + guests;
    }
}
