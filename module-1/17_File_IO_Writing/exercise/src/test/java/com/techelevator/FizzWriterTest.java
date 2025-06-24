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

import static com.techelevator.testing.TestingLibrary.*;

public class FizzWriterTest {

    private static final String TEST_FILE_PATH = "src/test/resources/fizzbuzz-%s.txt";
    private static final File NON_FILE = new File ("src/test/resources/");
    FizzWriter fizzWriter;

    @BeforeAll
    public static void cleanupTestFiles() {
        String regex = "fizzbuzz-.+\\.txt$";
        File dir = new File(TEST_FILE_PATH).getParentFile();
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
        fizzWriter = new FizzWriter();
    }

    @Test
    public void writeFizzBuzzFile_startAndEndNumbers() throws IOException {
        final String fizzValue = "fizz";
        final String buzzValue = "buzz";

        TestGroup happyPath = new TestGroup("Happy path values");

        // happy path from 1-15
        int startNumber = 1;
        int endNumber = 15;
        File file = new File(String.format(TEST_FILE_PATH, "happy-number-1"));
        FizzWriterParams params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        happyPath.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // happy path from 1-100
        endNumber = 100;
        file = new File(String.format(TEST_FILE_PATH, "happy-number-2"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        happyPath.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));


        TestGroup edgeCases = new TestGroup("Edge case values");

        // not starting at 1, ending on non-round number
        startNumber = 2;
        endNumber = 23;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-1"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // only fizz
        startNumber = 11;
        endNumber = 13;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-2"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // only buzz
        startNumber = 4;
        endNumber = 5;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-3"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // only fizz and buzz
        startNumber = 34;
        endNumber = 36;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-4"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // only fizzbuzz
        startNumber = 14;
        endNumber = 16;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-5"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // nothing
        startNumber = 7;
        endNumber = 8;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-6"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // negative only
        startNumber = -19;
        endNumber = -2;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-7"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // negative and positive
        startNumber = -16;
        endNumber = 16;
        file = new File(String.format(TEST_FILE_PATH, "edge-number-8"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));


        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(happyPath);
        testGroups.add(edgeCases);

        TestSuite testSuite = new TestSuite(testGroups, FizzWriterTest.class, "writeFizzBuzzFile", File.class, int.class, int.class, String.class, String.class);
        runTestSuite(testSuite);
    }

    @Test
    public void writeFizzBuzzFile_fizzAndBuzzReplacement() throws IOException {
        final int startNumber = 1;
        final int endNumber = 15;

        TestGroup happyPath = new TestGroup("Happy path values");

        // fizz, buzz
        String fizzValue = "fizz";
        String buzzValue = "buzz";
        File file = new File(String.format(TEST_FILE_PATH, "happy-replacement-1"));
        FizzWriterParams params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        happyPath.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // Fizz, Buzz
        fizzValue = "Fizz";
        buzzValue = "Buzz";
        file = new File(String.format(TEST_FILE_PATH, "happy-replacement-2"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        happyPath.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));


        TestGroup edgeCases = new TestGroup("Edge case values");

        // fix, bus
        fizzValue = "fix";
        buzzValue = "bus";
        file = new File(String.format(TEST_FILE_PATH, "edge-replacement-1"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // apple, banana
        fizzValue = "apple";
        buzzValue = "banana";
        file = new File(String.format(TEST_FILE_PATH, "edge-replacement-2"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));

        // CaMeL, cAsE
        fizzValue = "CaMeL";
        buzzValue = "cAsE";
        file = new File(String.format(TEST_FILE_PATH, "edge-replacement-3"));
        params = new FizzWriterParams(file, startNumber, endNumber, fizzValue, buzzValue);

        callWriteFizzBuzzFile(params);
        edgeCases.addTest(new TestingLibrary.Test(params.toArray(), getExpectedOutput(params)));


        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(happyPath);
        testGroups.add(edgeCases);

        TestSuite testSuite = new TestSuite(testGroups, FizzWriterTest.class, "writeFizzBuzzFile", File.class, int.class, int.class, String.class, String.class);
        runTestSuite(testSuite);
    }

    @Test
    public void writeFizzBuzzFile_ioError_throwsException() {
        final int startNumber = 1;
        final int endNumber = 10;
        final String fizzValue = "fizz";
        final String buzzValue = "buzz";

        Object[] parameters = new Object[]{NON_FILE, startNumber, endNumber, fizzValue, buzzValue};
        TestGroup nonFile = new TestGroup("Throws exception for non-file");
        nonFile.addTest(new TestingLibrary.Test(parameters, IOException.class));

        List<TestGroup> testGroups = new ArrayList<>();
        testGroups.add(nonFile);

        TestSuite testSuite = new TestSuite(testGroups, FizzWriter.class, "writeFizzBuzzFile", File.class, int.class, int.class, String.class, String.class);
        runTestSuite(testSuite);
    }

    private List<String> getExpectedOutput(FizzWriterParams params) {

        List<String> values = List.of("-19","F","-17","-16","FB","-14","-13","F","-11","B","F","-8","-7","F","B","-4","F","-2","-1","FB","1","2","F","4","B","F","7","8","F","B","11","F","13","14","FB","16","17","F","19","B","F","22","23","F","B","26","F","28","29","FB","31","32","F","34","B","F","37","38","F","B","41","F","43","44","FB","46","47","F","49","B","F","52","53","F","B","56","F","58","59","FB","61","62","F","64","B","F","67","68","F","B","71","F","73","74","FB","76","77","F","79","B","F","82","83","F","B","86","F","88","89","FB","91","92","F","94","B","F","97","98","F","B","101","F","103","104","FB","106","107","F","109","B","F","112","113","F","B","116","F","118","119","FB","121","122","F","124","B","F","127","128","F","B","131","F","133","134","FB","136","137","F","139","B","F","142","143","F","B","146","F","148","149","FB","151","152","F","154","B","F","157","158","F","B","161","F","163","164","FB","166","167","F","169","B","F","172","173","F","B","176","F","178","179","FB","181","182","F","184","B","F","187","188","F","B","191","F","193","194","FB","196","197","F","199","B","F","202","203","F","B","206","F","208","209","FB","211","212","F","214","B","F","217","218","F","B","221","F","223","224","FB","226","227","F","229","B","F","232","233","F","B","236","F","238","239","FB","241","242","F","244","B","F","247","248","F","B","251","F","253","254","FB","256","257","F","259","B","F","262","263","F","B","266","F","268","269","FB","271","272","F","274","B","F","277","278","F","B","281","F","283","284","FB","286","287","F","289","B","F","292","293","F","B","296","F","298","299","FB");
        List<String> returnValues = new ArrayList<>();
        int zeroIndex = values.indexOf("1") - 1; // because zero is divisible by 3 and 5
        int startIndex = zeroIndex + params.getStart();
        int endIndex = zeroIndex + params.getEnd();
        for (int i = startIndex; i <= endIndex; i++) {
            String value = values.get(i);
            String newValue = "";

            if (value.contains("F")) {
                newValue = params.getFizz();
            }
            if (value.contains("B")) {
                newValue += params.getBuzz(); // `+=` to handle buzz and fizzbuzz
            }
            if (newValue.isEmpty()) {
                newValue = value;
            }
            returnValues.add(newValue);
        }

        return returnValues;
    }

    // This is the method actually being run by the test runner because this exercise is tested by what is written to the file.
    // It's named like this so the test runner prints `writeFizzBuzzFile` and the parameter values sent to the actual `writeFizzBuzzFile()` method.
    // A better name would have been something like `readFizzBuzzFile()`
    public List<String> writeFizzBuzzFile(File file, int ignoredStartNumber, int ignoredEndNumber, String ignoredFizzValue, String ignoredBuzzValue) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // The actual call to writeFizzBuzzFile(), expanding the FizzWriterParams object
    private void callWriteFizzBuzzFile(FizzWriterParams params) throws IOException {
        fizzWriter.writeFizzBuzzFile(params.getFile(), params.getStart(), params.getEnd(), params.getFizz(), params.getBuzz());
    }

}
