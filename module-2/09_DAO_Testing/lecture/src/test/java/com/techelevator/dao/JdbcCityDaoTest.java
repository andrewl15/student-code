package com.techelevator.dao;

import com.techelevator.model.City;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class JdbcCityDaoTest extends BaseDaoTest {

    private static final City CITY_1 = new City(1, "City 1", "AA", 11, 111);
    private static final City CITY_2 = new City(2, "City 2", "BB", 22, 222);
    private static final City CITY_4 = new City(4, "City 4", "AA", 44, 444);

    private City testCity;

    private JdbcCityDao sut;

    @BeforeEach
    public void setup() {
        sut = new JdbcCityDao(dataSource);
        testCity = new City(0, "Test City", "CC", 99, 999);
    }

    @Test
    public void getCityById_with_valid_id_returns_correct_city() {
        City city = sut.getCityById(1);
        assertCitiesMatch(CITY_1, city);

        city = sut.getCityById(2);
        assertCitiesMatch(CITY_2, city);
    }

    @Test
    public void getCityById_with_invalid_id_returns_null_city() {
        City city = sut.getCityById(99);
        assertNull(city);
    }

    @Test
    public void getCitiesByState_with_valid_state_returns_correct_cities() {
        List<City> cities = sut.getCitiesByState("AA");
        assertEquals(2, cities.size());
        assertCitiesMatch(CITY_1, cities.get(0));
        assertCitiesMatch(CITY_4, cities.get(1));

        cities = sut.getCitiesByState("BB");
        assertEquals(1, cities.size());
        assertCitiesMatch(CITY_2, cities.get(0));
    }

    @Test
    public void getCitiesByState_with_invalid_state_returns_empty_list() {
        List<City> cities = sut.getCitiesByState("XX");
        assertEquals(0, cities.size());
    }

    @Test
    public void createCity_creates_city() {
        City createdCity = sut.createCity(testCity);
        assertNotNull(createdCity);

        int newId = createdCity.getCityId();
        assertTrue(newId > 0);

        City retrievedCity = sut.getCityById(newId);
        assertCitiesMatch(createdCity, retrievedCity);
    }

    @Test
    public void updateCity_updates_city() {
        City cityToUpdate = sut.getCityById(1);

        cityToUpdate.setCityName("Updated");
        cityToUpdate.setStateAbbreviation("CC");
        cityToUpdate.setPopulation(99);
        cityToUpdate.setArea(999);

        City updatedCity = sut.updateCity(cityToUpdate);
        assertNotNull(updatedCity);

        City retrievedCity = sut.getCityById(1);
        assertCitiesMatch(updatedCity, retrievedCity);
    }

    @Test
    public void deleteCityById_deletes_city() {
        int rowsAffected = sut.deleteCityById(4);
        assertEquals(1, rowsAffected);

        City retrievedCity = sut.getCityById(4);
        assertNull(retrievedCity);
    }

    private void assertCitiesMatch(City expected, City actual) {
        assertEquals(expected.getCityId(), actual.getCityId());
        assertEquals(expected.getCityName(), actual.getCityName());
        assertEquals(expected.getStateAbbreviation(), actual.getStateAbbreviation());
        assertEquals(expected.getPopulation(), actual.getPopulation());
        assertEquals(expected.getArea(), actual.getArea(), 0.1);
    }
}
