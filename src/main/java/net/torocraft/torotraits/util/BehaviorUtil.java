package net.torocraft.torotraits.util;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.torocraft.torotraits.ToroTraits;
import net.torocraft.torotraits.traits.TraitHandler;

public class BehaviorUtil {

	public static void setFollowSpeed(EntityCreature bodyGuard, double followSpeed) {
		EntityAIMoveTowardsRestriction ai = null;
		for (EntityAITaskEntry entry : bodyGuard.tasks.taskEntries) {
			if (entry.action instanceof EntityAIMoveTowardsRestriction) {
				ai = (EntityAIMoveTowardsRestriction) entry.action;
			}
		}
		if (ai == null) {
			System.out.println("guard ai not found");
			return;
		}
		//not sure field_75433_e is the correct name for EntityAIMoveTowardsRestriction.movementSpeed
		ObfuscationReflectionHelper.setPrivateValue(EntityAIMoveTowardsRestriction.class, ai, followSpeed, "field_75433_e", "movementSpeed");
	}

	public static boolean moveToBlock(EntityLiving entity, BlockPos randBlock, double speed) {
		return entity.getNavigator().tryMoveToXYZ(randBlock.getX(), randBlock.getY(), randBlock.getZ(), speed);
	}

	public static BlockPos findPanicDestination(EntityCreature entity, int level) {
		return new BlockPos(RandomPositionGenerator.findRandomTarget(entity, 5 + (2 * level), 4));
	}

	public static boolean stealAndWorshipItem(EntityLiving entity, List<EntityItem> desiredItems, int level) {
		if (desiredItems.size() > 0) {
			entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, desiredItems.get(0).getItem());
			BehaviorUtil.startWorshiping(entity,  3 + (2 * level));
			for (EntityItem item : desiredItems) {
				entity.world.removeEntity(item);
			}
			return true;
		}
		return false;
	}

	public static void stopWorshiping(EntityLiving entity) {
		entity.getEntityData().removeTag(ToroTraits.NBT_WORSHIP_COOLDOWN);
		entity.getTags().remove(TraitHandler.TAG_WORSHIPING);

		// TODO the item needs to be cached on the entity then restored here
		//entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, nemesis.getHandInventory().get(0));

		resumeAITasks(entity);
	}

	public static boolean isWorshiping(EntityLiving entity) {
		return entity.getTags().contains(TraitHandler.TAG_WORSHIPING) && entity.getEntityData().hasKey(ToroTraits.NBT_WORSHIP_COOLDOWN);
	}

	private static void startWorshiping(EntityLiving entity, int count) {
		entity.getTags().add(TraitHandler.TAG_WORSHIPING);
		entity.getEntityData().setTag(ToroTraits.NBT_WORSHIP_COOLDOWN, new NBTTagInt(count));
		cancelAllAITasks(entity);
	}

	public static void moveToItem(EntityLiving entity, EntityItem item) {
		EntityCreature mob = (EntityCreature)entity;
		mob.getNavigator().tryMoveToXYZ(item.getPosition().getX() + 0.5D, item.getPosition().getY() + 1, item.getPosition().getZ() + 0.5D, 2.0);
	}

	public static void cancelAllAITasks(EntityLiving entity) {
		//TODO replace this by inserting an overriding AI task so that the entity is not "frozen" in place when this is activated
		entity.setNoAI(true);
	}

	public static void resumeAITasks(EntityLiving entity) {
		entity.setNoAI(false);
	}

	public static boolean canSee(EntityCreature spectator, Entity subject) {
		return spectator.getEntitySenses().canSee(subject);
	}

	public static void throwPearl(EntityLiving entity, EntityLivingBase target) {
		World world = entity.getEntityWorld();
		EntityEnderPearl pearl = new EntityEnderPearl(world, entity);

		double dX = target.posX - entity.posX;
		double dY = target.getEntityBoundingBox().minY + (double) (target.height / 3.0F) - pearl.posY;
		double dZ = target.posZ - entity.posZ;

		double distanceSq = dX * dX + dY * dY + dZ * dZ;

		if (distanceSq < 20) {
			return;
		}

		double levelDistance = MathHelper.sqrt(dX * dX + dZ * dZ);

		pearl.setThrowableHeading(dX, dY + levelDistance * 0.20000000298023224D, dZ, 1.6F,
				(float) (14 - world.getDifficulty().getDifficultyId() * 4));

		entity.playSound(SoundEvents.ENTITY_ENDERPEARL_THROW, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));

		world.spawnEntity(pearl);
	}
}
