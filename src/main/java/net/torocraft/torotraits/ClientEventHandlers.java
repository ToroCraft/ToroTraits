package net.torocraft.torotraits;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.torocraft.torotraits.traits.TraitHandler;

@Mod.EventBusSubscriber(value = { Side.CLIENT })
public class ClientEventHandlers {

	@SubscribeEvent
	public void scaleEntity(RenderLivingEvent.Pre event) {
		float scale = 2f;
		if (modelShouldBeScaled(event)) {
			GlStateManager.pushAttrib();
			GlStateManager.pushMatrix();
			GlStateManager.translate(5, 0, 5);
			GlStateManager.scale(scale, scale, scale);
		}
	}

	@SubscribeEvent
	public void scaleEntity(RenderLivingEvent.Post event) {
		if (modelShouldBeScaled(event)) {
			GlStateManager.popMatrix();
			GlStateManager.popAttrib();
		}
	}

	private boolean modelShouldBeScaled(RenderLivingEvent event) {

		// TODO entity scaling
		if (true) {
			return false;
		}

		if (!event.getEntity().getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
			return false;
		}
		if (!(event.getRenderer().getMainModel() instanceof ModelBiped)) {
			return false;
		}
		return true;
	}


}
