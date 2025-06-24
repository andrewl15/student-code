package com.techelevator.farm;

import java.util.ArrayList;
import java.util.List;

public class OldMacdonald {
	public static void main(String[] args) {

		Cow bessie = new Cow("Cow","Moo");
		Sheep mary = new Sheep("Sheep","Baa");
		Chicken little = new Chicken("Chicken","Cluck");

		List<FarmAnimal> animals = new ArrayList<>();
		animals.add(bessie);
		animals.add(mary);
		animals.add(little);

		for(FarmAnimal animal : animals){
			String name = animal.getName();
			String sound = animal.getSound();

			//lets lay an egg
			//make sure I have a chicken class
			if(animal.getClass() == Chicken.class){
				((Chicken)animal).layEgg();
			}
			System.out.println("Old MacDonald had a farm, ee, ay, ee, ay, oh!");
			System.out.println("And on his farm he had a " + name + ", ee, ay, ee, ay, oh!");
			System.out.println("With a " + sound + " " + sound + " here");
			System.out.println("And a " + sound + " " + sound + " there");
			System.out.println("Here a " + sound + " there a " + sound + " everywhere a " + sound + " " + sound);
			System.out.println();
		}
		System.out.println("********************** Polymorphism with Interfaces ***********************");
		List<Singable> singables = new ArrayList<>();
		singables.add(new Tractor());
		singables.add(bessie);

		for(Singable song: singables){
			String name = song.getName();
			String sound = song.getSound();

			System.out.println("Old MacDonald had a farm, ee, ay, ee, ay, oh!");
			System.out.println("And on his farm he had a " + name + ", ee, ay, ee, ay, oh!");
			System.out.println("With a " + sound + " " + sound + " here");
			System.out.println("And a " + sound + " " + sound + " there");
			System.out.println("Here a " + sound + " there a " + sound + " everywhere a " + sound + " " + sound);
			System.out.println();
		}

		List<Sellable> sellables = new ArrayList<>();
		sellables.add(little.layEgg());
		sellables.add(mary);
		for(Sellable item: sellables){
			System.out.println(item.getName() + " can be yours for the price of " + item.getPrice());
		}
	}

}

