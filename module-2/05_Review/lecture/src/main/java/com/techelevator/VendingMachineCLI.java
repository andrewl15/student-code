package com.techelevator;

import com.techelevator.exceptions.InvalidDollarAmount;
import com.techelevator.exceptions.VendingMachineException;

import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/// <summary>
/// This class will handle the interaction with the user. Good idea to separate
/// this out so if we need to go to a different platform, we don't mess with our
/// core business logic
/// </summary>
public class VendingMachineCLI {
    /// <summary>
    /// Set up some constants for the menu selections to make the code easier to read
    /// </summary>
    private final String OPTION_DISPLAY_PURCHASE_MENU = "2";
    private final String OPTION_DISPLAY_VENDING_MACHINE = "1";
    private final String OPTION_INSERT_MONEY = "1";
    private final String OPTION_MAKE_SELECTION = "2";
    private final String OPTION_HIDDEN_MENU = "4";
    private final String OPTION_QUIT = "3";
    private final String OPTION_RETURN_CHANGE = "1";
    private final String OPTION_RETURN_TO_PREVIOUS_MENU = "3";
    /// <summary>
    /// Store the vending machine object. Read only so once loaded we don't overwrite it
    /// </summary>
    private VirtualVendingMachine vm;
    // Set up the keyboard scanner. Remember, only one per class!
    Scanner keyboard = new Scanner(System.in);
    /// <summary>
    /// Constructor to get things going and have access to the vending machine
    /// </summary>
    /// <param name="vvm"></param>
    public VendingMachineCLI(VirtualVendingMachine vvm)
    {
        vm = vvm;
    }
    /// <summary>
    /// The first method executed. This controls the flow of the program
    /// </summary>
    public void Run()
    {
        //Put the title on the screen
        printTitle();
        do
        {
            // A blank line, then the first menu
            System.out.println();
            displayMainMenu();
            // This loop until I exit manually
        } while (true);

    }

    /// <summary>
    /// Helper method to put the title on the screen
    /// </summary>
    private void printTitle()
    {
        System.out.println("Welcome to VVM");
    }
    /// <summary>
    /// The first menu
    /// </summary>
    private void displayMainMenu()
    {
        // Display the three possibilities
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");
        // Read from the user, and trim off the spaces

        String input = keyboard.nextLine().trim();
        // Based on user input, take the appropriate action
        switch (input)
        {
            // Display the inventory
            case OPTION_DISPLAY_VENDING_MACHINE:
                displayInventory();
                break;
            // Display the purchase menu
            case OPTION_DISPLAY_PURCHASE_MENU:
                displayPurchaseMenu();
                break;
            // Close the application
            case OPTION_HIDDEN_MENU:
                vm.createSalesReport();
                break;
            // Close the application
            case OPTION_QUIT:
                System.out.println("Thank you for your purchases.");
                // Call the vending machine to calculate change
                System.out.println(vm.returnChange());
                // Manually close the program
                System.exit(0);
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// This displays the current inventory to the screen
    /// </summary>
    private void displayInventory()
    {
        // Loop through the inventory dictionary
        for(Map.Entry<String, Queue<VendingMachineItem>> kvp : vm.getInventory().entrySet())
        {
            // if the queue is empty, we are sold out.
            if (kvp.getValue().size() == 0)
            {
                // Create the sold out item for display
                SoldOutItem soldOutItem = new SoldOutItem();
                // Disply the sold out item
                System.out.println(kvp.getKey() + " | " + soldOutItem.getItemName() + " | " + soldOutItem.getPrice() + " | " + soldOutItem.getItemName());
            }
            else
            {
                // Display the first item in the queue using Peek()
                System.out.println(kvp.getKey() + " | " + kvp.getValue().peek().getItemName() + " | " + kvp.getValue().peek().getPrice() + " | " + kvp.getValue().size());
            }
        }
        System.out.println();
    }
    /// <summary>
    /// Display the purchase menu to the screen
    /// </summary>
    private void displayPurchaseMenu()
    {
        String input = "";
        do
        {
            // Write the options to the screen
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            // Show them their current balance formatted for local currency
            System.out.println("Current Money Provided: " + vm.getCurrentBalance() );
            // Read from the user, and trim off the spaces
            input = keyboard.nextLine().trim();
            // Based on user input, take the appropriate action
            switch (input)
            {
                // The want to add money, call the FeedMoney method in the virtual machine
                case OPTION_INSERT_MONEY:
                    // Need to prompt for how much, use AskForMoney helper method
                    try{
                        vm.feedMoney(askForMoney());
                    } catch (InvalidDollarAmount ida) {
                        System.out.println(ida.getMessage());
                    }

                    break;
                // Make a selection for purchase
                case OPTION_MAKE_SELECTION:
                    makeSelection();
                    break;
                // Nothing to do if they selected quit
                case OPTION_QUIT:

                    break;
                default:
                    break;
            }
            // As long as they don't select Finish Transaction (3), loop this menu
        } while (!input.equals("3"));
    }
    /// <summary>
    /// Helper method to prompt the user for money
    /// </summary>
    /// <returns>An integer of money. No coins accepted</returns>
    private int askForMoney()
    {
        // Set to the moneyToLoad to an invalid number
        int moneyToLoad = -1;
        do
        {
            // Prompt the user to add money.
            System.out.println("Insert money in whole dollar amounts:");
            // Read from the user, and trim off the spaces
            String input = keyboard.nextLine().trim();
            try
            {
                // If we can parse the value, set it to moneyToLoad
                moneyToLoad = Integer.parseInt(input);
            }
            catch (Exception e)
            {
                // If we can't parse, tell them why
                System.out.println("Please only use numbers in your dollar amounts.");
            }
            // As long as moneyToLoad is -1, keep asking to load money
            // this protects from negative values
        } while (moneyToLoad < 0);
        // Have the method give back the money they loaded
        return moneyToLoad;
    }

    /// <summary>
    /// Helper method to choose a product to purchase
    /// </summary>
    private void makeSelection()
    {
        String selection = "";
        do
        {
            // Show them the current inventory
            displayInventory();
            // Show the user how much money is in the machine.
            System.out.println("Current balance is " + vm.getCurrentBalance());
            // Prompt them to make a selection
            System.out.println("Please Make a Selection.");
            System.out.println("Type Q to return to Main Menu.");
            // Read from the user, and trim off the spaces
            selection = keyboard.nextLine().trim();
            // Make sure they aren't wanting to quit
            if (!selection.toLowerCase().startsWith("q"))
            {

                try
                {
                    // Call the purchase menu on the vending machine and capture the purchased item
                    VendingMachineItem vmi = vm.purchase(selection.toUpperCase());
                    // Tell the user what they purchases
                    System.out.println();
                    System.out.println("You purchased " + vmi.getItemName() + " for " + vmi.getPrice());
                    // Listen to the user eat their purchase
                    System.out.println(vmi.consume());
                    System.out.println();
                }
                catch (VendingMachineException e)
                {
                    // Any errors, write them to the screen
                    System.out.println(e.getMessage());
                    System.out.println();
                }

            }
            // Make sure they aren't wanting to quit
        } while (!selection.toLowerCase().startsWith("q"));

    }

}
