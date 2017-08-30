package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.torocraft.torotraits.ToroTraits;
import net.torocraft.torotraits.network.MessageReflectDamageAnimation;
import net.torocraft.torotraits.traits.Trait;

public class Reflection {

	public static void onHurt(EntityCreature nemesisEntity, DamageSource source, float amount, Trait trait) {
		if (nemesisEntity.isEntityInvulnerable(source)) {
			return;
		}

		if (source instanceof EntityDamageSourceIndirect) {
			reflectArrowAtAttacker(nemesisEntity, source, trait);
		} else {
			reflectMeleeAttack(nemesisEntity, source, amount, trait);
		}
	}

	private static void reflectMeleeAttack(EntityCreature nemesisEntity, DamageSource source, float amount, Trait trait) {
		Entity attacker = source.getTrueSource();

		if (attacker == null) {
			return;
		}

		int level = trait.level;

		float reflectFactor = (float) level * 0.2f;
		float reflectAmount = amount * reflectFactor;

		attacker.attackEntityFrom(DamageSource.causeMobDamage(nemesisEntity), reflectAmount);

		TargetPoint point = new TargetPoint(attacker.dimension, attacker.posX, attacker.posY, attacker.posZ, 100);
		ToroTraits.NETWORK.sendToAllAround(new MessageReflectDamageAnimation(attacker.getEntityId()), point);
	}

	private static void reflectArrowAtAttacker(EntityCreature nemesisEntity, DamageSource source, Trait trait) {
		if (!"arrow".equals(source.getDamageType())) {
			return;
		}

		if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase) {

			int level = trait.level;

			int arrowCount = 1;

			if (level > 8) {
				arrowCount = 4;
			} else if (level > 6) {
				arrowCount = 3;
			} else if (level > 4) {
				arrowCount = 2;
			}

			for (int i = 0; i < arrowCount; i++) {
				Archer.attackWithArrow(nemesisEntity, (EntityLivingBase) source.getTrueSource(), 1);
			}
		}

		if (source.getImmediateSource() != null) {
			source.getImmediateSource().setDead();
		}
	}
}
