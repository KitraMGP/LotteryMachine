package com.zi_jing.lotterymachine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.commands.CommandLotteryMachine;
import com.zi_jing.lotterymachine.entity.EntityBox;
import com.zi_jing.lotterymachine.entity.RenderBox;
import com.zi_jing.lotterymachine.network.PacketLotteryMachine;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(LotteryMachine.MODID)
public class LotteryMachine {
	
	public static final String MODID = "lotterymachine";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(LotteryMachine.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static final Logger LOGGER = LogManager.getLogger();
	
	public LotteryMachine() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void preInit(final FMLCommonSetupEvent event) {
		INSTANCE.registerMessage(0, PacketLotteryMachine.class, PacketLotteryMachine::toBytes, (buf) -> PacketLotteryMachine.fromBytes(buf), PacketLotteryMachine::handle);
	}

	// SidedProxy当场去世
	private void clientInit(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityBox.class, new RendererFactory<EntityBox>(RenderBox.class));
	}
	
	@SubscribeEvent
	public void registerEntityTypes(RegistryEvent.Register<EntityType<? extends Entity>> event) {
		event.getRegistry().register(EntityBox.TYPE);
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		// 我认为这个mod不需要国际化，所以直接hard code进去（逃
		event.getPlayer().sendStatusMessage(new StringTextComponent("§a§l抽奖机已启用。\n§a§l手持金粒使用§r§e§l§n/lotterymachine§r§a§l命令开始抽奖。"), false);
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
