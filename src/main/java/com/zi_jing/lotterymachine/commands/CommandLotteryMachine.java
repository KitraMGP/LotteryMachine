package com.zi_jing.lotterymachine.commands;

import java.util.Timer;
import java.util.TimerTask;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

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
		//BlockPos pos = ((BlockRayTraceResult)player.func_213324_a(20.0D, 0.0F, false)).getPos();
		BlockPos pos = getRayTrace(player, player.world);
		if(player.world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() != Blocks.AIR) {player.sendStatusMessage(new StringTextComponent("��c׼�ı���ָ�����Ŷ"), true); return;}
		ItemStack currentStack = player.inventory.mainInventory.get(player.inventory.currentItem);
		if(currentStack.getItem() != Items.GOLD_NUGGET || currentStack.getCount() <= 5) {
			player.sendStatusMessage(new StringTextComponent("§a你的金粒好像不够了qwq"), true);
		} else {
			if(currentStack.getCount() == 5) player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
			if(currentStack.getCount() > 5) player.inventory.mainInventory.set(player.inventory.currentItem, new ItemStack(currentStack.getItem(), currentStack.getCount() - 5));
			player.sendStatusMessage(new StringTextComponent("§a抽奖开始！"), true);
			player.world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), EndPortalBlock.getStateById(5134));
			Timer timer = new Timer();
			Timer timer2 = new Timer();
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					player.server.getWorld(player.dimension).playSound(player, pos, SoundEvents.BLOCK_NOTE_BLOCK_BIT, SoundCategory.PLAYERS, 1f, 1f);
					timer2.schedule(new TimerTask() {

						private int times = 0;
						@Override
						public void run() {
							if(times <= 5000) {
								player.server.getWorld(player.dimension).spawnParticle(ParticleTypes.FLAME, x, y, z, 50, 0, -1, 0, 0.5);
								player.connection.sendPacket(new SPlaySoundPacket(SoundEvents.BLOCK_NOTE_BLOCK_BIT.getRegistryName(), SoundCategory.PLAYERS, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 0.5f, 1f));
								times+=50;
							} else {
								player.server.getWorld(player.dimension).spawnParticle(ParticleTypes.EXPLOSION_EMITTER, x, y + 1, z, 1, 0, 0, 0, 1);
								player.connection.sendPacket(new SPlaySoundPacket(SoundEvents.ENTITY_GENERIC_EXPLODE.getRegistryName(), SoundCategory.PLAYERS, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 0.5f, 1f));
								player.server.getWorld(player.dimension).spawnParticle(ParticleTypes.CLOUD, x, y + 1, z, 100, 0, 0.5, 0, 0.5);
								reward(player, x, y, z);
								this.cancel();
							}
							
						}
						
					}, 0, 50);
				}
				
			}, 0);
		}
		
		
	}
	
	private static BlockPos getRayTrace(ServerPlayerEntity player, World world) {
		Vec3d vec3d = player.getEyePosition(0.0f);
	    Vec3d vec3d1 = player.getLook(0.0f);
	    Vec3d vec3d2 = vec3d.add(vec3d1.x * 20d, vec3d1.y * 20d, vec3d1.z * 20d);
	    return ((BlockRayTraceResult)world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player))).getPos();
	}
	
	private static void reward(ServerPlayerEntity player, int x, int y, int z) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				player.connection.sendPacket(new SPlaySoundPacket(SoundEvents.ENTITY_PLAYER_LEVELUP.getRegistryName(), SoundCategory.PLAYERS, new Vec3d(x, y, z), 0.5f, 1f));
				
			}
			
		}, 1000);
	}

}
