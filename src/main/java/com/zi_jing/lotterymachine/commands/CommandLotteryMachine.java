package com.zi_jing.lotterymachine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.LotteryMachine;
import com.zi_jing.lotterymachine.entity.EntityBox;
import com.zi_jing.lotterymachine.network.PacketLotteryMachine;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class CommandLotteryMachine {
	// f**k you Mojang
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotterymachine").executes((sender) -> {
			if (sender.getSource().getEntity() instanceof ServerPlayerEntity) {
				run(sender.getSource().asPlayer());
			}
			return 0;
		}));
	}
	
	private static void run(ServerPlayerEntity player) {
		ItemStack currentStack = player.inventory.mainInventory.get(player.inventory.currentItem);
		if(currentStack.getItem() != Items.GOLD_NUGGET || currentStack.getCount() <= 5) {
			LotteryMachine.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketLotteryMachine(1));
		} else {
			if(currentStack.getCount() == 5) player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
			if(currentStack.getCount() > 5) player.inventory.mainInventory.set(player.inventory.currentItem, new ItemStack(currentStack.getItem(), currentStack.getCount() - 5));
			LotteryMachine.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketLotteryMachine(0));
		}
		World world = player.world;
		EntityBox entity = new EntityBox(EntityBox.TYPE, world);
		entity.posX = player.posX;
		entity.posY = player.posY;
		entity.posZ = player.posZ;
		world.addEntity(entity);
	}

}
