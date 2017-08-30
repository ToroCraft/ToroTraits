package net.torocraft.torotraits;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.torocraft.torotraits.traits.Type;
import net.torocraft.torotraits.util.SpawnUtil;

public class ToroTraitsCommand extends CommandBase {

	@Override
	@Nonnull
	public String getName() {
		return "torotraits";
	}

	@Override
	@Nonnull
	public String getUsage(@Nullable ICommandSender sender) {
		return "commands.torotraits.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {

		if (args.length < 1) {
			throw new WrongUsageException("commands.torotraits.usage");
		}

		String command = args[0];

		switch (command) {
		case "spawn":
			spawn(sender, args);
			return;
		default:
			throw new WrongUsageException("commands.torotraits.usage");
		}
	}

	private void spawn(ICommandSender sender, @Nonnull String[] args) throws CommandException {
		if (!(sender instanceof EntityPlayer)) {
			return;
		}

		if (args.length != 3) {
			throw new WrongUsageException("commands.torotraits.spawn");
		}

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		World world = player.world;

		Entity entity = SpawnUtil.getEntityFromString(world, args[1]);

		// TODO move to an addTrait method
		entity.addTag(ToroTraits.TAG_HAS_TRAIT);

		if (entity == null) {
			throw new WrongUsageException("commands.torotraits.spawn");
		}

		Type traitType = null;
		try {
			traitType = Type.valueOf(args[2]);
		}catch (Exception e) {
			throw new WrongUsageException("commands.torotraits.spawn");
		}

		// TODO add trait to entity, some how

		SpawnUtil.spawnEntityLiving(world, (EntityCreature) entity, player.getPosition(), 0);
	}

	@Override
	@Nonnull
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "spawn");
		}
		String command = args[0];
		switch (command) {
		case "spawn":
			return tabCompletionsForSpawn(args);
		default:
			return Collections.emptyList();
		}
	}

	private List<String> tabCompletionsForSpawn(String[] args) {
		if (args.length == 2) {
			return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());
		}

		if (args.length == 3) {
			return getListOfStringsMatchingLastWord(args, Arrays.asList(Type.values()));
		}

		return Collections.emptyList();
	}

}
