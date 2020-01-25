package com.zi_jing.lotterymachine.network;

import java.util.function.Supplier;

import com.zi_jing.lotterymachine.LotteryMachine;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketLotteryMachine {
	
	public PacketLotteryMachine() {
		
	}
	
	public static PacketLotteryMachine fromBytes(PacketBuffer buf) {
		return new PacketLotteryMachine();
	}
	
	public static void toBytes(PacketLotteryMachine msg, PacketBuffer buf) {
		
	}
	
	public static void handle(PacketLotteryMachine msg, Supplier<NetworkEvent.Context> ctx) {
		if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
			LotteryMachine.getLogger().info("packet");
		}
	}
}
