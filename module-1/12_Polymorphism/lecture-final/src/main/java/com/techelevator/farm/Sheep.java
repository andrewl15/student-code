package com.techelevator.farm;

import java.math.BigDecimal;

public class Sheep extends FarmAnimal implements Sellable{
    public Sheep(String name, String sound) {
        super(name, sound);
    }

    @Override
    public BigDecimal getPrice() {
        return new BigDecimal("456.75");
    }
}
