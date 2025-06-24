package com.techelevator.farm;

public class OldMacdonald {
	public static void main(String[] args) {

		Singable[] singables =
				new Singable[] {new Jersey(), new Chicken(), new Pig(), new Tractor(),
						new Cat("Rumtugtigger"),new Calico(), new Sheep()};

		// Make sure the singable at index 1 is a Chicken
		if(singables[1].getClass() == Chicken.class){
			Chicken temp = ((Chicken)singables[1]);
			System.out.println(temp.eat());
			temp.sleep();
		}

		for (Singable singable : singables) {
			// if it is a cat, have it take a cat nap
			if(singable.getClass() == Cat.class){
				((Cat)singable).sleep();
			}
			String name = singable.getName();
			String sound = singable.getSound();
			System.out.println("Old MacDonald had a farm, ee, ay, ee, ay, oh!");
			System.out.println("And on his farm he had a " + name
					+ ", ee, ay, ee, ay, oh!");
			System.out.println("With a " + sound + " " + sound + " here");
			System.out.println("And a " + sound + " " + sound + " there");
			System.out.println("Here a " + sound + " there a " + sound
					+ " everywhere a " + sound + " " + sound);
			System.out.println();
		}

		Sellable[] sellables = new Sellable[] {new Jersey(), new Pig(), new Egg()};

		for (Sellable sellable : sellables) {

			System.out.println("Step right up and get your " + sellable.getName());
			System.out.println("Only $" + sellable.getPrice());

			// let's play around with toString
//			if(sellable.getClass() == Jersey.class){

				// println calls the toString method on the object
				System.out.println(sellable);
//			}
		}
	}
}