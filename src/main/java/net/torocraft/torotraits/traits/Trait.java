package net.torocraft.torotraits.traits;

import java.util.Random;
import net.torocraft.torotraits.nbt.NbtField;

public class Trait {

	public static Random rand = new Random();

	@NbtField
	public Type type;

	@NbtField
	public int level;

	public Trait() {

	}

	public Trait(Type type, int level) {
		this.type = type;
		this.level = level;
	}

}
