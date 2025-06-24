package com.techelevator.farm;

public class Calico extends FarmAnimal{
    public Calico() {
        super("Felix","Meeeeoooww");
    }
    @Override
    public String eat(){
        return "catches mice";
    }
}
