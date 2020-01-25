package com.zi_jing.lotterymachine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.LotteryMachine;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandLotteryMachine {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotterymachine").executes((sender) -> {
			LotteryMachine.getLogger().info("233333333333333333333");
			return 0;
		}));
	}
}
