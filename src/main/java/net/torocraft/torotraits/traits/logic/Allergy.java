package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.torocraft.torotraits.traits.Trait;
import net.torocraft.torotraits.traits.Type;

public class Allergy {

  private static final int ONE_SECOND = 20;

  public static void onHurt(LivingHurtEvent event, EntityCreature entity, Trait trait) {
    EntityLivingBase attacker = getAttacker(event);
    int level = trait.level;

    if (attacker == null) {
      return;
    }

    ItemStack item = attacker.getHeldItem(EnumHand.MAIN_HAND);

    if (item.isEmpty()) {
      return;
    }

    String material = getToolMaterial(item);

    if (material == null) {
      return;
    }

    if (!isAllergicToMaterial(material, trait)) {
      return;
    }

    entity.addPotionEffect(getPotionEffectForAllergy(entity, level));
    event.setAmount(event.getAmount() * getDamageModifier(level));
  }

  private static PotionEffect getPotionEffectForAllergy(EntityLivingBase entity, int level) {
    PotionEffect p = new PotionEffect(MobEffects.POISON, getPotionDuration(level));
    if (!entity.isPotionApplicable(p)) {
      p = new PotionEffect(MobEffects.WEAKNESS, getPotionDuration(level));
    }
    return p;
  }

  private static int getPotionDuration(int level) {
    return ONE_SECOND * level * 2;
  }

  private static float getDamageModifier(int level) {
    return 1.5f + ((float) level / 3f);
  }

  private static EntityLivingBase getAttacker(LivingHurtEvent event) {
    Entity attacker = event.getSource().getTrueSource();
    if (attacker instanceof EntityLivingBase) {
      return (EntityLivingBase) attacker;
    }
    return null;
  }

  private static String getToolMaterial(ItemStack item) {
    if (item.getItem() instanceof ItemSword) {
      return ((ItemSword) item.getItem()).getToolMaterialName();
    }
    if (item.getItem() instanceof ItemTool) {
      return ((ItemTool) item.getItem()).getToolMaterialName();
    }
    return null;
  }

  private static boolean isAllergicToMaterial(String material, Trait trait) {
    return woodAllergyApplies(material, trait) || goldAllergyApplies(material, trait)
        || stoneAllergyApplies(material, trait);
  }

  private static boolean stoneAllergyApplies(String material, Trait trait) {
    return Type.STONE_ALLERGY.equals(trait.type) && material.equals("STONE");
  }

  private static boolean goldAllergyApplies(String material, Trait trait) {
    return Type.GOLD_ALLERGY.equals(trait.type) && material.equals("GOLD");
  }

  private static boolean woodAllergyApplies(String material, Trait trait) {
    return Type.WOOD_ALLERGY.equals(trait.type) && material.equals("WOOD");
  }

}
