package com.techelevator.farm;

//the word abstract means this class cannot be instantiated
//but you can use it for inheritance
public abstract class FarmAnimal implements Singable {
	private String name;
	private String sound;
	private boolean isAsleep;

	public FarmAnimal(String name, String sound) {
		this.name = name;
		this.sound = sound;
	}

	public String getName( ) {
		return name;
	}
	public final String getSound( ) {
		if(isAsleep){
			return "zzzzzzz";
		}
		return sound;
	}

	public void sleep(){
		isAsleep = true;
	}

	public void wakeUp(){
		isAsleep = false;
	}

	//abstract methods MUST be overridden by the subclass
	public abstract String eat();
}