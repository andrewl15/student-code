package com.techelevator;

import java.util.List;

public class Finder {

	/*
	 * Given a List of Integers, return the largest value. If the list is empty, return null.
	 */
	public Integer findLargest(List<Integer> integerList) {
		if(integerList == null){
			return null;
		}
		if (integerList.isEmpty()) {
			return null;
		}

		Integer largestInt = null;
		for (Integer currentInteger : integerList) {
			if(currentInteger == null){
				continue;
			}
			else if (largestInt == null || currentInteger > largestInt) {
				largestInt = currentInteger;
			}
		}
		return largestInt;
	}

}
