package com.techelevator;

import com.techelevator.fruittree.FruitTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FruitTreeTest {

    private String typeOfFruit = "Apple";
    private FruitTree sut;

    //this method will be executed before each test
    @BeforeEach
    public void setup(){
        sut = null;
    }
    @Test
    public void constructor_initializes_fruit_tree_happy_earth(){
        //Arrange
        //String typeOfFruit = "Pear";
        int numberOfFruit = 42;

        //Act
        FruitTree sut = new FruitTree(typeOfFruit, numberOfFruit);

        //Assert
        //did we create a fruit tree?
        assertNotNull(sut);

    }

    @Test
    public void constructor_initializes_with_null_fruit_type(){
        //Arrange
        typeOfFruit = null;
        int numnberOfFruit = 8;

        //Act
        FruitTree sut = new FruitTree(typeOfFruit, numnberOfFruit);

        //Assert
        assertNotNull(sut);
    }

    @Test
    public void constructor_initializes_with_zero_fruit(){
        //String typeOfFruit = "Apple";
        int numberOfFruit = 0;

        FruitTree sut = new FruitTree(typeOfFruit, numberOfFruit);

        assertNotNull(sut);
    }

    @Test
    public void constructor_initializes_with_negative_fruit(){
        //String typeOfFruit = "Grape";
        int numberOfFruit = -10;

        FruitTree sut = new FruitTree(typeOfFruit, numberOfFruit);


        //talked with developer, they will return a tree with zero fruit
        assertNotNull(sut);
        assertEquals(0, sut.getPiecesOfFruitLeft());
    }

    @Test
    public void pickFruit_with_enough_fruit_to_pick(){
        //Arrange
        //String fruitType = "Banana";
        int numberOfFruit = 50;
        //what do I expect
        int numberOfFruitToPick = 10;
        int expectedFruitLeft = numberOfFruit - numberOfFruitToPick;
        FruitTree sut = new FruitTree(typeOfFruit, numberOfFruit);
        //Act
        boolean actualResult = sut.pickFruit(numberOfFruitToPick);

        //Assert
        assertTrue(actualResult);
        //doess it have the correct amount of fruit left
        assertEquals(expectedFruitLeft, sut.getPiecesOfFruitLeft());
    }

    @Test
    public void pickFruit_without_enough_fruit_to_pick(){
        int numberOfFruit = 15;
        int numberOfFruitToPick = 16;
        sut = new FruitTree(typeOfFruit, numberOfFruit);

        boolean actual = sut.pickFruit(numberOfFruitToPick);

        assertFalse(actual);
        assertEquals(numberOfFruit, sut.getPiecesOfFruitLeft());
    }
}
