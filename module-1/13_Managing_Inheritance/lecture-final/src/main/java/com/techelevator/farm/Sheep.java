package com.techelevator.farm;

public class Sheep extends FarmAnimal{
    public Sheep(){
        super("Grommit","baaa");
    }

    @Override
    public String eat() {
        return "Gnaws on hay";
    }
}
