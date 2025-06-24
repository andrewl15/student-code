package com.techelevator;
    /// <summary>
    /// Item that inherits from VendingMachineItem
    /// </summary>
public class ChipItem extends VendingMachineItem{
    /// <summary>
    /// Constructor to set item's name and price. Calls the base constructor to set them
    /// </summary>
    /// <param name="itemName">The name of the item</param>
    /// <param name="price">The price of the item</param>
    public ChipItem(String itemName,String price) {
        super(itemName,price);
    }
    /// <summary>
    /// Implementation of the abstract method for consuming the item
    /// </summary>
    /// <returns>The string specific to this item</returns>
    public String consume()
    {
        return "Crunch Crunch, Yum!";
    }
}
