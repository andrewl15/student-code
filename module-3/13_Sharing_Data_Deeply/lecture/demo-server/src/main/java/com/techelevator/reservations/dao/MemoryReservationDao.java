package com.techelevator.reservations.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.reservations.exception.DaoException;
import com.techelevator.reservations.model.Hotel;
import com.techelevator.reservations.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class MemoryReservationDao implements ReservationDao {

    private static final List<Reservation> reservations = new ArrayList<>();
    private final HotelDao hotelDao;

    public MemoryReservationDao(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
        if (reservations.isEmpty()) {
            loadReservationData();
        }
    }

    public List<Reservation> getReservations() {
        List<Reservation> returnVals = new ArrayList<>();
        for (Reservation r : reservations) {
            Hotel hotel = hotelDao.getHotelById(r.getHotelId());
            r.setHotelName(hotel == null ? "" : hotel.getName());
            returnVals.add(r);
        }
        return returnVals;
    }

    @Override
    public List<Reservation> getReservationsByHotel(int hotelId) {
        List<Hotel> hotels = hotelDao.getHotels();
        boolean hotelExists = false;
        for (Hotel hotel : hotels) {
            if (hotel.getId() == hotelId) {
                hotelExists = true;
                break;
            }
        }

        if (!hotelExists) {
            return null;
        }

        List<Reservation> hotelReservations = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getHotelId() == hotelId) {
                hotelReservations.add(r);
            }
        }

        return hotelReservations;
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        for (Reservation res : reservations) {
            if (res.getId() == reservationId) {
                return res;
            }
        }
        return null;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        reservation.setId(getMaxIdPlusOne());
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        Reservation result = reservation;
        boolean finished = false;

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.set(i, result);
                finished = true;
                break;
            }
        }
        if (!finished) {
            throw new DaoException("Reservation to update not found");
        }

        return result;
    }

    @Override
    public int deleteReservationById(int id) {
        Reservation target = null;
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                target = reservation;
                break;
            }
        }

        // when using foreach we must remove the item after iterating to avoid a ConcurrentModificationException
        if (target != null) {
            reservations.remove(target);
            return 1; // number of rows affected
        } else {
            return 0; // number of rows affected
        }
    }

    private void loadReservationData() {
        LocalDate now = LocalDate.now();
        List<Hotel> hotels = hotelDao.getHotels();

        reservations.add(new Reservation(getMaxIdPlusOne(),
                hotels.get(0).getId(),
                "Gregory Trembey",
                now.toString(),
                now.plusDays(3).toString(),
                2));
        reservations.add(new Reservation(getMaxIdPlusOne(),
                hotels.get(0).getId(),
                "Nat Belhomme",
                now.plusDays(1).toString(),
                now.plusDays(5).toString(),
                4));
        reservations.add(new Reservation(getMaxIdPlusOne(),
                hotels.get(1).getId(),
                "Alex Ricco",
                now.plusDays(7).toString(),
                now.plusDays(10).toString(),
                2));
        reservations.add(new Reservation(getMaxIdPlusOne(),
                hotels.get(1).getId(),
                "Cammy Vanin",
                now.plusDays(-1).toString(),
                now.plusDays(2).toString(),
                1));
        reservations.add(new Reservation(getMaxIdPlusOne(),
                hotels.get(2).getId(),
                "Drew Bennett",
                now.plusDays(1).toString(),
                now.plusDays(5).toString(),
                4));
    }

    /**
     * finds the max id in the list of reservations and returns it
     *
     * @return int the max id
     */
    private int getMaxId() {
        int maxId = 0;
        for (Reservation r : reservations) {
            if (r.getId() > maxId) {
                maxId = r.getId();
            }
        }
        return maxId;
    }

    /**
     * Adds 1 to the max id and returns it
     */
    private int getMaxIdPlusOne() {
        return getMaxId() + 1;
    }

}
