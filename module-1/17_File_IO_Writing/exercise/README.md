# File I/O (Writing)

The purpose of this exercise is to provide you with the opportunity to create command-line applications that can create and write to files.

## Learning objectives

After completing this exercise, you'll understand:

* How to programmatically create and write to text files.
* How to read, interpret, and resolve errors related to file I/O.

## Evaluation criteria and functional requirements

* The project must not have any build errors.
* The program writes the expected results to a file.
  * You don't need to submit the output files with your exercise. Your exercise score depends on the unit tests passing.
* Paths to files aren't hard-coded—in other words, the user must be able to enter the path to the input file.
* Your console application works as expected when you run it.
  * Each file has a main method that allows you to run it as a console application.
* The unit tests pass as expected.
  * Note: Tests are only provided for the FindAndReplace and FizzWriter exercises.

## Getting started

1. Open the `file-io-part2-exercise` project in IntelliJ.
2. Open the Java file for the exercise you're working on. The files are in the `src/main/java/com/techelevator` package.
3. Provide enough code to get the program started.
4. Verify your work on the command line.
5. Repeat until you've implemented all required features and all unit tests pass.

## Part One: Create a find and replace program

In this exercise, you'll write a program that finds all occurrences of a user-specified word in a text file and replaces it with another user-specified word. You'll write the text with the replaced word to a different text file.

You can use any text file on your computer in the program, or you can use the included file called `alice.txt` in your project folder.

### Step One: Review the starting code

Open `FindAndReplace.java` and review the starting code.

The main logic for `FindAndReplace` is in the `run()` method. It has a series of prompts for input from the user. The user must provide the following information:

1. The full path of the file to read.
  * The user is re-prompted if the path isn't valid. The starting code provides this logic.
2. The full path of the file to write.
  * The user is re-prompted if the path isn't valid. The starting code provides this logic.
3. The word or words to search for.
  * The user is re-prompted if the search term is blank.
4. The word or words to use as the replacement.
  * The user is re-prompted if the replacement term is blank.

After the program collects that information, it's passed to the `writeReplacedTextFile()` method where the logic for reading the input file and writing the output file goes. You have to write the code for this method.

### Step Two: Complete the `writeReplacedTextFile()` method

This writes the contents of the source file to the output file with the replaced words. If the file doesn't exist, create it. If it already exists, overwrite any existing content.

Refer to the Javadoc comment of the method for the purpose of each parameter.

The tests for this exercise are in the file `src/test/java/com/techelevator/FindAndReplaceTest.java`. When you complete this program, all tests pass.

#### Notes on search

This is a case-sensitive search. If the search term is "Drink", then you must not replace "drink" when you write the output file.

If the search term contains multiple words and are in the text file split between lines, you don't need to perform the find and replace on it. Only perform the find and replace if the entire search term is on one line.

## Part Two: Create a FizzWriter program

This purpose of this program is to write the "FizzBuzz" output to a file. Instead of starting at 1 and ending at a particular value, this program starts and ends at user-defined values. Additionally, the user can provide a different string in place of "fizz" or "buzz" in the output.

As a refresher, the "FizzBuzz" output is as follows:

* If the number is divisible by 3, print "fizz" (or the user-defined value).
* If the number is divisible by 5, print "buzz" (or the user-defined value).
* If the number is divisible by 3 and 5, print "fizzbuzz" (or the combined user-defined values for "fizz" and "buzz").
* Otherwise, print the number.

### Step One: Review the starting code

Open `FizzWriter.java` and review the starting code.

The main logic for `FizzWriter` is in the `run()` method. It has a series of prompts for input from the user. The user must provide the following information:

1. The full path of where to save the file.
  * The user is re-prompted if the path isn't valid. The starting code provides this logic.
2. The starting and ending values.
3. The optional replacement values for "fizz" and "buzz".
  * If the user doesn't enter values, it uses the defaults ("fizz" and "buzz").

After the program collects that information, it's passed to the `writeFizzBuzzFile()` method where the logic for "FizzBuzz" and writing the output file goes. You have to write the code for this method.

### Step Two: Complete the `writeFizzBuzzFile()` method

This writes the expected "FizzBuzz" output to the file path. If the file doesn't exist, create it. If it already exists, overwrite any existing content. Each number or word from the "FizzBuzz" output must appear on it's own line. Be sure to handle any errors that might occur when working with files.

Refer to the Javadoc comment of the method for the purpose of each parameter.

The tests for this exercise are in the file `src/test/java/com/techelevator/FizzWriterTest.java`. When you complete this program, all tests pass.

## File splitter (Challenge)

Create an application that takes a significantly large input file and splits it into smaller file chunks. These types of files were common back when floppy disks were popular and couldn't hold a larger program on their own.

To determine how many files you need to create, ask the user for the maximum amount of lines to appear in each output file.

Sample Input/Output:
```
Where is the input file (please include the path to the file)? [path-to-file]/input.txt
How many lines of text (max) should there be in the split files? 3
The input file has 50 lines of text.

Each file that is created must have a sequential number assigned to it.

For a 50 line input file "input.txt", this produces 17 output files.

**GENERATING OUTPUT**

Generating input-1.txt
Generating input-2.txt
Generating input-3.txt
Generating input-4.txt
Generating input-5.txt
Generating input-6.txt
Generating input-7.txt
Generating input-8.txt
Generating input-9.txt
Generating input-10.txt
Generating input-11.txt
Generating input-12.txt
Generating input-13.txt
Generating input-14.txt
Generating input-15.txt
Generating input-16.txt
Generating input-17.txt

```

Here are a few things to keep in mind:

* When you run the command `find . -name '[your-input-file-name]-*.[your-input-file-extension]' | xargs wc -l`, the result lists each file that matches that name and the line counts for each of the files. For instance, given the previous example, the result of the command would be:
    ```
       3 ./input-4.txt
       3 ./input-5.txt
       3 ./input-7.txt
       3 ./input-6.txt
       3 ./input-2.txt
       3 ./input-3.txt
       3 ./input-1.txt
       3 ./input-12.txt
       3 ./input-13.txt
       3 ./input-11.txt
       3 ./input-10.txt
       3 ./input-14.txt
       3 ./input-15.txt
       2 ./input-17.txt
       3 ./input-8.txt
       3 ./input-9.txt
       3 ./input-16.txt
      50 total
    ```
* Use the `less` command or your favorite text editor to verify the contents of the file are what you expect them to be.
* The input filename must be the prefix—the first part of the filename—followed by a dash (`-`), then the number of the current file, and finally ending with the file extension of the input file. For instance, if the name of the file is `big-old-file.md`, the filenames must be `big-old-file-1.md`, `big-old-file-2.md`, etc.
* Write the output files to the same directory the input file is in.
* The application must run and exit. You shouldn't need to press a key to stop the application.

## Tips and tricks

### Use the `wc` command to verify your work

Verify your work on the command line by running the `wc` command specified in the requirements. The `wc` command displays the number of lines, words, and bytes contained in each input file.

The definition of a line is a string of characters ending with a newline character. Characters beyond the final newline character aren't included in the line count. The `-l` flag determines the number of lines in the file, and the `-w` flag determines the number of words in the file.

For more information about what `wc` provides, try typing `wc --help`. Many commands in the terminal have a `--help` option to help the user learn more about the command and how it works.

### Learn more about the File class

The Java [File class][file-api-docs] is a helpful resource for learning about how to interact with files and directories programmatically.

---

[file-api-docs]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html
