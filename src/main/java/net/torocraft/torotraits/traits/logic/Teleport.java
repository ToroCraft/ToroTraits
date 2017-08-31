package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.torocraft.torotraits.api.BehaviorApi;

public class Teleport {

	public static void onUpdate(EntityLiving entity) {
		World world = entity.world;

		if (world.getTotalWorldTime() % 40 != 0) {
			return;
		}

		EntityLivingBase target = entity.getAttackTarget();

		if (target == null) {
			return;
		}

		if (!entity.getEntitySenses().canSee(target)) {
			return;
		}

		BehaviorApi.throwPearl(entity, target);
	}

}
