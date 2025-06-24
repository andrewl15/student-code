package com.techelevator.model;

public class VMSlot {
    private String slotID;
    private VMItem item;

    public VMItem getItem() {
        return item;
    }

    public void setItem(VMItem item) {
        this.item = item;
    }

    public String getSlotID() {
        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }
}
