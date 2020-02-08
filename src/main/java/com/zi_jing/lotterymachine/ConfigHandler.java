package com.zi_jing.lotterymachine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHandler {
	
	
	
	@SubscribeEvent
	public static void onConfigLoad(ModConfig.Loading event) {
		
	}
	
	@SubscribeEvent
	public static void onConfigReload(ModConfig.ConfigReloading event) {
		
	}
	
	public static class Server {
		public static ForgeConfigSpec CONFIG;
		public static IntValue cost;
		public static ConfigValue<List<String>> common;
		public static IntValue commonChance;
		public static ConfigValue<List<String>> rare;
		public static IntValue rareChance;
		public static ConfigValue<List<String>> legendary;
		
		Server(ForgeConfigSpec.Builder builder) {
			builder.push("main");
			cost = builder.comment("每次抽奖花费的金粒个数").defineInRange("cost", 5, 0, 64);
			builder.pop();
			builder.comment("奖品列表\n格式：方块/物品ID 特殊值 数量\n允许出现重复项，重复可以提高抽中概率\n抽到\"传奇\"的概率无需指定，由Mod自动计算").push("loot table");
			
			commonChance = builder.comment("玩家抽中\"普通\"的概率").defineInRange("chance_common", 70, 0, 100);
			common = builder.comment("普通").define("common", new Supplier<List<String>>() {
				
				@Override
				public List<String> get() {
					ArrayList<String> list = new ArrayList<String>();
					list.add(encode(Items.WHITE_WOOL, 0, 3));
					list.add(encode(Items.WHITE_WOOL, 0, 3));
					list.add(encode(Items.OAK_LOG, 0, 5));
					list.add(encode(Items.BIRCH_LOG, 0, 5));
					list.add(encode(Items.COBBLESTONE, 0, 32));
					list.add(encode(Items.IRON_INGOT, 0, 3));
					list.add(encode(Items.IRON_INGOT, 0, 3));
					list.add(encode(Items.COAL, 0, 4));
					list.add(encode(Items.COAL, 0, 4));
					list.add(encode(Items.GOLD_NUGGET, 0, 10));
					list.add(encode(Items.TORCH, 0, 7));
					list.add(encode(Items.WOODEN_PICKAXE, 20, 1));
					list.add(encode(Items.STONE_SWORD, 30, 1));
					list.add(encode(Items.IRON_PICKAXE, 25, 1));
					list.add(encode(Items.IRON_ORE, 0, 3));
					list.add(encode(Items.IRON_ORE, 0, 3));
					list.add(encode(Items.IRON_ORE, 0, 3));
					list.add(encode(Items.IRON_BOOTS, 10, 1));
					list.add(encode(Items.REDSTONE, 0, 5));
					list.add(encode(Items.DIRT, 0, 64));
					list.add(encode(Items.OAK_PLANKS, 0, 6));
					list.add(encode(Items.COOKED_BEEF, 0, 3));
					list.add(encode(Items.COOKED_CHICKEN, 0, 3));
					list.add(encode(Items.BAKED_POTATO, 0, 3));
					list.add(encode(Items.POTATO, 0, 3));
					list.add(encode(Items.CARROT, 0, 3));
					list.add(encode(Items.APPLE, 0, 5));
					list.add(encode(Items.BOOK, 0, 3));
					list.add(encode(Items.BOW, 10, 1));
					list.add(encode(Items.ARROW, 0, 5));
					return list;
				}
				
			}.get());
			
			rareChance = builder.comment("玩家抽中\"稀有\"的概率").defineInRange("chance_rare", 23, 0, 100);
			rare = builder.comment("稀有").define("rare", new Supplier<List<String>>() {
	
				@Override
				public List<String> get() {
					ArrayList<String> list = new ArrayList<String>();
					list.add(encode(Items.IRON_BLOCK, 0, 3));
					list.add(encode(Items.GOLD_ORE, 0, 5));
					list.add(encode(Items.OAK_LOG, 0, 64));
					list.add(encode(Items.COAL_ORE, 0, 32));
					list.add(encode(Items.DIAMOND, 0, 2));
					list.add(encode(Items.REDSTONE_ORE, 0, 10));
					list.add(encode(Items.NETHER_QUARTZ_ORE, 0, 32));
					list.add(encode(Items.IRON_ORE, 0, 10));
					return list;
				}
				
			}.get());
			
			legendary = builder.comment("传奇").define("legendary", new Supplier<List<String>>() {
	
				@Override
				public List<String> get() {
					ArrayList<String> list = new ArrayList<String>();
					list.add(encode(Items.DIAMOND_SWORD, 12, 1));
					list.add(encode(Items.DIAMOND_ORE, 0, 7));
					list.add(encode(Items.IRON_ORE, 0, 64));
					list.add(encode(Items.COAL_BLOCK, 0, 32));
					list.add(encode(Items.DIAMOND_PICKAXE, 12, 1));
					list.add(encode(Items.ENCHANTED_GOLDEN_APPLE, 0, 16));
					list.add(encode(Items.EXPERIENCE_BOTTLE, 0, 64));
					list.add(encode(Items.DIAMOND_BLOCK, 0, 8));
					return list;
				}
				
			}.get());
			builder.pop();
			CONFIG = builder.build();
		}
	}
	
	public static final ForgeConfigSpec spec;
	public static final Server server;
	static {
		final Pair<Server, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Server::new);
		spec = pair.getValue();
		server = pair.getKey();
	}
	
	private static String encode(Item item, int meta, int count) {
		return item.getRegistryName() + " " + meta + " " + count;
	}
	
	public static void load() {
		final CommentedFileConfig conf = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("lotterymachine.toml")).sync().autosave().writingMode(WritingMode.REPLACE).build();
		conf.load();
		spec.setConfig(conf);
	}
}
