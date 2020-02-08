package com.zi_jing.lotterymachine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.ConfigHandler;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandReload {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lt_reload").requires((p) -> p.hasPermissionLevel(3)).executes((p) -> {
			ConfigHandler.load();
			return 0;
		}));
	}
}
