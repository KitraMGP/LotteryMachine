package com.zi_jing.lotterymachine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.LotteryMachine;
import com.zi_jing.lotterymachine.network.PacketLotteryMachine;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.fml.network.NetworkDirection;

public class CommandLotteryMachine {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotterymachine").executes((sender) -> {
			LotteryMachine.INSTANCE.sendTo(new PacketLotteryMachine(), sender.getSource().asPlayer().connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
			return 0;
		}));
	}
}
