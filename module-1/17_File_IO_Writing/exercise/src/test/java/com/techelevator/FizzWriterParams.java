package com.techelevator;

import java.io.File;

/**
 * The purpose of this class is to encapsulate the different parameters being sent to FizzWriter.writeFizzBuzzFile().
 * This object can then be used to pass to the test runner, generate the expected test values, and make the test setup a little cleaner.
 */
public class FizzWriterParams {
    private final File file;
    private final int start;
    private final int end;
    private final String fizz;
    private final String buzz;

    public FizzWriterParams(File file, int start, int end, String fizz, String buzz) {
        this.file = file;
        this.start = start;
        this.end = end;
        this.fizz = fizz;
        this.buzz = buzz;
    }

    public File getFile() {
        return file;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getFizz() {
        return fizz;
    }

    public String getBuzz() {
        return buzz;
    }

    public Object[] toArray() {
        return new Object[]{file, start, end, fizz, buzz};
    }
}
