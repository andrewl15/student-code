package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Lecture {

	public static void main(String[] args) throws IOException {

		Scanner userInput = new Scanner(System.in);

		/*
		 * The java.io.File class is a representation of file and directory path names.  It provides methods to inspect and
		 * modify file system objects.
		 *
		 * One benefit is that it compensates for differences in Windows and Unix use of '/' and '\' as directory delimiters.
		 *
		 * A new instance of File can be created from a String that contains a file system path
		 */
		System.out.println("What is the name of the directory to create");
		String dir = userInput.nextLine();
		//make file object
		File dirToCreate = new File(dir);
		//make sure it doesnt already exist
		if(dirToCreate.exists()){
			System.out.println("This directory already exists");
			throw new IOException("Directory already exists");
		}else{
			if(dirToCreate.mkdir()){
				System.out.println("Directory created successfully");
			}else{
				System.out.println("I'm sorry, I cannot do that");
				throw new IOException("Cant create directory");
			}
		}

		//create files
		System.out.println("name of file to create");
		String fileToCreate = userInput.nextLine();
		//create object
		File fileObj = new File(fileToCreate);
		fileObj.createNewFile();
		System.out.println("Abolsute path: " + fileObj.getAbsolutePath());
		System.out.println("Name of file: " + fileObj.getName());



		//write some info to the file
		try(PrintWriter pw = new PrintWriter(fileObj)){
			pw.print("Hello");
			pw.print("World");
			pw.println("!");
			pw.println("So long and thanks for all the fish");
		}
		userInput.close();
	}

}
