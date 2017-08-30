package net.torocraft.torotraits;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

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
			throw new WrongUsageException("commands.skillutil.usage");
		}

		String command = args[0];

		switch (command) {
		case "test":
			return;
		default:
			throw new WrongUsageException("commands.skillutil.usage");
		}
	}

}
