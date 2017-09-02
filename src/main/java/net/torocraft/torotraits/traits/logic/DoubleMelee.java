package net.torocraft.torotraits.traits.logic;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.torocraft.torotraits.EventHandlers;
import net.torocraft.torotraits.ToroTraits;
import net.torocraft.torotraits.traits.Trait;

public class DoubleMelee {
	public static void onAttack(LivingHurtEvent event, EntityCreature attacker, Trait trait) {
		EntityLivingBase victim = EventHandlers.getVictim(event);
		if (victim == null) {
			return;
		}
		event.setAmount(event.getAmount() * (1f + (float) trait.level/2f));
	}
}
