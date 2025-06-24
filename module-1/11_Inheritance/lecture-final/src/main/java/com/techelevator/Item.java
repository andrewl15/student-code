package com.techelevator;

public class Item {
    private String description;
    private String owner;

    public Item(String description){
        this.description = description;
    }
    public Item(String description, String owner){
        this.description = description;
        this.owner = owner;
    }
    public String getDescription(){
        return description;
    }
    public String getOwner(){
        return owner;
    }
}
