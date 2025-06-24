package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FindAndReplace {

    // Scanner for all user input. Don't create additional Scanners with System.in
    private final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        FindAndReplace findAndReplace = new FindAndReplace();
        findAndReplace.run();
    }

    public void run() {

        File inputFile = null;
        File outputFile = null;

        // Get the path of the file to read
        String inputPath = promptForFilePathRead();
        while (!isFileValid(inputPath)) {
            inputPath = promptForFilePathRead();
        }
        inputFile = new File(inputPath);

        // Get the path of the file to write
        String outputPath = promptForFilePathWrite();
        while (!isFileValid(outputPath)) {
            outputPath = promptForFilePathWrite();
        }
        outputFile = new File(outputPath);

        // Get the search term
        String searchTerm = "";
        while (searchTerm.isEmpty()) {
            searchTerm = promptForSearchTerm();
        }

        // Get the replacement term
        String replacementTerm = "";
        while (replacementTerm.isEmpty()) {
            replacementTerm = promptForReplacementTerm();
        }

        System.out.println("---------------------");

        // Perform search & replace
        try {
            writeReplacedTextFile(inputFile, outputFile, searchTerm, replacementTerm);
        } catch (IOException e) {
            System.out.println("An error occurred while searching text: " + e.getMessage());
        }
    }

    /**
     * Copies the contents of sourceFile to destinationFile, replacing occurrences of searchTerm with replacementTerm.
     * Line breaks in sourceFile are maintained and copied to destinationFile.
     * @param sourceFile the file to read from
     * @param destinationFile the file to write to
     * @param searchTerm the search term to look for in the file (match is case-sensitive)
     * @param replacementTerm the term to replace where searchTerm is found
     * @throws IOException thrown if file could not be read, written, or other related error
     */
    public void writeReplacedTextFile(File sourceFile, File destinationFile, String searchTerm, String replacementTerm) throws IOException {
        try(Scanner dataInput = new Scanner(sourceFile); PrintWriter dataOutput = new PrintWriter(destinationFile)){
            while(dataInput.hasNext()){
                String inputLine = dataInput.nextLine();
                dataOutput.println(inputLine.replace(searchTerm, replacementTerm));
            }
        }
    }

    /**
     * Validates that provided path exists (at least up to the containing directory) and file extension is ".txt".
     * @param path the path to the file
     * @return true if is a file and exists, or file doesn't exist but the entire path does, otherwise false
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

    private String promptForFilePathRead() {
        System.out.println("What is the file that you want to search?");
        return userInput.nextLine();
    }

    private String promptForFilePathWrite() {
        System.out.println("What is the file that you want to write to?");
        return userInput.nextLine();
    }

    private String promptForSearchTerm() {
        System.out.println("What is the search term you are looking for?");
        String searchTerm = userInput.nextLine();
        if ((searchTerm == null) || (searchTerm.isEmpty())) {
            System.out.println("The search term is empty");
        }
        return searchTerm;
    }

    private String promptForReplacementTerm() {
        System.out.println("What is the replacement term?");
        String searchTerm = userInput.nextLine();
        if ((searchTerm == null) || (searchTerm.isEmpty())) {
            System.out.println("The search term is empty");
        }
        return searchTerm;
    }

}
