package com.techelevator.farm;

import java.math.BigDecimal;

public class Tractor implements Singable, Sellable {
    @Override
    public String getName() {
        return "Tractor";
    }

    @Override
    public String getSound() {
        return "Vrooom";
    }

    @Override
    public BigDecimal getPrice(){
        return new BigDecimal("75000");
    }
}
