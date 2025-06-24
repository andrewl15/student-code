package com.techelevator.farm;

//final classes means that no class can inherit it
public final class Cat extends FarmAnimal{
    public Cat(String name) {super(name, "Meow");}

    public String getCatSound(){
        return "Meow";
    }

    @Override
    public String eat(){return "Eating all the field mice";}
}
