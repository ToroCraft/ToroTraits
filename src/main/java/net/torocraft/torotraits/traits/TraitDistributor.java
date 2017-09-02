package net.torocraft.torotraits.traits;

import java.util.function.Function;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.torocraft.torotraits.api.TraitApi;
import net.torocraft.torotraits.traits.logic.Allergy;
import net.torocraft.torotraits.traits.logic.Archer;
import net.torocraft.torotraits.traits.logic.Chicken;
import net.torocraft.torotraits.traits.logic.DoubleMelee;
import net.torocraft.torotraits.traits.logic.Fireball;
import net.torocraft.torotraits.traits.logic.Gluttony;
import net.torocraft.torotraits.traits.logic.Greedy;
import net.torocraft.torotraits.traits.logic.Heal;
import net.torocraft.torotraits.traits.logic.Hydrophobia;
import net.torocraft.torotraits.traits.logic.Inferno;
import net.torocraft.torotraits.traits.logic.Potion;
import net.torocraft.torotraits.traits.logic.Pyrophobia;
import net.torocraft.torotraits.traits.logic.Reflection;
import net.torocraft.torotraits.traits.logic.Summon;
import net.torocraft.torotraits.traits.logic.Teleport;


// TODO secondary traits should be used less frequently
// TODO randomize attack timing

public class TraitDistributor {

	public interface TraitProcess {
		void process(EntityEvent event, EntityCreature entity, Trait trait);
	}

	public static void distribute(EntityEvent event, EntityCreature entity, TraitProcess func) {
		TraitStore store = TraitApi.read(entity);
		if (store.traits == null) {
			return;
		}
		for (Trait trait : store.traits) {
			func.process(event, entity, trait);
		}
	}

	public static void onUpdate(EntityEvent event, EntityCreature entity, Trait trait) {
		switch (trait.type) {
		case FIREBALL:
			Fireball.onUpdate(entity, trait.level);
			return;
		case INFERNO:
			Inferno.onUpdate(entity, trait.level);
			return;
		case ARCHER:
			Archer.onUpdate(entity, trait.level);
			return;
		case SUMMON:
			Summon.onUpdate(entity, trait);
			return;
		case REFLECT:
			return;
		case POTION:
			Potion.onUpdate(entity);
			return;
		case TELEPORT:
			Teleport.onUpdate(entity);
			return;
		case HEAL:
			Heal.onUpdate(entity);
			return;
		case GREEDY:
			Greedy.onUpdate(entity, trait.level);
			return;
		case CHICKEN:
			Chicken.onUpdate(entity, trait.level);
			return;
		case GLUTTONY:
			Gluttony.onUpdate(entity, trait.level);
			return;
		case PYROPHOBIA:
			Pyrophobia.onUpdate(entity, trait.level);
			return;
		case HYDROPHOBIA:
			Hydrophobia.onUpdate(entity, trait.level);
			return;
		}
	}

	public static void onHurt(EntityEvent eventIn, EntityCreature victim, Trait trait) {
		LivingHurtEvent event = (LivingHurtEvent) eventIn;
		switch (trait.type) {
		case REFLECT:
			Reflection.onHurt(victim, event.getSource(), event.getAmount(), trait);
			break;
		case GOLD_ALLERGY:
		case STONE_ALLERGY:
		case WOOD_ALLERGY:
			Allergy.onHurt(event, victim, trait);
		}
	}


	public static void onAttack(EntityEvent eventIn, EntityCreature attacker, Trait trait) {
		LivingHurtEvent event = (LivingHurtEvent) eventIn;
		switch (trait.type) {
		case DOUBLE_MELEE:
			DoubleMelee.onAttack(event, attacker, trait);
			break;
		}
	}

	public static void onDrop(EntityEvent eventIn, EntityCreature entity, Trait trait) {
		LivingDropsEvent event = (LivingDropsEvent) eventIn;
		switch (trait.type) {
		case ARCHER:
			Archer.onDrop(event.getDrops(), entity, trait);
			break;
		}
	}
}
