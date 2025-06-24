package com.techelevator;

import java.util.Scanner;

public class UserInterface {
    Scanner keyboard = new Scanner(System.in);

    public void run(){

        System.out.println("*********** Welcome to Concentration ***********");
        System.out.print("How many pairs do you want? ");
        int numberOfPairs = Integer.parseInt(keyboard.nextLine());
        Game game = new Game(numberOfPairs);
        do{
            System.out.println(game.displayBoard());
            int guess = askForGuess();
            boolean correct = game.chooseSlot(guess);
            if(correct){
                System.out.println("Hooray, that's a match!");
            }
        }while (!game.gameOver());
        System.out.println();
        System.out.println("Thanks for playing.");

    }

    public int askForGuess(){
        System.out.print("Which number do you want to see? ");
        return Integer.parseInt(keyboard.nextLine());
    }
}
