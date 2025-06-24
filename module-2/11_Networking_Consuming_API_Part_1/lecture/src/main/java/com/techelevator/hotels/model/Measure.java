package com.techelevator.hotels.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Measure {

    private String unitCode;
    private double value;

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String symbol = "";
        switch (unitCode) {
            case ("wmoUnit:degC"): {
                symbol = "°C";
                break;
            }
            case ("wmoUnit:m"): {
                symbol = "m";
                break;
            }
            case ("wmoUnit:percent"): {
                symbol = "%";
                break;
            }
            case ("wmoUnit:km_h-1"): {
                symbol = "km/h";
                break;
            }
        }
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value) + symbol;
    }
}
