package net.torocraft.torotraits.traits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.torocraft.torotraits.traits.logic.Allergy;
import net.torocraft.torotraits.traits.logic.Archer;
import net.torocraft.torotraits.traits.logic.Chicken;
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
import net.torocraft.torotraits.util.TraitUtil;

public class TraitHandler {

	public static final Random rand = new Random();

	public static void onUpdate(EntityCreature entity) {
		Greedy.decrementCooldown(entity);

		TraitStore store = TraitUtil.read(entity);

		if (store.traits == null) {
			return;
		}

		for (Trait trait : store.traits) {
			// TODO secondary traits should be used less frequently

			// TODO randomize attack timing
			onUpdate(entity, trait);
		}
	}

	private static void onUpdate(EntityCreature entity, Trait trait) {
		switch (trait.type) {
		case DOUBLE_MELEE:
			// TODO make the nemesis hit twice when attacking
			return;
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

	public static void onHurt(LivingHurtEvent event) {
		EntityCreature entity = (EntityCreature) event.getEntity();

		TraitStore store = TraitUtil.read(entity);

		if (store.traits == null) {
			return;
		}

		for (Trait trait : store.traits) {
			onHurt(event, entity, trait);
		}
	}

	private static void onHurt(LivingHurtEvent event, EntityCreature entity, Trait trait) {
		switch (trait.type) {
		case REFLECT:
			Reflection.onHurt(entity, event.getSource(), event.getAmount(), trait);
			break;
		case GOLD_ALLERGY:
		case STONE_ALLERGY:
		case WOOD_ALLERGY:
			Allergy.onHurt(event, trait);
		}
	}

	public static void onDrop(LivingDropsEvent event) {
		EntityCreature entity = (EntityCreature) event.getEntity();

		TraitStore store = TraitUtil.read(entity);

		if (store.traits == null) {
			return;
		}

		for (Trait trait : store.traits) {
			onDrop(event, entity, trait);
		}
	}

	private static void onDrop(LivingDropsEvent event, EntityCreature entity, Trait trait) {
		switch (trait.type) {
		case ARCHER:
			Archer.onDrop(event.getDrops(), entity, trait);
			break;
		}
	}

}
