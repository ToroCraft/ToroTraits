package net.torocraft.torotraits;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//@Mod.EventBusSubscriber(value = { Side.CLIENT }, modid = ToroTraits.MODID)
public class ClientEventHandlers {

  @SubscribeEvent
  public static void scaleEntity(RenderLivingEvent.Pre event) {
    float scale = 2f;
    if (modelShouldBeScaled(event)) {
      GlStateManager.pushAttrib();
      GlStateManager.pushMatrix();
      GlStateManager.translate(5, 0, 5);
      GlStateManager.scale(scale, scale, scale);
    }
  }

  @SubscribeEvent
  public static void scaleEntity(RenderLivingEvent.Post event) {
    if (modelShouldBeScaled(event)) {
      GlStateManager.popMatrix();
      GlStateManager.popAttrib();
    }
  }

  private static boolean modelShouldBeScaled(RenderLivingEvent event) {

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
