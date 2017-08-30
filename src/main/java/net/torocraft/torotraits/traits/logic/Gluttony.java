package net.torocraft.torotraits.traits.logic;

import java.util.Arrays;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.torocraft.torotraits.ToroTraits;
import net.torocraft.torotraits.network.MessageWorshipAnimation;
import net.torocraft.torotraits.traits.TraitHandler;
import net.torocraft.torotraits.util.BehaviorUtil;

public class Gluttony {

	private static final Item[] TASTY_THINGS = { Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT };

	public static void onUpdate(EntityCreature entity, int level) {
		if (BehaviorUtil.isWorshiping(entity)) {
			if (entity.getEntityData().getInteger(ToroTraits.NBT_WORSHIP_COOLDOWN) >= 0) {
				TargetPoint point = new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100);
				ToroTraits.NETWORK.sendToAllAround(new MessageWorshipAnimation(entity.getEntityId()), point);
				return;
			}
			BehaviorUtil.stopWorshiping(entity);
		}

		if (BehaviorUtil.stealAndWorshipItem(entity, getFoodWithinAABB(entity, 1), level)) {
			return;
		}
		searchForTastyTreats(entity);
	}

	private static void searchForTastyTreats(EntityCreature entity) {
		EntityItem food = lookForANearByTreat(entity);
		if (food != null) {
			entity.setAttackTarget(null);
			BehaviorUtil.moveToItem(entity, food);
		}
	}

	private static EntityItem lookForANearByTreat(EntityCreature entity) {
		return getFoodWithinAABB(entity, 20)
				.stream()
				.filter(e -> BehaviorUtil.canSee(entity, e))
				.findFirst().orElse(null);
	}

	private static List<EntityItem> getFoodWithinAABB(EntityLiving entity, int distance) {
		return entity.world.getEntitiesWithinAABB(EntityItem.class, TraitHandler.nearByBox(entity.getPosition(), distance),
				Gluttony::isTasty);
	}

	private static boolean isTasty(EntityItem item) {
		return Arrays.stream(TASTY_THINGS).anyMatch(x -> x == item.getItem().getItem());
	}

}
