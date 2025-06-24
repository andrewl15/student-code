package com.techelevator;

import java.util.*;

public class Lecture {

	public static void main(String[] args) {
		System.out.println("####################");
		System.out.println("       LISTS");
		System.out.println("####################");
		// Declare a list
		List<String> names;
		// initialize that list
		names = new ArrayList<>();
		// let's add some items
		names.add("Frodo");
		names.add("Bilbo");
		names.add("Samwise");
		names.add("Gandalf");
		names.add("Gimli");
		names.add("Legolas");

		System.out.println("####################");
		System.out.println("Lists are ordered");
		System.out.println("####################");
		// print out our list
		for(int i = 0; i < names.size();i++){
			System.out.println(names.get(i));
		}


		System.out.println("####################");
		System.out.println("Lists allow duplicates");
		System.out.println("####################");
		names.add("Frodo");
		for(int i = 0; i < names.size();i++){
			System.out.println(i + " -- " + names.get(i));
		}
		System.out.println("####################");
		System.out.println("Lists allow elements to be inserted in the middle");
		System.out.println("####################");

		names.add(3,"Pippin");
		for(int i = 0; i < names.size();i++){
			System.out.println(i + " -- " + names.get(i));
		}

		System.out.println("####################");
		System.out.println("Lists allow elements to be removed by index");
		System.out.println("####################");
		names.remove(5);

		// removes the first occurrance of this object in the list
		names.remove("Frodo");

		System.out.println("####################");
		System.out.println("Find out if something is already in the List");
		System.out.println("####################");

		boolean hasGandalf = names.contains("Gandalf");
		if(hasGandalf){
			System.out.println("Yep, he's here, we haven't met the Balrog yet.");
		} else {
			System.out.println("Where did he go?");
		}

		System.out.println("####################");
		System.out.println("Find index of item in List");
		System.out.println("####################");

		// let's find out the index of Legolas
		int indexOfLegolas = names.indexOf("Legolas");
		System.out.println(indexOfLegolas);
		int indexOfSauron = names.indexOf("Sauron");
		System.out.println(indexOfSauron);
		names.add(0,"Frodo");
		int indexOfFrodo = names.indexOf("Frodo");
		System.out.println(indexOfFrodo);
		System.out.println(names.lastIndexOf("Frodo"));

		System.out.println("####################");
		System.out.println("Lists can be turned into an array");
		System.out.println("####################");
		// manipulating arrays is more efficient
		String[] namesAsArray = names.toArray(new String[0]);
		for (int i = 0; i < namesAsArray.length; i++) {
			System.out.println(namesAsArray[i]);
		}

		System.out.println("####################");
		System.out.println("Lists can be sorted");
		System.out.println("####################");
		Collections.sort(names);
		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}

		System.out.println("####################");
		System.out.println("Lists can be shuffled");
		System.out.println("####################");

		Collections.shuffle(names);
		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}

		System.out.println("####################");
		System.out.println("Lists can be reversed too");
		System.out.println("####################");

		Collections.reverse(names);
		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i) + " is at index " + i);
		}

		System.out.println("####################");
		System.out.println("       FOREACH");
		System.out.println("####################");
		System.out.println();

		for(String item : names){
			System.out.println(item);
		}

		// can't manipulate the list you are looping
//		for(String item : names){
//			if(item.equals("Gandalf")){
//				names.remove(item);
//			}
//		}

		// make a copy of the ArrayList and loop through that item
		List<String> copyOfNames = new ArrayList<>(names);
		for(String item : copyOfNames){
			if(item.equals("Gandalf")){
				names.remove(item);
			}
		}
		// let's make a list of pet names
		Scanner keyboard = new Scanner(System.in);
		// Create a list to hold the pet names
		List<String> petNames = new ArrayList<>();
		String userInput = "";
		do{
			System.out.print("Enter a pet name or Q to quit: ");
			// grab the input from the user
			userInput = keyboard.nextLine();
			if(!userInput.toUpperCase().equals("Q")){
				petNames.add(userInput);
			}

		}while(!userInput.toUpperCase().equals("Q"));
		// close the keyboard
		keyboard.close();
		// let's alphabetize our pets
		Collections.sort(petNames);
		for(String pet : petNames){
			System.out.println(pet);
		}

		System.out.println("####################");
		System.out.println("       QUEUE");
		System.out.println("####################");
		System.out.println();

		Queue<String> petQueue = new LinkedList<>();
		petQueue.offer("Max");
		petQueue.offer("Charlie");
		petQueue.offer("Hermie");
		petQueue.offer("Daisy");
		petQueue.offer("Nova");
		petQueue.offer("Peach");
		// this goes until all the items are processed out of the queue.
		while(petQueue.size() > 0){
			// what's the size of the queue
			System.out.print(petQueue.size() + " -- ");
			// grab an item
			String cutie = petQueue.poll();
			System.out.println(cutie);
			// let's look at the next item to come out of the queue without removing it
			System.out.println("What's next? " + petQueue.peek());
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
		numbers.push(6);

		while(numbers.size() > 0){
			int currentNumber = numbers.pop();
			System.out.println(currentNumber);
		}
	}
}
