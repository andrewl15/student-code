package com.techelevator;

public class SavingsAccount extends BankAccount{
    public SavingsAccount(String accountHolderName, String accountNumber, int balance){
        super(accountHolderName, accountNumber, balance);
    }
    public SavingsAccount(String accountHolderName, String accountNumber){
        super(accountHolderName, accountNumber);
    }
    @Override
    public int withdraw(int amountToWithdraw){
        int currentBalance = getBalance();
        int newBalance = currentBalance - amountToWithdraw;
        if(newBalance >= 150){
            super.withdraw(amountToWithdraw);
        }else if(newBalance - 2 >= 0){
            super.withdraw(amountToWithdraw + 2);
        }
        return getBalance();
    }
}
