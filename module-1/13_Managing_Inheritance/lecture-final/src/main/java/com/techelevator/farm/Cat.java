package com.techelevator.farm;

// making the class final means nothing can inherit from it
// because Cats are perfect the way they are.
public final class Cat extends FarmAnimal{
    public Cat(String name){
        super(name,"Meow");
    }

    public String getCatSound(){
        return "Meow";
    }

    @Override
    public String eat(){
        return "toss food around the room";
    }
}
