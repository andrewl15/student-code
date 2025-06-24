package com.techelevator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Lecture {

	public static void main(String[] args) {

		System.out.println("####################");
		System.out.println("        MAPS");
		System.out.println("####################");
		System.out.println();

		// declare our variable to hold a Map of String, String
		Map<String,String> nameToZip;
		// initialize the map
		nameToZip = new HashMap<String,String>();
		// add some values
		nameToZip.put("Henry","15601");
		nameToZip.put("Max","15229-2001");
		nameToZip.put("Kathy","98004");
		nameToZip.put("Mimi","15237");

		// get values out of the map
		System.out.println("Kathy lives in zipcode " + nameToZip.get("Kathy"));

		// What if we don't know the keys?
		// we can ask the map for them
		Set<String> mapKeys = nameToZip.keySet();
		System.out.println("Please type the name of the person whose zip code you want: ");
		for(String item : mapKeys){
			System.out.println(item);
		}
		System.out.println();
		for(String item : mapKeys){
			System.out.println(item + " lives in the zip " + nameToZip.get(item));
		}
		System.out.println();
		// values only, no association with a key
		Collection<String> values = nameToZip.values();
		for(String item : values){
			System.out.println(item);
		}
		System.out.println();
		// loop through and get values and keys
		Set<Map.Entry<String, String>> entrySet = nameToZip.entrySet();
		for(Map.Entry<String,String> entry : entrySet){
			System.out.println(entry.getKey() + " is in zip " + entry.getValue());
		}
		// check if the key is there
		if(nameToZip.containsKey("Batman")){
			System.out.println(nameToZip.get("Batman"));
		} else {
			System.out.println("I don't know Batman's zip");
		}
		// keys must be unique
		nameToZip.put("Henry","90210");
		// put will add the key and value if the key isn't there.
		// if the key is there, it will overwrite the value.
		System.out.println(nameToZip.get("Henry"));
		if(nameToZip.containsKey("Henry")){
			nameToZip.put("Henry_02","15601");
		}

		System.out.println("********************* LinkedHashMap *****************");
		Map<Integer, String> penguinsLinked = new LinkedHashMap<>();
		penguinsLinked.put(71, "Evgeni Malkin");
		penguinsLinked.put(17,"Brian Rust");
		penguinsLinked.put(59,"Jake Guentzel");
		penguinsLinked.put(87,"Sydney Crosby");

		for(Integer key : penguinsLinked.keySet()){
			System.out.println(penguinsLinked.get(key) + " is number " + key);
		}

		penguinsLinked.remove(17);

		System.out.println();
		System.out.println("*********************** TreeMap ************************");
		Map<Integer, String> penguinsTree = new TreeMap<>();
		penguinsTree.put(71, "Evgeni Malkin");
		penguinsTree.put(17,"Brian Rust");
		penguinsTree.put(59, "Jake Guentzel");
		penguinsTree.put(87, "Sydney Crosby");

		System.out.println();
		System.out.println("********************** HashSet **********************");
		Set<Integer> playerNumbers = new HashSet<>();
		playerNumbers.add(17);
		playerNumbers.add(59);
		playerNumbers.add(42);
		playerNumbers.add(87);
		playerNumbers.add(42);

		if(playerNumbers.add(87)){
			System.out.println("No problem, you get nuber 87");
		} else {
			System.out.println("Sorry, 87 is taken");
		}
		for(Integer num : playerNumbers){
			System.out.println(num);
		}

		System.out.println();
		System.out.println("********************** BigDecimal *********************");
		// set up variable with the same values as doubles
		double dOne = 3.47;
		double dTwo = 3.17;
		// best practice is to use a string for the number as a parameter to BigDecimal constructor
		BigDecimal thingOne = new BigDecimal("3.47");
		BigDecimal thingTwo = new BigDecimal("3.17");
		BigDecimal thingThree = new BigDecimal("3.17");

		// addition
		BigDecimal sum = thingOne.add(thingTwo);
		System.out.println("Big Decimal Sum " + sum);
		System.out.println("Double Sum " + (dOne + dTwo));

		// subtraction
		BigDecimal difference = thingOne.subtract(thingTwo);
		System.out.println("Big Decimal Subtraction " + difference);
		System.out.println(thingTwo.subtract(thingOne));
		System.out.println("Double subtraction " + (dOne - dTwo));

		// division
		//                                           vvv decimal points
		BigDecimal quotient = thingOne.divide(thingTwo,4, RoundingMode.FLOOR);
		//                                                ^^^^^^^^^^^^^^^^^ round the rest down
		System.out.println("BigDecimal division " + quotient);

		// multiplication
		BigDecimal product = thingOne.multiply(thingTwo);
		System.out.println("BigDecimal multiplication " + product);

		int oneVTwo = thingOne.compareTo(thingTwo);
		int twoVOne = thingTwo.compareTo(thingOne);
		int twoVThree = thingTwo.compareTo(thingThree);
		System.out.println("ThingOne to ThingTwo " + oneVTwo);
		System.out.println("ThingTwo to ThingOne " + twoVOne);
		System.out.println("ThingTwo to ThingThree " + twoVThree);
		if(thingOne.compareTo(thingTwo) > 0){
			System.out.println("ThingOne is larger");
		} else if(thingOne.compareTo(thingTwo) < 0) {
			System.out.println("ThingTwo is larger");
		} else {
			System.out.println("They are the same value.");
		}
	}

}
