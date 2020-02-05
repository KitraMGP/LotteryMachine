package com.zi_jing.lotterymachine.commands;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.LotteryMachine;
import com.zi_jing.lotterymachine.network.PacketLotteryMachine;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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
		BlockPos pos = ((BlockRayTraceResult)player.func_213324_a(20.0D, 0.0F, false)).getPos();
		if(player.world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() != Blocks.AIR) {LotteryMachine.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketLotteryMachine(2)); return;}
		ItemStack currentStack = player.inventory.mainInventory.get(player.inventory.currentItem);
		if(currentStack.getItem() != Items.GOLD_NUGGET || currentStack.getCount() <= 5) {
			LotteryMachine.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketLotteryMachine(1));
		} else {
			if(currentStack.getCount() == 5) player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
			if(currentStack.getCount() > 5) player.inventory.mainInventory.set(player.inventory.currentItem, new ItemStack(currentStack.getItem(), currentStack.getCount() - 5));
			//try {
			//player.world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), new BlockState(Blocks.ENDER_CHEST , ImmutableMap.<IProperty<?>, Comparable<?>>builder().put(EnderChestBlock.FACING, Direction.byIndex(2)).put(EnderChestBlock.WATERLOGGED, false).build()).with(EnderChestBlock.WATERLOGGED, false).with(EnderChestBlock.FACING, (player.getHorizontalFacing().getIndex() <= 1) ? Direction.byIndex((int)Math.random() * 4 + 1) : player.getHorizontalFacing()));
			//} catch(Exception e) {e.printStackTrace();}
			player.world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), EndPortalBlock.getStateById(5134));
			LotteryMachine.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketLotteryMachine(0));
			
		}
		
		//EntityBox entity = new EntityBox(EntityBox.TYPE, world);
		//entity.posX = player.posX;
		//entity.posY = player.posY;
		//entity.posZ = player.posZ;
		//world.addEntity(entity);
		
	}

}
