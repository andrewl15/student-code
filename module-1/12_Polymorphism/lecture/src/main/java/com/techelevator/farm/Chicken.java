package com.techelevator.farm;

public class Chicken extends FarmAnimal{
    public Chicken(String name, String sound){
        super(name, sound);
    }

    public Egg layEgg(){
        return new Egg();
    }
}
