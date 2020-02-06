package com.zi_jing.lotterymachine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.commands.CommandLotteryMachine;
import net.minecraft.command.CommandSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(LotteryMachine.MODID)
public class LotteryMachine {
	
	public static final String MODID = "lotterymachine";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(LotteryMachine.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static final Logger LOGGER = LogManager.getLogger();
	
	public LotteryMachine() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		event.getPlayer().sendStatusMessage(new StringTextComponent("§a§l抽奖机已启用，§a§l输入命令/lotterymachine开始抽奖。\n§c§l注意：使用该命令时，准心不能指向空中或墙上，只能指向地面。"), false);
	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		CommandDispatcher<CommandSource> dispatcher = event.getCommandDispatcher();
		CommandLotteryMachine.register(dispatcher);
	}
	
	public static Logger getLogger() {
		return LOGGER;
	}
}
