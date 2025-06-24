package com.techelevator;

public class CheckingAccount extends BankAccount{

    public CheckingAccount(String accountHolderName, String accountNumber, int balance){
        super(accountHolderName, accountNumber, balance);
    }
    public CheckingAccount(String accountHolderName, String accountNumber){
        super(accountHolderName, accountNumber);
    }
    @Override
    public int withdraw(int amountToWithdraw) {
        int currentBalance = getBalance();
        int newBalance = currentBalance - amountToWithdraw;
        if (newBalance >= 0) {
            super.withdraw(amountToWithdraw);
        } else if (newBalance > -100) {
            super.withdraw(amountToWithdraw + 10);
        }
        return getBalance();
    }


}
