package com.techelevator;

import com.techelevator.testing.TestingLibrary;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.techelevator.testing.TestingLibrary.runTestSuite;
import static org.junit.jupiter.api.Assertions.fail;

public class FindAndReplaceTest {

    private static final String FILE_NAME = "elysian-fields";
    private static final String TEST_INPUT_FILE_PATH = "src/test/resources/" + FILE_NAME + ".txt";
    private static final String TEST_OUTPUT_FILE_PATH = "src/test/resources/" + FILE_NAME + "-%s.txt";
    private static final File NON_FILE = new File ("src/test/resources/");
    FindAndReplace findAndReplace;
    private List<String> srcContent;

    @BeforeAll
    public static void cleanupTestFiles() {
        String regex = FILE_NAME + "-.+\\.txt$";
        File dir = new File(TEST_OUTPUT_FILE_PATH).getParentFile();
        File[] test_files = dir.listFiles(file -> file.getName().matches(regex));
        if (test_files != null) {
            for (File file : test_files) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    @BeforeEach
    public void initialize() {
        findAndReplace = new FindAndReplace();
        // get text from source file
        File srcFile = new File(TEST_INPUT_FILE_PATH);
        try {
            srcContent = Files.readAllLines(srcFile.toPath());
        } catch (IOException e) {
            fail("Unable to read file to generate expected output");
        }
    }

    @Test
    public void writeReplacedTextFile_replacesMultipleOccurrences() throws IOException {
        final File input = new File(TEST_INPUT_FILE_PATH);

        TestingLibrary.TestGroup happyPath = new TestingLibrary.TestGroup("Happy path - replace one word with another");

        File output = new File(String.format(TEST_OUTPUT_FILE_PATH, "happy-path-multiple-1"));
        String searchTerm = "ancient";
        String replaceTerm = "venerable";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        happyPath.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));

        output = new File(String.format(TEST_OUTPUT_FILE_PATH, "happy-path-multiple-2"));
        searchTerm = "amidst";
        replaceTerm = "among";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        happyPath.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));


        TestingLibrary.TestGroup edgeCases = new TestingLibrary.TestGroup("Edge cases - replace multiple words with one or more words");

        output = new File(String.format(TEST_OUTPUT_FILE_PATH, "edge-case-multiple-1"));
        searchTerm = "Elysian Fields";
        replaceTerm = "Toledo";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        edgeCases.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));

        output = new File(String.format(TEST_OUTPUT_FILE_PATH, "edge-case-multiple-2"));
        searchTerm = "beauty and";
        replaceTerm = "charms with";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        edgeCases.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));


        List<TestingLibrary.TestGroup> testGroups = new ArrayList<>();
        testGroups.add(happyPath);
        testGroups.add(edgeCases);

        TestingLibrary.TestSuite testSuite = new TestingLibrary.TestSuite(testGroups, FindAndReplaceTest.class, "writeReplacedTextFile", File.class, File.class, String.class, String.class);
        runTestSuite(testSuite);
    }

    @Test
    public void writeReplacedTextFile_replacesSingleLine() throws IOException {
        final File input = new File(TEST_INPUT_FILE_PATH);

        TestingLibrary.TestGroup happyPath = new TestingLibrary.TestGroup("Happy path - replace one word with another");

        File output = new File(String.format(TEST_OUTPUT_FILE_PATH, "happy-path-single-1"));
        String searchTerm = "Serenity";
        String replaceTerm = "Peace";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        happyPath.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));

        output = new File(String.format(TEST_OUTPUT_FILE_PATH, "happy-path-single-2"));
        searchTerm = "city";
        replaceTerm = "town";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        happyPath.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));


        TestingLibrary.TestGroup edgeCases = new TestingLibrary.TestGroup("Edge cases - replace multiple words with one or more words");

        output = new File(String.format(TEST_OUTPUT_FILE_PATH, "edge-case-single-1"));
        searchTerm = "flora and fauna";
        replaceTerm = "plants and animals";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        edgeCases.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));


        List<TestingLibrary.TestGroup> testGroups = new ArrayList<>();
        testGroups.add(happyPath);
        testGroups.add(edgeCases);

        TestingLibrary.TestSuite testSuite = new TestingLibrary.TestSuite(testGroups, FindAndReplaceTest.class, "writeReplacedTextFile", File.class, File.class, String.class, String.class);
        runTestSuite(testSuite);
    }

    @Test
    public void writeReplacedTextFile_replacesNone() throws IOException {
        final File input = new File(TEST_INPUT_FILE_PATH);

        TestingLibrary.TestGroup happyPath = new TestingLibrary.TestGroup("Happy path - replace no words when searchTerm isn't found");

        File output = new File(String.format(TEST_OUTPUT_FILE_PATH, "happy-path-none-1"));
        String searchTerm = "Peace";
        String replaceTerm = "Serenity";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        happyPath.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));

        output = new File(String.format(TEST_OUTPUT_FILE_PATH, "happy-path-none-2"));
        searchTerm = "town";
        replaceTerm = "city";

        callWriteReplacedTextFile(input, output, searchTerm, replaceTerm);
        happyPath.addTest(new TestingLibrary.Test(new Object[]{input, output, searchTerm, replaceTerm}, getExpectedOutput(searchTerm, replaceTerm)));


        List<TestingLibrary.TestGroup> testGroups = new ArrayList<>();
        testGroups.add(happyPath);

        TestingLibrary.TestSuite testSuite = new TestingLibrary.TestSuite(testGroups, FindAndReplaceTest.class, "writeReplacedTextFile", File.class, File.class, String.class, String.class);
        runTestSuite(testSuite);
    }

    @Test
    public void writeReplacedTextFile_ioError_throwsException() {
        final File output = new File(String.format(TEST_OUTPUT_FILE_PATH, "ioError-1"));
        final String searchTerm = "Serenity";
        final String replaceTerm = "Peace";

        TestingLibrary.TestGroup nonFile = new TestingLibrary.TestGroup("Throws exception for non-file");
        nonFile.addTest(new TestingLibrary.Test(new Object[]{NON_FILE, output, searchTerm, replaceTerm}, IOException.class));

        List<TestingLibrary.TestGroup> testGroups = new ArrayList<>();
        testGroups.add(nonFile);

        TestingLibrary.TestSuite testSuite = new TestingLibrary.TestSuite(testGroups, FindAndReplace.class, "writeReplacedTextFile", File.class, File.class, String.class, String.class);
        runTestSuite(testSuite);
    }

    // This is the method actually being run by the test runner because this exercise is tested by what is written to the file.
    // It's named like this so the test runner prints `writeReplacedTextFile` and the parameter values sent to the actual `writeReplacedTextFile()` method.
    // A better name would have been something like `readReplacedTextFile()`
    public List<String> writeReplacedTextFile(File ignoredFile, File destinationFile, String ignoredSearchTerm, String ignoredReplacementTerm) throws IOException {
        return Files.readAllLines(destinationFile.toPath());
    }

    // The actual call to writeReplacedTextFile()
    private void callWriteReplacedTextFile(File sourceFile, File destinationFile, String searchTerm, String replacementTerm) throws IOException {
        findAndReplace.writeReplacedTextFile(sourceFile, destinationFile, searchTerm, replacementTerm);
    }

    private List<String> getExpectedOutput(String s1, String s2) {
        List<String> returnValue = new ArrayList<>();
        for (String line : srcContent) {
            returnValue.add(line.replaceAll(s1, s2));
        }
        return returnValue;
    }
}
