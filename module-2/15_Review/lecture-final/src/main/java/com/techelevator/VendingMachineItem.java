package com.techelevator;

/// <summary>
/// Base class for all Vending Machine Items. It is abstract since there isn't a generic Vending Machine Item.
/// </summary>

import java.math.BigDecimal;

public abstract class VendingMachineItem {
    /// <summary>
    /// The name of the item/product
    /// </summary>
    private String itemName;

    // Getter for itemName
    public String getItemName() {
        return itemName;
    }

    // Getter for price
    public BigDecimal getPrice() {
        return price;
    }

    /// <summary>
    /// The price of the item/product
    /// </summary>
    private BigDecimal price;
    /// <summary>
    /// An abstract method so each derived class has a Consume method
    /// </summary>
    /// <returns>The string the represents the consuming of the item/product</returns>
    public abstract String consume();
    /// <summary>
    /// Constructor for the instance
    /// </summary>
    /// <param name="itemName">The name of the item/product</param>
    /// <param name="price">The price of the item/product</param>
    public VendingMachineItem(String itemName,String price) throws NumberFormatException
    {
        // Nick (Cohort 5) says: make sure there is a string in the name to prevent crashes if we manipulate the itemName
        this.itemName = itemName == null ? "Unknown" : itemName;
        // Since the local variable price is the same as parameter price, use "this" to reference the local variable
        this.price = new BigDecimal(price);
    }
}
