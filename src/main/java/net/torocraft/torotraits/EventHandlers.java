package net.torocraft.torotraits;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.torocraft.torotraits.traits.TraitHandler;

@Mod.EventBusSubscriber(modid = ToroTraits.MODID)
public class EventHandlers {

	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent event) {
		World world = event.getEntity().getEntityWorld();

		if (world.getTotalWorldTime() % 20 != 0) {
			return;
		}

		if (!(event.getEntity() instanceof EntityLiving)) {
			return;
		}

		if (event.getEntity().getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
			TraitHandler.onUpdate((EntityCreature) event.getEntity());
		}
	}

	@SubscribeEvent
	public static void onDrop(LivingDropsEvent event) {
		if (!(event.getEntity() instanceof EntityMob)) {
			return;
		}

		if (event.getEntity().getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
			TraitHandler.onDrop(event);
		}

	}

	@SubscribeEvent
	public static void onHurt(LivingHurtEvent event) {
		World world = event.getEntity().getEntityWorld();
		if (!(event.getEntity() instanceof EntityCreature)) {
			return;
		}
		if (event.getEntity().getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
			TraitHandler.onHurt(event);
		}
	}

}
