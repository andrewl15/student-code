package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoffeeTest {

    @Test
    public void constructor_should_set_properties() {
        // Arrange - since this is testing a constructor, there's nothing to set up

        // Act - call the constructor by creating a new object, passing valid parameters
        Coffee coffee = new Coffee("Large", "Decaf", new String[] { "cream", "sugar" }, 2.99);

        // Assert - verify the properties are set appropriately
        assertEquals("Large", coffee.getSize(), "size not set correctly");
        assertEquals("Decaf", coffee.getBlend(), "blend not set correctly");
        assertEquals(2.99, coffee.getPrice(), 0.001, "price not set correctly"); // 0.001 is the delta, the allowable
                                                                                 // difference when comparing doubles
        assertEquals(2, coffee.getAdditions().length, "additions not set correctly");
    }
}
