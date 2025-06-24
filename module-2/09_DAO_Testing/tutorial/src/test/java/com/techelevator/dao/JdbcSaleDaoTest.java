package com.techelevator.dao;

import com.techelevator.model.Sale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class JdbcSaleDaoTest extends BaseDaoTest {

    // Step One: Add constants for Madge

    // Step Two: Add constants for customer without sale and non-existent customer

    private JdbcSaleDao jdbcSaleDao;

    @BeforeEach
    public void setup() {

        // Arrange - new instance of JdbcSaleDao before each and every test
        jdbcSaleDao = new JdbcSaleDao(dataSource);
    }

    @Test
    public void getSaleById_returns_correct_sale() {

        // Step One: Replace fail("Test not implemented.")
        fail("Test not implemented.");
    }

    @Test
    public void getSalesByCustomerId_with_valid_id_returns_correct_sales() {

        // Step Two: Replace fail("Test not implemented.")
        fail("Test not implemented.");
    }

    @Test
    public void createSale_creates_sale() {

        // Step Three: Replace fail("Test not implemented.")
        fail("Test not implemented.");
    }

    @Test
    public void updateSale_updates_sale() {

        // Step Four: Replace fail("Test not implemented.")
        fail("Test not implemented.");
    }

    @Test
    public void deleteSaleById_deletes_sale() {

        // Step Five: Replace fail("Test not implemented.")
        fail("Test not implemented.");
    }

    // Convenience method in lieu of a Sale constructor with all the fields as parameters.
    // Similar to mapRowToSale() in JdbcSaleDao.
    private static Sale mapValuesToSale(int saleId, BigDecimal total, boolean delivery, Integer customerId) {

        Sale sale = new Sale();
        sale.setSaleId(saleId);
        sale.setTotal(total);
        sale.setDelivery(delivery);
        sale.setCustomerId(customerId);
        return sale;
    }

    private void assertSalesMatch(Sale expected, Sale actual, String message) {

        assertEquals(expected.getSaleId(), actual.getSaleId(), message);
        assertEquals(expected.getTotal(), actual.getTotal(), message);
        assertEquals(expected.isDelivery(), actual.isDelivery(), message);
        assertEquals(expected.getCustomerId(), actual.getCustomerId(), message);
    }
}
