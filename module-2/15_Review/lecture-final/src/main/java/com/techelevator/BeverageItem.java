package com.techelevator;

/// <summary>
/// Item that extends VendingMachineItem
/// </summary>
public class BeverageItem extends VendingMachineItem{
    /// <summary>
    /// Constructor to set item's name and price. Calls the base constructor to set them
    /// </summary>
    /// <param name="itemName">The name of the item</param>
    /// <param name="price">The price of the item</param>
    public BeverageItem(String itemName, String price){
        super(itemName,price);
    }
    /// <summary>
    /// Implementation of the abastract method for consuming the item
    /// </summary>
    /// <returns>The string specific to this item</returns>
    public String consume() {
        return "Glub Glub, Yum!";
    }
}
