package com.techelevator.dao;

import com.techelevator.model.Park;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class JdbcParkDaoTest extends BaseDaoTest {

    // Arrange
    // creating expected objects
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
        // see if it matches
        assertParksMatch(PARK_1,parkFromSut);

        Park parkId2 = sut.getParkById(2);
        assertParksMatch(PARK_2,parkId2);

        Park parkId3 = sut.getParkById(3);
        assertParksMatch(PARK_3,parkId3);
    }

    @Test
    public void getParkById_with_invalid_id_returns_null_park() {
        Park noPark = sut.getParkById(17);
        assertNull(noPark);
    }

    @Test
    public void getParksByState_with_valid_state_returns_correct_parks() {
        List<Park> parks = sut.getParksByState("AA");
        assertEquals(2, parks.size());
        assertParksMatch(PARK_1, parks.get(0));
        assertParksMatch(PARK_3, parks.get(1));

        parks = sut.getParksByState("BB");
        assertEquals(1, parks.size());
        assertParksMatch(PARK_2, parks.get(0));
    }

    @Test
    public void getParksByState_with_invalid_state_returns_empty_list() {
        List<Park> parks = sut.getParksByState("XX");
        assertEquals(0, parks.size());
    }

    @Test
    public void createPark_creates_park() {
        // We need a park to create
        Park parkToAdd = new Park(0,"Test Park",LocalDate.now(),150,true);
        Park returnedPark = sut.createPark(parkToAdd);

        // first, did I get back a park?
        assertNotNull(returnedPark);

        // second, did the park_id change and is it valid? (greater than zero)
        assertTrue(returnedPark.getParkId() > 0);

        // Did it get added to the database?
        // Get it out of the database
        Park parkInDB = sut.getParkById(returnedPark.getParkId());
        assertParksMatch(parkInDB,returnedPark);
    }

    @Test
    public void updatePark_updates_park() {
        // get a park from the database
        Park parkToUpdate = sut.getParkById(1);
        // change some values
        parkToUpdate.setParkName("New Park Name");
        parkToUpdate.setHasCamping(!parkToUpdate.getHasCamping());
        parkToUpdate.setArea(856);
        parkToUpdate.setDateEstablished(LocalDate.now());
        // call the method on sut (act)
        Park updatedPark = sut.updatePark(parkToUpdate);
        // make sure I get back a park
        assertNotNull(updatedPark);
        // get the update park from the database
        Park updatedFromDB = sut.getParkById(parkToUpdate.getParkId());
        // make sure it matches what I sent in
        assertParksMatch(parkToUpdate,updatedFromDB);
        assertParksMatch(updatedPark,updatedFromDB);
    }

    @Test
    public void deleteParkById_deletes_park() {
        int rowsAffected = sut.deleteParkById(1);
        assertEquals(1,rowsAffected);

        // validate the park is actually gone from db
        Park noPark = sut.getParkById(1);
        assertNull(noPark);
    }

    @Test
    public void linkParkState_adds_park_to_list_of_parks_in_state() {
        int preLinkParkCount = sut.getParksByState("CC").size();

        sut.linkParkState(1, "CC");
        List<Park> parks = sut.getParksByState("CC");
        int postLinkParkCount = parks.size();

        assertEquals(preLinkParkCount + 1, postLinkParkCount);
        assertParksMatch(PARK_1, parks.get(0));
    }

    @Test
    public void unlinkParkState_removes_park_from_list_of_parks_in_state() {
        int preUnlinkParkCount = sut.getParksByState("AA").size();

        sut.unlinkParkState(1, "AA");
        List<Park> parks = sut.getParksByState("AA");
        int postUnlinkParkCount = parks.size();

        assertEquals(preUnlinkParkCount - 1, postUnlinkParkCount);
        assertParksMatch(PARK_3, parks.get(0));
    }

    private void assertParksMatch(Park expected, Park actual) {
        assertEquals(expected.getParkId(), actual.getParkId());
        assertEquals(expected.getParkName(), actual.getParkName());
        assertEquals(expected.getDateEstablished(), actual.getDateEstablished());
        assertEquals(expected.getArea(), actual.getArea(), 0.1);
        assertEquals(expected.getHasCamping(), actual.getHasCamping());
    }

}
