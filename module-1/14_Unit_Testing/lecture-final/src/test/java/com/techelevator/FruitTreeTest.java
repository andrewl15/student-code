package com.techelevator;

import com.techelevator.fruittree.FruitTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FruitTreeTest {

    private String typeOfFruit = "Apple";
    private FruitTree sut;

    // this method will be executed beforeEach test
    @BeforeEach
    public void setup(){
        // reset my sut to null
        sut = null;
    }

    @Test
    public void constructor_initializes_fruit_tree_happy_path(){
        // Arrange
        // String typeOfFruit = "Pear";
        int numberOfFruit = 42;

        // Act
        sut = new FruitTree(typeOfFruit,numberOfFruit);

        // Assert
        // did it create a fruit tree?
        assertNotNull(sut);
    }

    @Test
    public void constructor_initializes_with_null_fruit_type(){
        // Arrange
        typeOfFruit = null;
        int numberOfFruit = 8;

        // Act
        sut = new FruitTree(typeOfFruit,numberOfFruit);

        // Assert
        assertNotNull(sut);
    }

    @Test
    public void constructor_initialize_with_zero_fruit(){
        // Arrange
        // String typeOfFruit = "Apple";
        int numberOfFruit = 0;

        // Act
        sut = new FruitTree(typeOfFruit,numberOfFruit);

        // Assert
        assertNotNull(sut);

    }

    @Test
    public void constructor_initialize_with_negative_fruit(){
        // Arrange
        // String typeOfFruit = "Orange";
        int numberOfFruit = -10;

        // Act
        sut = new FruitTree(typeOfFruit,numberOfFruit);

        // Assert
        // should not be able to create a tree with negative fruit
        // talked with developer, they will return a tree with 0 fruit
        // assertNull(sut);
        assertNotNull(sut);
        assertEquals(0,sut.getPiecesOfFruitLeft());
    }

    @Test
    public void pickFruit_with_enough_fruit_to_pick(){
        // Arrange
        // String fruitType = "Banana";
        int numberOfFruit = 50;
        // what do I expect?
        // expect true since the fruit can be picked
        int numberOfFruitToPick = 10;
        int expectedFruitLeft = numberOfFruit - numberOfFruitToPick;
        sut = new FruitTree(typeOfFruit,numberOfFruit);

        // Act
        boolean actualResult = sut.pickFruit(numberOfFruitToPick);

        // Assert
        // did it return the right boolean
        assertTrue(actualResult);
        // does it have the correct amount of fruit remaining?
        assertEquals(expectedFruitLeft,sut.getPiecesOfFruitLeft());
    }

    @Test
    public void pickFruit_without_enough_fruit_to_pick(){
        // Arrange
        int numberOfFruit = 15;
        int numberOfFruitToPick = 25;
        sut = new FruitTree(typeOfFruit,numberOfFruit);

        // Act
        boolean actual = sut.pickFruit(numberOfFruitToPick);

        // Assert
        assertFalse(actual,"Hey, I expected to see false that you didn't pick fruit.");
        assertEquals(numberOfFruit,sut.getPiecesOfFruitLeft());
    }
}
