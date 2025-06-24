package com.techelevator;

import com.techelevator.testing.TestingLibrary;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.techelevator.testing.TestingLibrary.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class WordSearchTest {

    private static final File TEST_FILE = new File("src/test/resources/hidden-cafe.txt");
    private static final File NON_EXISTENT_FILE = new File("non-existent-file.txt");
    private static final File NON_FILE = new File ("src/test/resources/");

    @Test
    public void getMatchingLines_caseSensitiveSearchTerms() {
        boolean isCaseSensitive = true;
        boolean includeLineNumbers = false;

        String searchTerm_multipleLines = "outside";
        String searchTerm_singleLine = "The café";
        String searchTerm_zeroLines = "baristas";

        List<String> expected_multipleLines = List.of(
                "to the symphony of the café. The world outside may be rushing, but within these",
                "each bite, letting the outside world melt away. The hidden gem becomes a");
        List<String> expected_singleLine = List.of("that range from acoustic melodies to poetry readings. The café transforms into");
        List<String> expected_zeroLines = new ArrayList<>();

        Object[] parameters = new Object[]{TEST_FILE, searchTerm_multipleLines, isCaseSensitive, includeLineNumbers};
        TestGroup multipleLines = new TestGroup("Returns multiple lines with case-sensitive search term");
        multipleLines.addTest(new TestingLibrary.Test(parameters, expected_multipleLines));

        parameters = new Object[]{TEST_FILE, searchTerm_singleLine, isCaseSensitive, includeLineNumbers};
        TestGroup singleLine = new TestGroup("Returns single line with case-sensitive search term");
        singleLine.addTest(new TestingLibrary.Test(parameters, expected_singleLine));

        parameters = new Object[]{TEST_FILE, searchTerm_zeroLines, isCaseSensitive, includeLineNumbers};
        TestGroup zeroLines = new TestGroup("Returns zero lines with case-sensitive search term");
        zeroLines.addTest(new TestingLibrary.Test(parameters, expected_zeroLines));

        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(multipleLines);
        testGroups.add(singleLine);
        testGroups.add(zeroLines);

        TestSuite testSuite = new TestSuite(testGroups, WordSearch.class, "getMatchingLines", File.class, String.class, boolean.class, boolean.class);
        runTestSuite(testSuite);
    }

    @Test
    public void getMatchingLines_caseInsensitiveSearchTerms() {
        boolean isCaseSensitive = false;
        boolean includeLineNumbers = false;

        String searchTerm_multipleLines = "outside";
        String searchTerm_singleLine = "love for art and coffee";
        String searchTerm_zeroLines = "espresso";

        List<String> expected_multipleLines = List.of(
                "Outside the large windows, raindrops tap a soothing rhythm on the glass, adding",
                "to the symphony of the café. The world outside may be rushing, but within these",
                "each bite, letting the outside world melt away. The hidden gem becomes a");
        List<String> expected_singleLine = List.of("where the love for art and coffee intertwines seamlessly.");
        List<String> expected_zeroLines = new ArrayList<>();

        Object[] parameters = new Object[]{TEST_FILE, searchTerm_multipleLines, isCaseSensitive, includeLineNumbers};
        TestGroup multipleLines = new TestGroup("Returns multiple lines with case-insensitive search term");
        multipleLines.addTest(new TestingLibrary.Test(parameters, expected_multipleLines));

        parameters = new Object[]{TEST_FILE, searchTerm_singleLine, isCaseSensitive, includeLineNumbers};
        TestGroup singleLine = new TestGroup("Returns single line with case-insensitive search term");
        singleLine.addTest(new TestingLibrary.Test(parameters, expected_singleLine));

        parameters = new Object[]{TEST_FILE, searchTerm_zeroLines, isCaseSensitive, includeLineNumbers};
        TestGroup zeroLines = new TestGroup("Returns zero lines with case-insensitive search term");
        zeroLines.addTest(new TestingLibrary.Test(parameters, expected_zeroLines));

        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(multipleLines);
        testGroups.add(singleLine);
        testGroups.add(zeroLines);

        TestSuite testSuite = new TestSuite(testGroups, WordSearch.class, "getMatchingLines", File.class, String.class, boolean.class, boolean.class);
        runTestSuite(testSuite);
    }

    @Test
    public void getMatchingLines_ioError_throwsException() {
        String searchTerm = "";
        boolean isCaseSensitive = false;
        boolean includeLineNumbers = false;

        Object[] parameters = new Object[]{NON_EXISTENT_FILE, searchTerm, isCaseSensitive, includeLineNumbers};
        TestGroup nonExistent = new TestGroup("Throws exception for non-existent file");
        nonExistent.addTest(new TestingLibrary.Test(parameters, IOException.class));

        parameters = new Object[]{NON_FILE, searchTerm, isCaseSensitive, includeLineNumbers};
        TestGroup nonFile = new TestGroup("Throws exception for non-file");
        nonFile.addTest(new TestingLibrary.Test(parameters, IOException.class));

        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(nonExistent);
        testGroups.add(nonFile);
        TestSuite testSuite = new TestSuite(testGroups, WordSearch.class, "getMatchingLines", File.class, String.class, boolean.class, boolean.class);
        runTestSuite(testSuite);
    }

    @Test
    public void getMatchingLines_lineNumbers() {
        boolean isCaseSensitive = false;
        boolean includeLineNumbers = true;

        String searchTerm_multipleLines = "outside";
        String searchTerm_singleLine = "intertwines";

        List<String> expected_multipleLines = List.of(
                "17) Outside the large windows, raindrops tap a soothing rhythm on the glass, adding",
                "18) to the symphony of the café. The world outside may be rushing, but within these",
                "20) each bite, letting the outside world melt away. The hidden gem becomes a");
        List<String> expected_singleLine = List.of("27) where the love for art and coffee intertwines seamlessly.");

        Object[] parameters = new Object[]{TEST_FILE, searchTerm_multipleLines, isCaseSensitive, includeLineNumbers};
        TestGroup multipleLines = new TestGroup("Returns correct line numbers for multiple lines");
        multipleLines.addTest(new TestingLibrary.Test(parameters, expected_multipleLines));

        parameters = new Object[]{TEST_FILE, searchTerm_singleLine, isCaseSensitive, includeLineNumbers};
        TestGroup singleLine = new TestGroup("Returns correct line numbers for single line");
        singleLine.addTest(new TestingLibrary.Test(parameters, expected_singleLine));

        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(multipleLines);
        testGroups.add(singleLine);

        TestSuite testSuite = new TestSuite(testGroups, WordSearch.class, "getMatchingLines", File.class, String.class, boolean.class, boolean.class);
        runTestSuite(testSuite);
    }

    @Test
    public void isFileValid_tests() {
        TestGroup validFile = new TestGroup("Testing with a valid file");
        validFile.addTest(new TestingLibrary.Test(new Object[]{TEST_FILE.getAbsolutePath()}, true));

        TestGroup invalidFile = new TestGroup("Testing with a non-existent file");
        invalidFile.addTest(new TestingLibrary.Test(new Object[]{NON_EXISTENT_FILE.getAbsolutePath()}, false));

        TestGroup nonFile = new TestGroup("Testing with a non-file");
        nonFile.addTest(new TestingLibrary.Test(new Object[]{NON_FILE.getAbsolutePath()}, false));

        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(validFile);
        testGroups.add(invalidFile);
        testGroups.add(nonFile);

        TestSuite testSuite = new TestSuite(testGroups, WordSearch.class, "isFileValid", String.class);
        runTestSuite(testSuite);
    }
}
