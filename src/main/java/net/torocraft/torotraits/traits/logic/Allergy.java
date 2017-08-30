package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Allergy {
	private static final int ONE_SECOND = 20;

	public static void onHurt(LivingHurtEvent event, int level) {
		EntityLiving entity = (EntityLiving) event.getEntity();
		EntityLivingBase attacker = getAttacker(event);

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

		if (!isAllergicToMaterial(material)) {
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

	private static boolean isAllergicToMaterial(String material) {
		return woodAllergyApplies(material) || goldAllergyApplies(material) || stoneAllergyApplies(material);
	}

	private static boolean stoneAllergyApplies(String material) {
		// TODO add has trait check from entity
		return false;
		// return nemesis.hasTrait(Type.STONE_ALLERGY) && material.equals("STONE");
	}

	private static boolean goldAllergyApplies(String material) {
		// TODO add has trait check from entity
		return false;
		// return nemesis.hasTrait(Type.GOLD_ALLERGY) && material.equals("GOLD");
	}

	private static boolean woodAllergyApplies(String material) {
		// TODO add has trait check from entity
		return false;
		// return nemesis.hasTrait(Type.WOOD_ALLERGY) && material.equals("WOOD");
	}

}
