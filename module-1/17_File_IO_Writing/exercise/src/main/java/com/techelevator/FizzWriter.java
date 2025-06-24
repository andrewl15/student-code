package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FizzWriter {

    // Scanner for all user input. Don't create additional Scanners with System.in
	private final Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		FizzWriter fizzWriter = new FizzWriter();
		fizzWriter.run();
	}

	public void run() {

        // Get the path of the file to write
        File destinationFile;
        String path = promptForFilePath();
        while (!isFileValid(path)) {
            path = promptForFilePath();
        }
        destinationFile = new File(path);

        // Get the starting number
        int startNumber = promptForNumber("starting");

        // Get the ending number
        int endNumber = promptForNumber("ending");
        while (endNumber <= startNumber) {
            System.out.println("Ending number must be greater than the starting number");
            endNumber = promptForNumber("ending");
        }

        // Get the "fizz" value
        String fizz = promptForString("fizz");

        // Get the "buzz" value
        String buzz = promptForString("buzz");

        // Write file
        try {
            writeFizzBuzzFile(destinationFile, startNumber, endNumber, fizz, buzz);
        } catch (IOException e) {
            System.out.println("An error occurred while searching text: " + e.getMessage());
        }

        System.out.println("Done.");
    }

    /**
     * Creates a new file containing the "fizz buzz" output, based on the start and end numbers, and the substituted values.
     * @param destinationFile the file to write to
     * @param startNumber the number to start with
     * @param endNumber the number to end with
     * @param fizzValue the value to print for the "fizz" numbers (divisible by 3)
     * @param buzzValue the value to print for the "buzz" numbers (divisible by 5)
     * @throws IOException thrown if file could not be read, written, or other related error
     */
    public void writeFizzBuzzFile(File destinationFile, int startNumber, int endNumber, String fizzValue, String buzzValue) throws IOException {
        try(PrintWriter dataOutput = new PrintWriter(destinationFile)){
            for(int i = startNumber; i <= endNumber; i++){
                if(i % 3 == 0 && i % 5 == 0){
                    dataOutput.println(fizzValue + buzzValue);
                }else if(i % 3 == 0){
                    dataOutput.println(fizzValue);
                }else if(i % 5 == 0){
                    dataOutput.println(buzzValue);
                }else{
                    dataOutput.println(i);
                }
            }
        }
	}

    /**
     * Validates that provided path exists (at least up to the containing directory) and file extension is ".txt".
     * @param path the path to the file
     * @return true if is a file and exists, otherwise false
     */
    private boolean isFileValid(String path) {
        if (!path.endsWith(".txt")) {
            System.out.println("File does not have '.txt' extension");
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            // make sure path is a full path
            File parent = file.getParentFile();
            if (parent == null) {
                System.out.println("Enter a full path for the file location.");
                return false;
            }

            // directory of file exists (but not the file itself) is considered valid
            if (file.getParentFile().isDirectory() && file.getParentFile().exists()) {
                return true;
            } else {
                System.out.println(path + " does not exist");
                return false;
            }
        }
        if (!file.isFile()) {
            System.out.println(path + " is not a file");
            return false;
        }

        return true;
    }


    // ** methods prompting for user input **

    private String promptForFilePath() {
        System.out.println("Where do you want to save the file?");
        return userInput.nextLine();
    }

    private int promptForNumber(String position) {
        Integer value = null;
        while (value == null) {
            System.out.println("What is the " + position + " value?");
            String input = userInput.nextLine();

            // check if value is an integer
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException ignored) {
                System.out.println("Value is not an integer");
            }
        }
        return value;
    }

    private String promptForString(String name) {
        System.out.println("What value should be printed for '" + name + "'? (default: " + name + ")");
        String line = userInput.nextLine();
        if (line.isEmpty()) {
            line = name;
        }
        return line;
    }
}
