package com.techelevator.tebucks.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TxLogDTO {
    // Put in some validation
    @NotBlank
    private String description;
    @NotBlank
    private String username_from;
    @NotBlank
    private String username_to;
    @Min(value=0)
    private double amount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername_from() {
        return username_from;
    }

    public void setUsername_from(String username_from) {
        this.username_from = username_from;
    }

    public String getUsername_to() {
        return username_to;
    }

    public void setUsername_to(String username_to) {
        this.username_to = username_to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

