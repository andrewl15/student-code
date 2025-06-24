package com.techelevator;

import java.math.BigDecimal;

/// <summary>
/// Item that inherits from VendingMachineItem
/// </summary>
public class SoldOutItem extends VendingMachineItem{
    /// <summary>
    /// Constructor to set item's name and price. Calls the base constructor to set them
    /// </summary>
    /// <param name="itemName">The name of the item</param>
    /// <param name="price">The price of the item</param>
    public SoldOutItem() {
        super("SOLD OUT","0");
    }
    /// <summary>
    /// Implementation of the abstract method for consuming the item
    /// </summary>
    /// <returns>The string specific to this item</returns>
    public String consume()
    {
        return "Hmm, nothing to eat.";
    }
}
