package com.techelevator;

import java.util.*;

public class Lecture {

	public static void main(String[] args) {
		System.out.println("####################");
		System.out.println("       LISTS");
		System.out.println("####################");
		List<String> names;
		names = new ArrayList<String>();

		names.add("Frodo");
		names.add("Samwise");
		names.add("Legolas");
		names.add("Aragorn");


		System.out.println("####################");
		System.out.println("Lists are ordered");
		System.out.println("####################");
		for(String name : names){
			System.out.println(name);
		}

		System.out.println("####################");
		System.out.println("Lists allow duplicates");
		System.out.println("####################");
		names.add("Frodo");
		for(String name : names){
			System.out.println(name);
		}

		System.out.println("####################");
		System.out.println("Lists allow elements to be inserted in the middle");
		System.out.println("####################");
		names.add(3, "Arwen");
		for(String name : names){
			System.out.println(name);
		}

		System.out.println("####################");
		System.out.println("Lists allow elements to be removed by index");
		System.out.println("####################");
		names.remove(2);
		for(String name : names){
			System.out.println(name);
		}

		System.out.println("####################");
		System.out.println("Find out if something is already in the List");
		System.out.println("####################");
		if(names.contains("Aragorn")){
			System.out.println("Aragorn will save us from the Nazgul");
		}else {
			System.out.println("Frodo is about to get stabbed");
		}


		System.out.println("####################");
		System.out.println("Find index of item in List");
		System.out.println("####################");
		int indexOfAragorn = names.indexOf("Aragorn");
		System.out.println("The index of Aragorn is: " + indexOfAragorn);

		System.out.println("####################");
		System.out.println("Lists can be turned into an array");
		System.out.println("####################");
		String[] namesAsArray = names.toArray(new String[0]);
		for(int i = 0; i < namesAsArray.length; i++){
			System.out.println(namesAsArray[i]);
		}

		System.out.println("####################");
		System.out.println("Lists can be sorted");
		System.out.println("####################");
		Collections.sort(names);
		for(String name : names){
			System.out.println(name);
		}
		System.out.println("####################");
		System.out.println("Lists can be reversed too");
		System.out.println("####################");
		Collections.reverse(names);
		for(String name : names){
			System.out.println(name);
		}
		System.out.println("####################");
		System.out.println("       QUEUE");
		System.out.println("####################");
		System.out.println();
		Queue<String> petQueue = new LinkedList<>();
		petQueue.offer("Max");
		petQueue.offer("Josh");
		petQueue.offer("Jared");
		petQueue.offer("Nova");
		//this goes until all items are processed out of the queue
		while(petQueue.size() > 0){
			//what is the size of the queue
			System.out.println(petQueue.size());
			//grab and item
			String pet = petQueue.poll();
			System.out.println(pet);
			//lets look at the next item to come out of the queue
			System.out.println("What is next? " + petQueue.peek());
		}


		System.out.println("####################");
		System.out.println("       STACK");
		System.out.println("####################");
		System.out.println();
		Stack<Integer> numbers = new Stack<Integer>();
		numbers.push(1);
		numbers.push(2);
		numbers.push(3);
		numbers.push(4);
		numbers.push(5);

		while(numbers.size() > 0){
			int currentNumber = numbers.pop();
			System.out.println(currentNumber);
		}
	}
}
