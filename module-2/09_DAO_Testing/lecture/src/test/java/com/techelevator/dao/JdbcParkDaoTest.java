package com.techelevator.dao;

import com.techelevator.model.Park;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class JdbcParkDaoTest extends BaseDaoTest {

    private static final Park PARK_1 =
            new Park(1, "Park 1", LocalDate.parse("1800-01-02"), 100, true);
    private static final Park PARK_2 =
            new Park(2, "Park 2", LocalDate.parse("1900-12-31"), 200, false);
    private static final Park PARK_3 =
            new Park(3, "Park 3", LocalDate.parse("2000-06-15"), 300, false);

    private JdbcParkDao sut;

    @BeforeEach
    public void setup() {
        sut = new JdbcParkDao(dataSource);
    }

    @Test
    public void getParkById_with_valid_id_returns_correct_park() {
        Park parkFromSut = sut.getParkById(1);
        assertParksMatch(PARK_1, parkFromSut);

        parkFromSut = sut.getParkById(2);
        assertParksMatch(PARK_2, parkFromSut);

        parkFromSut = sut.getParkById(3);
        assertParksMatch(PARK_3, parkFromSut);
    }

    @Test
    public void getParkById_with_invalid_id_returns_null_park() {
        Park noPark = sut.getParkById(17);
        assertNull(noPark);
    }

    @Test
    public void getParksByState_with_valid_state_returns_correct_parks() {
        fail();
    }

    @Test
    public void getParksByState_with_invalid_state_returns_empty_list() {
        fail();
    }

    @Test
    public void createPark_creates_park() {
        Park parkToAdd = new Park(0,"Test Park",LocalDate.now(), 150, true);
        Park returnedPark = sut.createPark(parkToAdd);

        assertNotNull(returnedPark);

        assertTrue(returnedPark.getParkId() > 0);

        Park parkInDB = sut.getParkById(returnedPark.getParkId());
        assertParksMatch(parkInDB, returnedPark);
    }

    @Test
    public void updatePark_updates_park() {
        Park parkToUpdate = sut.getParkById(1);
        parkToUpdate.setParkName("New Park Name");
        parkToUpdate.setHasCamping(!parkToUpdate.getHasCamping());
        parkToUpdate.setArea(parkToUpdate.getArea() + 1000);
        parkToUpdate.setDateEstablished(LocalDate.now());

        Park updatedPark = sut.updatePark(parkToUpdate);
        assertNotNull(updatedPark);

        Park updatedFromDB = sut.getParkById(parkToUpdate.getParkId());
        assertParksMatch(parkToUpdate, updatedFromDB);
    }

    @Test
    public void deleteParkById_deletes_park() {
        int rowsAffected = sut.deleteParkById(1);
        assertEquals(1, rowsAffected);

        Park noPark = sut.getParkById(1);
        assertNull(noPark);
    }

    @Test
    public void linkParkState_adds_park_to_list_of_parks_in_state() {
        fail();
    }

    @Test
    public void unlinkParkState_removes_park_from_list_of_parks_in_state() {
        fail();
    }

    private void assertParksMatch(Park expected, Park actual) {
        assertEquals(expected.getParkId(), actual.getParkId());
        assertEquals(expected.getParkName(), actual.getParkName());
        assertEquals(expected.getDateEstablished(), actual.getDateEstablished());
        assertEquals(expected.getArea(), actual.getArea(), 0.1);
        assertEquals(expected.getHasCamping(), actual.getHasCamping());
    }

}
