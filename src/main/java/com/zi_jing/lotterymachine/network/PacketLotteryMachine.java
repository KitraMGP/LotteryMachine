package com.zi_jing.lotterymachine.network;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketLotteryMachine {
	
	public int id;
	public ItemStack stack = ItemStack.EMPTY;
	public BlockPos pos;
	
	// 0=服务器->客户端：开始抽奖，显示动画	1=服务器->客户端：金粒不足
	// 2=服务器->客户端：方块位置不可用		3=客户端->服务器：动画结束，发放奖励
	public PacketLotteryMachine(int packetId) {
		this.id = packetId;
	}
	
	public PacketLotteryMachine(int packetId, ItemStack stack) {
		this.id = packetId;
		this.stack = stack;
	}
	
	public PacketLotteryMachine(int packetId, BlockPos pos) {
		this.id = packetId;
		this.pos = pos;
	}
	
	public static PacketLotteryMachine fromBytes(PacketBuffer buf) {
		int packetId = buf.readInt();
		if(packetId == 3) return new PacketLotteryMachine(packetId, buf.readItemStack());
		
		return new PacketLotteryMachine(packetId);
	}
	
	public static void toBytes(PacketLotteryMachine msg, PacketBuffer buf) {
		if(msg.id == 3) {buf.writeInt(msg.id); buf.writeItemStack(msg.stack); return;}
		buf.writeInt(msg.id);
	}
	
	public static void handle(PacketLotteryMachine msg, Supplier<NetworkEvent.Context> ctx) {
		switch(msg.id) {
		case 0:
			if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
				ctx.get().enqueueWork(new Runnable() {
					@Override
					public void run() {
						Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent("§e§l抽奖已开始，祝你好运！"), false);
						Timer timer = new Timer();
						/*
						BlockPos pos = move(Minecraft.getInstance().player);
						int x = pos.getX();
						int y = pos.getY();
						int z = pos.getZ();*/
						
						BlockPos pos = ((BlockRayTraceResult)Minecraft.getInstance().player.func_213324_a(20.0D, 0.0F, false)).getPos();
						move(pos);
						int x = pos.getX();
						int y = pos.getY();
						int z = pos.getZ();
						timer.schedule(new TimerTask() {

							private int time = 0;
							
							@Override
							public void run() {
								time++;
								
								if(time <= 200) {
									Minecraft.getInstance().world.addParticle(ParticleTypes.FLAME, x, y, z, 0.5, 2, 0);
									Minecraft.getInstance().world.addParticle(ParticleTypes.FLAME, x, y, z, -0.5, 2, 0);
									Minecraft.getInstance().world.addParticle(ParticleTypes.FLAME, x, y, z, 0, 2, 0.5);
									Minecraft.getInstance().world.addParticle(ParticleTypes.FLAME, x, y, z, 0, 2, -0.5);
								}
								
								if(time > 200) {
									this.cancel();
								}
							}
							
						}, 10, 10);
						
					}
					
				});
			};
			break;
		case 1:
			if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent("§c你的金粒好像不够了qwq"), true);
			break;
		case 2:
			if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent("§c准心必须指向地面哦"), true);
			break;
		}
		ctx.get().setPacketHandled(true);
	}
	
	private static void move(BlockPos pos) {
		pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
	}
}
