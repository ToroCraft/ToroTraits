package net.torocraft.torotraits;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.torocraft.torotraits.proxy.CommonProxy;

@Mod(modid = ToroTraits.MODID, version = ToroTraits.VERSION, name = ToroTraits.MODNAME)
public class ToroTraits {

	public static final String MODID = "skillutil";
	public static final String VERSION = "1.12.1-1";
	public static final String MODNAME = "ToroTraits";

	public static final String TAG_HAS_TRAIT = MODID + "_hasTrait";
	public static final String TAG_SUMMONED_MOB = MODID + "_summonedMob";

	public static final String NBT_WORSHIP_COOLDOWN = MODID + "_worshipCooldown";

	@Mod.Instance(MODID)
	public static ToroTraits INSTANCE;

	public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@SidedProxy(clientSide = "net.torocraft.torotraits.proxy.ClientProxy", serverSide = "net.torocraft.torotraits.proxy.ServerProxy")
	public static CommonProxy PROXY;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		PROXY.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		PROXY.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		PROXY.postInit(e);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		e.registerServerCommand(new ToroTraitsCommand());
	}

}
