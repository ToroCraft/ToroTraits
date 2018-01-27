package net.torocraft.torotraits.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.torocraft.torotraits.network.MessageHealAnimation;
import net.torocraft.torotraits.network.MessageReflectDamageAnimation;
import net.torocraft.torotraits.network.MessageWorshipAnimation;

public class CommonProxy {

  public void preInit(FMLPreInitializationEvent e) {

  }

  public void init(FMLInitializationEvent e) {
    initPackets();
  }

  public void postInit(FMLPostInitializationEvent e) {

  }

  private void initPackets() {
    int packetId = 0;
    MessageHealAnimation.init(packetId++);
    MessageReflectDamageAnimation.init(packetId++);
    MessageWorshipAnimation.init(packetId++);
  }

  public void openGui(int modGuiId) {

  }

  public EntityPlayer getPlayer() {
    return null;
  }

  public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord,
      double zCoord, double xSpeed, double ySpeed,
      double zSpeed, int... parameters) {

  }

}
