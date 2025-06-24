package com.techelevator;

import java.util.HashMap;
import java.util.Map;

public class WordCount {

	/*
	 * Given an array of strings, return a Map<String, Integer> with a key for
	 * each different string, with the value the number of times that string appears
	 * in the array.
	 */
	public Map<String, Integer> getCount(String[] words) {
		Map<String, Integer> output = new HashMap<>();

		if (words != null) {
			for (String word : words) {
				if (!output.containsKey(word)) {
					output.put(word, 1);
				} else {
					output.put(word, output.get(word) + 1);
				}
			}
		}

		return output;
	}
}
