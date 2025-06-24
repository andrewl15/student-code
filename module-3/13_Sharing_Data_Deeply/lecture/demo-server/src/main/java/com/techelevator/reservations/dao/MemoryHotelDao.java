package com.techelevator.reservations.dao;

import com.techelevator.reservations.model.Address;
import com.techelevator.reservations.model.Hotel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryHotelDao implements HotelDao {

    private static final List<Hotel> hotels = new ArrayList<>();

    public MemoryHotelDao() {
        if (hotels.isEmpty()) {
            loadHotelData();
        }
    }

	@Override
	public List<Hotel> getHotels() {
		return hotels;
	}

    @Override
    public Hotel getHotelById(int id) {
        for (Hotel hotel : hotels) {
            if (hotel.getId() == id) {
                return hotel;
            }
        }
        return null;
    }

	@Override
	public void deleteHotelById(int id) {
		for (Hotel hotel : hotels) {
			if (hotel.getId() == id) {
				hotels.remove(hotel);
				return;
			}
		}
	}

    private void loadHotelData() {
        hotels.add(new Hotel(1,
                "Aloft Cleveland",
                new Address("1111 W 10th St", "", "Cleveland", "Ohio", "44115"),
                4,
                48, 274));
        hotels.add(new Hotel(2,
                "Hilton Cleveland Downtown",
                new Address("100 Lakeside Ave", "", "Cleveland", "Ohio", "44114"),
                4,
                12, 287));
        hotels.add(new Hotel(3,
                "Metropolitan at the 9",
                new Address("2017 E 9th St", "", "Cleveland", "Ohio", "48226"),
                5,
                22, 319));
        hotels.add(new Hotel(4,
                "The Westin Pittsburgh",
                new Address("1000 Penn Ave", "", "Pittsburgh", "Pennsylvania", "15222"),
                4,
                60, 131));
        hotels.add(new Hotel(5,
                "Hilton Columbus Downtown",
                new Address("401 N High St", "", "Columbus", "Ohio", "43215"),
                4,
                34, 190));
        hotels.add(new Hotel(6,
                "The Summit A Dolce Hotel",
                new Address("5345 Medpace Way", "", "Cincinnati", "Ohio", "43215"),
                4,
                43, 218));
        hotels.add(new Hotel(7,
                "Greektown Detroit",
                new Address("1200 St Antoine St", "", "Detroit", "Michigan", "48226"),
                4,
                75, 185));
    }

    static private boolean emptyString(String s) {
        // Check if a string is null or empty
        return (s == null || s.trim().isEmpty());
    }
}
