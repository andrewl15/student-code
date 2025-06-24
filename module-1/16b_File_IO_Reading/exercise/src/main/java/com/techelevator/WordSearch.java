package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordSearch {

	// Scanner for all user input. Don't create additional Scanners with System.in
	private final Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		WordSearch wordSearch = new WordSearch();
		wordSearch.run();
	}

	public void run() {

		File inputFile = null;

		// Get the path of the file to search
		String path = promptForFilePath();
		while (!isFileValid(path)) {
			path = promptForFilePath();
		}
		inputFile = new File(path);

		// Get the search term
		String searchTerm = "";
		while (searchTerm.isEmpty()) {
			searchTerm = promptForSearchTerm();
		}

		// Ask if the search should be case-sensitive
		boolean isCaseSensitive = promptForCaseSensitive();

		// Ask if line numbers should be included
		boolean includeLineNumbers = promptForLineNumbers();

		System.out.println("---------------------");

		// Perform search
		List<String> matchingLines = new ArrayList<>();
		try {
			matchingLines = getMatchingLines(inputFile, searchTerm, isCaseSensitive, includeLineNumbers);
		} catch (IOException e) {
			System.out.println("An error occurred while searching text: " + e.getMessage());
		}

		// Display search results
		if (matchingLines.size() > 0) {
			System.out.println("The search term was found in the following lines:");
			for (String line : matchingLines) {
				System.out.println(line);
			}
		} else {
			System.out.println("The search term was not found in the file.");
		}

	}

	/**
	 * Reads the passed in File line-by-line, tracking occurrences of the searchTerm.
	 * @param file the file to read
	 * @param searchTerm the search term to look for in the file
	 * @param isCaseSensitive indicates whether search term match should be case-sensitive (true) or case-insensitive (false)
	 * @param includeLineNumbers indicates if line numbers should be included with the returned lines
	 * @return the lines from the file where the search term appears, optionally with the line numbers if includeLineNumbers is true
	 * @throws IOException thrown if file could not be read or other related error
	 */
	public List<String> getMatchingLines(File file, String searchTerm, boolean isCaseSensitive, boolean includeLineNumbers) throws IOException {
		List<String> matchingLines = new ArrayList<>();
		String target = isCaseSensitive ? searchTerm : searchTerm.toLowerCase();
		int currentLineNumber = 1;

		Scanner fileScanner = new Scanner(file);

		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String thisLine = isCaseSensitive ? line : line.toLowerCase();

			if (thisLine.contains(target)) {
				matchingLines.add(includeLineNumbers ? currentLineNumber + ") " + line : line);
			}
			currentLineNumber++;
		}

		return matchingLines;

	}

	/**
	 * Validates that provided path is a file and exists
	 * @param path the path to the file
	 * @return true if is a file and exists, otherwise false
	 */
	public boolean isFileValid(String path) {
		File inputFile = new File(path);
		return inputFile.exists() && inputFile.isFile();
	}


	// ** methods prompting for user input **

	private String promptForFilePath() {
		System.out.println("What is the file that you want to search?");
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

	private boolean promptForCaseSensitive() {
		System.out.println("Should the search be case-sensitive? (Y\\N)");
		return userInput.nextLine().equalsIgnoreCase("y");
	}

	private boolean promptForLineNumbers() {
		System.out.println("Should the output include line numbers? (Y\\N)");
		return userInput.nextLine().equalsIgnoreCase("y");
	}
}
