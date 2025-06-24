package com.techelevator.farm;

import java.util.ArrayList;
import java.util.List;

public class OldMacdonald {
	public static void main(String[] args) {

		Cow bessie = new Cow("Cow","Moo");
		Chicken little = new Chicken("Chicken","Cluck");
		Sheep mary = new Sheep("Sheep", "Baa");
		FarmAnimal wookie = new FarmAnimal("Wookie","Grroog");

		List<FarmAnimal> animals = new ArrayList<>();
		animals.add(bessie);
		animals.add(little);
		animals.add(mary);

		for(FarmAnimal item : animals){
			String name = item.getName();
			String sound = item.getSound();
			// let's lay an egg
			// make sure I have a chicken
			if(item.getClass() == Chicken.class){
				((Chicken)item).layEgg();
			}

			System.out.println("Old MacDonald had a farm, ee, ay, ee, ay, oh!");
			System.out.println("And on his farm he had a " + name + ", ee, ay, ee, ay, oh!");
			System.out.println("With a " + sound + " " + sound + " here");
			System.out.println("And a " + sound + " " + sound + " there");
			System.out.println("Here a " + sound + " there a " + sound + " everywhere a " + sound + " " + sound);
			System.out.println();
		}

		System.out.println("********** Polymorphism with Interfaces **************");
		List<Singable> singables = new ArrayList<>();
		singables.add(new Tractor());
		singables.addAll(animals);

		for(Singable item : singables){
			String name = item.getName();
			String sound = item.getSound();

			System.out.println("Old MacDonald had a farm, ee, ay, ee, ay, oh!");
			System.out.println("And on his farm he had a " + name + ", ee, ay, ee, ay, oh!");
			System.out.println("With a " + sound + " " + sound + " here");
			System.out.println("And a " + sound + " " + sound + " there");
			System.out.println("Here a " + sound + " there a " + sound + " everywhere a " + sound + " " + sound);
			System.out.println();

		}

		List<Sellable> sellables = new ArrayList<>();
		sellables.add(little.layEgg());
		sellables.add(new Tractor());
		sellables.add(mary);

		for(Sellable item : sellables){
			System.out.println(item.getName() + " can by yours for low low price of $" + item.getPrice());
		}

	}


}