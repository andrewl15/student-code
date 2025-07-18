package com.techelevator.reservations.dao;

import com.techelevator.reservations.model.Hotel;

import java.util.List;

public interface HotelDao {

    List<Hotel> getHotels();

    Hotel getHotelById(int id);

	void deleteHotelById(int id);
}
