package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Lecture {

	public static void main(String[] args) throws IOException {

		DirListing ourObject = new DirListing();
		ourObject.makeDirList();

		Scanner userInput = new Scanner(System.in);

		/*
		 * The java.io.File class is a representation of file and directory path names.  It provides methods to inspect and
		 * modify file system objects.
		 *
		 * One benefit is that it compensates for differences in Windows and Unix use of '/' and '\' as directory delimiters.
		 *
		 * A new instance of File can be created from a String that contains a file system path
		 */

		// Get info about a file or path
		System.out.print("Enter file name or path. ");
		String path = userInput.nextLine();
		// create a File object
		File userPath = new File(path);
		System.out.println();
		// does this directory already exist?
		if(userPath.exists()){
			// let's get some data about this directory
			System.out.println("name: " + userPath.getName());
			System.out.println("absolute path: " + userPath.getAbsolutePath());
			if(userPath.isDirectory()){
				System.out.println("It is a directory.");
			} else if(userPath.isFile()){
				System.out.println("Is file.");
			}
			System.out.println("size: " + userPath.length());
		}
		// create a directory
		System.out.print("What is the name of the directory to create? ");
		String dir = userInput.nextLine();
		// Make my file object
		File dirToCreate = new File(dir);
		if(dirToCreate.exists()){
			System.out.println("This directory already exists.");
			throw new IOException("Directory already exists!");
		} else {
			if(dirToCreate.mkdir()){
				System.out.println("Directory created successfully.");
			} else {
				System.out.println("I'm sorry Dave, I cannot do that.");
				throw new IOException("Can't create directory.");
			}
		}

		// Create files
		System.out.print("Name of file to create: ");
		String fileToCreate = userInput.nextLine();
		// Create file object
		File fileObj = new File(fileToCreate);
		fileObj.createNewFile();
		System.out.println("Absolute path: " + fileObj.getAbsolutePath());
		System.out.println("Name of file: " + fileObj.getName());

		// Write some info to the file
		try(PrintWriter pw = new PrintWriter(fileObj)){
			pw.print("Hello ");
			pw.print("World");
			pw.println("!");
			pw.println("So long and thanks for all the fish.");
		}


		userInput.close();

	}

}
