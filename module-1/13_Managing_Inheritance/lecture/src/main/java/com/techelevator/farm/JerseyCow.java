package com.techelevator.farm;

public class JerseyCow extends Cow{
    @Override
    public String eat() {
        return null;
    }

    //override from the base object
    @Override
    public String toString(){
        return "This is a Jersey Cow";
    }
}
