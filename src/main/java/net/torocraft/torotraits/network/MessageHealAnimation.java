package net.torocraft.torotraits.network;

import io.netty.buffer.ByteBuf;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.torocraft.torotraits.ToroTraits;

public class MessageHealAnimation implements IMessage {

	private int id;

	public static void init(int packetId) {
		ToroTraits.NETWORK.registerMessage(MessageHealAnimation.Handler.class, MessageHealAnimation.class, packetId, Side.CLIENT);
	}

	public MessageHealAnimation() {

	}

	public MessageHealAnimation(int id) {
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<MessageHealAnimation, IMessage> {
		@Override
		public IMessage onMessage(final MessageHealAnimation message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> work(message));
			return null;
		}

		public static void work(MessageHealAnimation message) {
			EntityPlayer player = ToroTraits.PROXY.getPlayer();
			Entity e = player.getEntityWorld().getEntityByID(message.id);
			if (e != null) {
				spawnParticles(e.getPosition(), player.getRNG());
			}
		}

		private static void spawnParticles(BlockPos pos, Random rand) {
			double x, y, z;
			for (int i = 0; i < 15; i++) {
				x = (double) ((float) pos.getX() + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 2D;
				y = (double) ((float) pos.getY() + 1.4F) + (double) (rand.nextFloat() - 0.5F) * 2D;
				z = (double) ((float) pos.getZ() + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 2D;
				ToroTraits.PROXY.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
			}
			for (int i = 0; i < 10; i++) {
				x = (double) ((float) pos.getX() + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 1D;
				y = (double) ((float) pos.getY() + 0.4F) + (double) (rand.nextFloat() - 0.5F) * 1D;
				z = (double) ((float) pos.getZ() + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 1D;
				ToroTraits.PROXY.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
