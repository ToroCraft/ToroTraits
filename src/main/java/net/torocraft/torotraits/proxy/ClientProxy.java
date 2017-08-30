package net.torocraft.torotraits.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.torocraft.torotraits.ToroTraits;

public class ClientProxy extends CommonProxy {

	@Override
	public void openGui(int modGuiId) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		player.openGui(ToroTraits.INSTANCE, modGuiId, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
	}

	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Override
	public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed,
			double zSpeed, int... parameters) {
		Minecraft.getMinecraft().world.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
	}

}