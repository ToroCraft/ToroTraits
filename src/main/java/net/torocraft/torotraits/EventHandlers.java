package net.torocraft.torotraits;

import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.torotraits.traits.TraitDistributor;
import net.torocraft.torotraits.traits.logic.Greedy;

@Mod.EventBusSubscriber(modid = ToroTraits.MODID)
public class EventHandlers {

	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent event) {

		if (event.getEntity().world.getTotalWorldTime() % 20 != 0) {
			return;
		}

		if (!(event.getEntity() instanceof EntityCreature)) {
			return;
		}

		EntityCreature entity = (EntityCreature) event.getEntity();

		World world = entity.getEntityWorld();

		if (entity.getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
			Greedy.decrementCooldown(entity);
			TraitDistributor.distribute(event, entity, TraitDistributor::onUpdate);
		}
	}

	@SubscribeEvent
	public static void onDrop(LivingDropsEvent event) {
		if (!(event.getEntity() instanceof EntityCreature)) {
			return;
		}

		if (event.getEntity().getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
			EntityCreature entity = (EntityCreature) event.getEntity();
			TraitDistributor.distribute(event, entity, TraitDistributor::onDrop);
		}
	}

	@SubscribeEvent
	public static void onHurtOrAttack(LivingHurtEvent event) {
		World world = event.getEntity().getEntityWorld();
		if (!(event.getEntity() instanceof EntityCreature)) {
			return;
		}
		if (event.getEntity().getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
			if (!(event.getEntity() instanceof EntityCreature)) {
				return;
			}

			EntityCreature attacker = (EntityCreature) event.getEntity();
			TraitDistributor.distribute(event, attacker, TraitDistributor::onHurt);
		}
		// TODO handle trait holder as the attacker : onAttack
	}

}
