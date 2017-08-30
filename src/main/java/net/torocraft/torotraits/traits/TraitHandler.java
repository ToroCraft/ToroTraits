package net.torocraft.torotraits.traits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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

public class TraitHandler {

	public static final String TAG_WORSHIPING = "nemesissystem_worshiping";

	public static final Random rand = new Random();

	public static void onUpdate(EntityCreature nemesisEntity) {
		Greedy.decrementCooldown(nemesisEntity);

		// TODO get traits from entity
		List<Trait> traits = new ArrayList<>();

		for (Trait trait : traits) {
			// TODO secondary traits should be used less frequently

			// TODO randomize attack timing
			onUpdate(nemesisEntity, trait);
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
			Summon.onUpdate(entity);
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
		case GOLD_ALLERGY:
			return;
		case WOOD_ALLERGY:
			return;
		case STONE_ALLERGY:
			return;
		}
	}

	public static void onHurt(LivingHurtEvent event) {
		EntityCreature nemesisEntity = (EntityCreature) event.getEntity();

		// TODO get traits from entity
		List<Trait> traits = new ArrayList<>();

		for (Trait trait : traits) {
			switch (trait.type) {
			case DOUBLE_MELEE:
				break;
			case ARCHER:
				break;
			case SUMMON:
				break;
			case REFLECT:
				Reflection.onHurt(nemesisEntity, event.getSource(), event.getAmount());
				break;
			case FIREBALL:
				break;
			case POTION:
				break;
			case TELEPORT:
				break;
			case HEAL:
				break;
			case GOLD_ALLERGY:
			case STONE_ALLERGY:
			case WOOD_ALLERGY:
				Allergy.onHurt(event, trait.level);
			}
		}
	}

	public static AxisAlignedBB nearByBox(BlockPos position, int radius) {
		return new AxisAlignedBB(position).grow(radius, radius, radius);
	}

	private static EntityItem damageAndDrop(EntityCreature entity, ItemStack stack) {
		if (stack.isItemStackDamageable()) {
			stack.setItemDamage(stack.getMaxDamage() - entity.getRNG().nextInt(1 + entity.getRNG().nextInt(Math.max(stack.getMaxDamage() - 3, 1))));
		}
		return drop(entity, stack);
	}

	public static EntityItem drop(EntityCreature entity, ItemStack stack) {
		return new EntityItem(entity.getEntityWorld(), entity.posX, entity.posY, entity.posZ, stack);
	}
}
