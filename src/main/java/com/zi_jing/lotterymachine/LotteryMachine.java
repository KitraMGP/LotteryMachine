package com.zi_jing.lotterymachine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.CommandDispatcher;
import com.zi_jing.lotterymachine.commands.CommandLotteryMachine;
import com.zi_jing.lotterymachine.network.PacketLotteryMachine;

import net.minecraft.command.CommandSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LotteryMachine.MODID)
public class LotteryMachine {
	
	public static final String MODID = "lotterymachine";
	// ÍøÂçÍ¨Ñ¶
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(LotteryMachine.MODID, "main"), 
			() -> PROTOCOL_VERSION, 
			PROTOCOL_VERSION::equals, 
			PROTOCOL_VERSION::equals);
	
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public LotteryMachine() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	// preinit
	private void setup(final FMLCommonSetupEvent event) {
		INSTANCE.registerMessage(0, PacketLotteryMachine.class, PacketLotteryMachine::toBytes, (buf) -> PacketLotteryMachine.fromBytes(buf), PacketLotteryMachine::handle);
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		CommandDispatcher<CommandSource> dispatcher = event.getCommandDispatcher();
		CommandLotteryMachine.register(dispatcher);
	}
	
	public static Logger getLogger() {
		return LOGGER;
	}

}
