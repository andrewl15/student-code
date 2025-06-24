package com.techelevator.farm;

public class OldMacdonald {
	public static void main(String[] args) {

		Singable[] singables =
				new Singable[] {new JerseyCow(), new Chicken(), new Pig(), new Tractor(), new Cat("Oreo"), new Calico(), new Sheep()};

        for (Singable singable : singables) {
			if(singable.getClass() == Cat.class){
				System.out.println(((Cat) singable).eat());
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

		Sellable[] sellables = new Sellable[] {new JerseyCow(), new Pig(), new Egg()};

		for (Sellable sellable : sellables) {
			System.out.println("Step right up and get your " + sellable.getName());
			System.out.println("Only $" + sellable.getPrice());

			//playing around with toString
			//if(sellable.getClass() == JerseyCow.class){

				//println actually calls the twoString method on the object
				System.out.println(sellable);
			//}
		}
	}
}