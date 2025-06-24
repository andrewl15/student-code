package com.techelevator;

import com.techelevator.exceptions.VendingMachineException;

public class Application {
    public static void main(String[] args) {
        // Keep main method minimal. All the work should happen in the classes


        try{
            // Instantiate a VirtualVendingMachine object. This will hold the balance for the machine
            // and the methods related to the vending machine
            VirtualVendingMachine vm = new VirtualVendingMachine();
            // Instantiate the VendingMachineCLI (command line interface). This object will be responsible
            // for the interaction with the user.
            VendingMachineCLI vendingMachineCLI = new VendingMachineCLI(vm);
            // Run the main method to start displaying menus to the user.
            vendingMachineCLI.Run();
        } catch (VendingMachineException vme) {
            System.out.println(vme.getMessage());
            System.exit(1);
        }


    }
}
