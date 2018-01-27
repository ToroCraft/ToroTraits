package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.torocraft.torotraits.api.BehaviorApi;
import net.torocraft.torotraits.api.TraitApi;

public class Chicken {

  public static void onUpdate(EntityLiving entity, int level) {
    if (chickensNearby(entity)) {
      entity.setAttackTarget(null);
      BehaviorApi
          .moveToBlock(entity, BehaviorApi.findPanicDestination((EntityCreature) entity, level),
              2.0D);
    }
  }

  private static boolean chickensNearby(EntityLiving entity) {
    return entity.world
        .getEntitiesWithinAABB(EntityChicken.class, TraitApi.nearByBox(entity.getPosition(), 5))
        .size() > 0;
  }

}
