package com.techelevator.reservations.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Reservation {


    private int id;
    // NotNull won't work on int
    //@NotNull
    @Min(value=1)
    private int hotelId;
    @NotBlank
    @NotNull
    private String fullName;
    @NotBlank(message = "Please provide a valid date")
    private String checkinDate;
    @NotBlank(message = "Please provide a valid date")
    private String checkoutDate;
    @Min(value=1)
    @Max(value=6, message = "No more than 6 guests per room.")
    private int guests;

    public Reservation(int id, int hotelId, String fullName, String checkinDate, String checkoutDate, int guests) {
        this.id = id;
        this.hotelId = hotelId;
        this.fullName = fullName;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return "\n--------------------------------------------" + "\n Reservation Details"
                + "\n--------------------------------------------" + "\n Id: " + id + "\n Hotel Id: " + hotelId
                + "\n Full Name: " + fullName + "\n Checkin Date: " + checkinDate + "\n Checkout Date: " + checkoutDate
                + "\n Guests: " + guests;
    }
}
