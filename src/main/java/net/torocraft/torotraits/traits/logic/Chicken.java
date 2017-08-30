package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.torocraft.torotraits.traits.TraitHandler;
import net.torocraft.torotraits.util.BehaviorUtil;

public class Chicken {

	public static void onUpdate(EntityLiving entity, int level) {
		if (chickensNearby(entity)) {
			entity.setAttackTarget(null);
			BehaviorUtil.moveToBlock(entity, BehaviorUtil.findPanicDestination((EntityCreature) entity, level), 2.0D);
		}
	}

	private static boolean chickensNearby(EntityLiving entity) {
		return entity.world.getEntitiesWithinAABB(EntityChicken.class, TraitHandler.nearByBox(entity.getPosition(), 5)).size() > 0;
	}

}
