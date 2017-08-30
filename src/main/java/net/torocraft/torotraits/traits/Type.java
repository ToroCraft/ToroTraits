package net.torocraft.torotraits.traits;

import java.util.Arrays;

public enum Type {

	/*
	 * attacks
	 */
	DOUBLE_MELEE(Affect.STRENGTH, 1),
	ARCHER(Affect.STRENGTH, 6),
	SUMMON(Affect.STRENGTH, 1),
	REFLECT(Affect.STRENGTH, 1),
	POTION(Affect.STRENGTH, 1),
	TELEPORT(Affect.STRENGTH, 1),
	HEAL(Affect.STRENGTH, 1),
	FIREBALL(Affect.STRENGTH, 4),
	INFERNO(Affect.STRENGTH, 4),

	/*
	 * weaknesses
	 */
	HYDROPHOBIA(Affect.WEAKNESS, 4),
	PYROPHOBIA(Affect.WEAKNESS, 4),
	WOOD_ALLERGY(Affect.WEAKNESS, 8),
	GOLD_ALLERGY(Affect.WEAKNESS, 4),
	STONE_ALLERGY(Affect.WEAKNESS, 5),
	GREEDY(Affect.WEAKNESS, 4),
	GLUTTONY(Affect.WEAKNESS, 4),
	CHICKEN(Affect.WEAKNESS, 4);

	//TODO  AMOROUS, DANCE, PLASMOPHOBIA, ICHTHYOPHOBIA, ANIMAL_LOVER

	public final static Type[] STRENGTHS;
	public final static Type[] WEAKNESSES;

	static {
		STRENGTHS = filterByAffect(Affect.STRENGTH);
		WEAKNESSES = filterByAffect(Affect.WEAKNESS);
	}

	private static Type[] filterByAffect(Affect affect) {
		return Arrays.stream(Type.values()).filter((Type t) -> t.getAffect().equals(affect)).toArray(Type[]::new);
	}

	Type(Affect affect, int maxLevel) {
		this.affect = affect;
		this.maxLevel = maxLevel;
	}

	private final Affect affect;
	private final int maxLevel;

	public Affect getAffect() {
		return affect;
	}

	public boolean isStrength () {
		return isStrength(this);
	}

	public boolean isWeakness () {
		return isWeakness(this);
	}

	public static boolean isStrength(Type type) {
		return Affect.STRENGTH.equals(type.affect);
	}

	public static boolean isWeakness(Type type) {
		return Affect.WEAKNESS.equals(type.affect);
	}

	public int getMaxLevel() {
		return maxLevel;
	}
}
