package net.torocraft.torotraits.traits.logic;

import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.torocraft.torotraits.ToroTraits;
import net.torocraft.torotraits.api.TraitApi;
import net.torocraft.torotraits.network.MessageHealAnimation;

public class Heal {

  public static void onUpdate(EntityLiving entity) {
    World world = entity.world;
    if (world.getTotalWorldTime() % 40 != 0) {
      return;
    }
    findHealableEntities(entity.world, entity.getPosition(), 40)
        .forEach(Heal::possiblyHealCreature);
  }

  private static List<EntityCreature> findHealableEntities(World world, BlockPos pos, int range) {
    return world.getEntitiesWithinAABB(EntityCreature.class, TraitApi.nearByBox(pos, range),
        Heal::entityIsHealable);
  }

  private static boolean entityIsHealable(EntityCreature entity) {
    return !entity.isDead && entity.getHealth() > 0 &&
        (entity instanceof EntityMob || entity.getTags().contains(ToroTraits.TAG_IS_HEALABLE));
  }

  private static void possiblyHealCreature(EntityCreature entity) {
    if (entity.getRNG().nextInt(1) == 0) {
      healCreature(entity);
    }
  }

  private static void healCreature(EntityCreature entity) {
    if (canBeHealed(entity)) {
      float healTo = Math
          .min(entity.getHealth() + 1 + entity.getRNG().nextInt(5), entity.getMaxHealth());
      entity.setHealth(healTo);
      TargetPoint point = new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ,
          100);
      ToroTraits.NETWORK.sendToAllAround(new MessageHealAnimation(entity.getEntityId()), point);
      entity.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 3.0F,
          1.0F / (entity.getRNG().nextFloat() * 0.4F + 0.8F));
    }
  }

  private static boolean canBeHealed(EntityCreature entity) {
    return entity.getHealth() < entity.getMaxHealth() && entity.getHealth() > 0 && !entity.isDead;
  }
}
