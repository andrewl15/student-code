package com.techelevator.farm;

public class Calico extends FarmAnimal{

    public Calico() {
        super("Felix", "Meow");
    }

    @Override
    public String eat(){
        return "Catches mice";
    }
}
