package com.techelevator.farm;

public class Jersey extends Cow{
    @Override
    public String eat() {
        return "Chews grass on the Jersey Shore.";
    }
    // override from the base Object
    @Override
    public String toString(){
        return "This is a Jersey Cow.";
    }
}
