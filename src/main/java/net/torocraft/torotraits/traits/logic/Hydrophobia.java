package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.torocraft.torotraits.api.BehaviorApi;

public class Hydrophobia {

	public static void onUpdate(EntityLiving entity, int level) {
		if (entity.isWet()) {
			entity.setAttackTarget(null);
			BehaviorApi.moveToBlock(entity, BehaviorApi.findPanicDestination((EntityCreature)entity, level), 2.0D);
		}
	}
}
