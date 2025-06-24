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
		//declare our variable to hold a map of string
		Map<String, String> nameToZip;
		//initialize the map
		nameToZip = new HashMap<>();
		//add some values
		nameToZip.put("Henry", "15229");
		nameToZip.put("Andrew", "19810");
		nameToZip.put("Peter", "15237");

		//get values out of the map
		System.out.println("Andrew lives in zipcode: " + nameToZip.get("Andrew"));

		Set<String> mapKeys = nameToZip.keySet();
		System.out.println("Please type in the name of the person whose zip code you want: ");
		for(String item: mapKeys){
			System.out.println(item);
		}
		for(String item: mapKeys){
			System.out.println(item + " lives in the zip " + nameToZip.get(item));
		}
		System.out.println();
		Collection<String> values = nameToZip.values();
		for(String item: values){
			System.out.println(item);
		}
		System.out.println();
		Set<Map.Entry<String, String>> entrySet = nameToZip.entrySet();
		for(Map.Entry<String, String> entry: entrySet){
			System.out.println(entry.getKey() + " is the key for " + entry.getValue());
		}
		if(nameToZip.containsKey("Batman")){
			System.out.println(nameToZip.get("Batman"));
		}else{
			System.out.println("We do not have a record of Batman");
		}
		nameToZip.put("Andrew" , "19811");
		System.out.println(nameToZip.get("Andrew"));


		System.out.println("####################");
		System.out.println("        LINKED HASH MAPS");
		System.out.println("####################");
		System.out.println();

		Map<Integer, String> penguinLinked = new LinkedHashMap<>();
		penguinLinked.put(71, "Evgeni Malkin");
		penguinLinked.put(17, "Brian Rust");
		penguinLinked.put(59, "Jake Guentzel");

		for(Integer key : penguinLinked.keySet()){
			System.out.println(penguinLinked.get(key) + " is number " + key);
		}
		penguinLinked.remove(59);

		System.out.println();
		System.out.println("******************** TREE MAP *******************");
		Map<Integer, String> penquinsTree = new TreeMap<>();

		penquinsTree.put(71, "Evgeni Malkin");
		penquinsTree.put(17, "Brian Rust");
		penquinsTree.put(59, "Jake Guentzel");

		System.out.println();
		System.out.println("******************** HASH SET *******************");
		Set<Integer> playerNumbers = new HashSet<>();
		playerNumbers.add(17);
		playerNumbers.add(12);
		playerNumbers.add(87);
		playerNumbers.add(53);
		playerNumbers.add(12);
		playerNumbers.add(8);

		if(playerNumbers.add(87)){
			System.out.println("No problem, you get number 87");
		}else{
			System.out.println("Sorry, 87 ios taken");

		}

		for(Integer num : playerNumbers){
			System.out.println(num);
		}


		System.out.println();
		System.out.println("******************** BIG DECIMAL *******************");
		//variables with the same values as doubles
		double dOne = 3.47;
		double dTwo = 3.17;
		//
		BigDecimal thingOne = new BigDecimal("3.47");
		BigDecimal thingTwo = new BigDecimal("3.17");
		BigDecimal thingThree = new BigDecimal("3.17");

		//addition
		BigDecimal sum = thingOne.add(thingTwo);
		System.out.println("Big Decimal Sum");
		System.out.println(sum);
		System.out.println("double sum");
		System.out.println(dOne + dTwo);

		//subtraction
		BigDecimal difference = thingOne.subtract(thingTwo);
		System.out.println("Big Decimal Difference");
		System.out.println(difference);
		System.out.println("double minus");
		System.out.println(dOne - dTwo);

		//division
		BigDecimal quotient = thingOne.divide(thingTwo, 4, RoundingMode.FLOOR);
		//											Decimal points      Round the rest down
		System.out.println("Big Decimal division " + quotient);


		//multiplication
		BigDecimal product = thingOne.multiply(thingTwo);
		System.out.println(product);

		int oneVTwo = thingOne.compareTo(thingTwo);
		int twoVOne = thingTwo.compareTo(thingOne);
		int twoVThree = thingTwo.compareTo(thingThree);

		System.out.println(oneVTwo);
		System.out.println(twoVOne);
		System.out.println(twoVThree);

	}

}
