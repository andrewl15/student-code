package com.techelevator;

public class Application {
    public static void main(String[] args){
        System.out.println("Bank Account");
        System.out.println("-----------------");

        BankAccount newBankAccount = new BankAccount("Andrew","1234");

        newBankAccount.deposit(5_000);
        System.out.println(newBankAccount.getAccountHolderName() + " " + newBankAccount.getAccountNumber() + " " + newBankAccount.getBalance());
        System.out.println();

        System.out.println("Checking Account");
        System.out.println("-----------------");
        CheckingAccount newCheckingAccount = new CheckingAccount("Andrew C", "1223", 150);
        newCheckingAccount.withdraw(151);
        System.out.println(newCheckingAccount.getBalance());
        System.out.println();

        System.out.println("Savings Account");
        System.out.println("-----------------");
        SavingsAccount newSavingsAccount = new SavingsAccount("Andrew S", "13423", 200);
        newSavingsAccount.withdraw(51);
        System.out.println(newSavingsAccount.getBalance());
    }
}
