package net.torocraft.torotraits.traits.logic;

import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.torocraft.torotraits.ToroTraits;
import net.torocraft.torotraits.network.MessageWorshipAnimation;
import net.torocraft.torotraits.api.BehaviorApi;

public class Greedy {

	public static void onUpdate(EntityLiving entity, int level) {
		if (BehaviorApi.isWorshiping(entity)) {
			if (entity.getEntityData().getInteger(ToroTraits.NBT_WORSHIP_COOLDOWN) >= 0) {
				TargetPoint point = new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100);
				ToroTraits.NETWORK.sendToAllAround(new MessageWorshipAnimation(entity.getEntityId()), point);
				return;
			}
			BehaviorApi.stopWorshiping(entity);
		}

		if (BehaviorApi.stealAndWorshipItem(entity, getShiniesWithinAABB(entity, 1.0D, 0.0D, 1.0D), level)) {
			return;
		}

		int distractDistance = 20;
		EntityItem shiny = getVisibleItem(entity, getShiniesWithinAABB(entity, distractDistance, distractDistance, distractDistance));
		if (shiny != null) {
			entity.setAttackTarget(null);
			BehaviorApi.moveToItem(entity, shiny);
		}

	}

	private static List<EntityItem> getShiniesWithinAABB(EntityLiving entity, double x, double y, double z) {
		return entity.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(entity.getPosition()).expand(x, y, z),
				item -> item.getEntityItem().getItem().equals(Items.GOLD_INGOT) || item.getEntityItem().getItem().equals(Items.EMERALD) || item.getEntityItem()
						.getItem().equals(Items.DIAMOND));
	}

	public static void decrementCooldown(EntityLiving entity) {
		if (!entity.getEntityData().hasKey(ToroTraits.NBT_WORSHIP_COOLDOWN)) {
			return;
		}
		entity.getEntityData()
				.setInteger(ToroTraits.NBT_WORSHIP_COOLDOWN, entity.getEntityData().getInteger(ToroTraits.NBT_WORSHIP_COOLDOWN) - 1);
	}

	public static EntityItem getVisibleItem(EntityLiving entity, List<EntityItem> desiredItems) {
		for (EntityItem item : desiredItems) {
			if (entity.getEntitySenses().canSee(item)) {
				return item;
			}
		}

		return null;
	}
}
