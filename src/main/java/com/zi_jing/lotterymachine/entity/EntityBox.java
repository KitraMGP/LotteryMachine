package com.zi_jing.lotterymachine.entity;

import java.util.ArrayList;

import com.zi_jing.lotterymachine.LotteryMachine;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

public class EntityBox extends LivingEntity {

	public static final String NAME = "treasure_box";
	public static final EntityType<EntityBox> TYPE = EntityType.Builder.<EntityBox>create(EntityBox::new, EntityClassification.MISC).size(5F, 5F).build(LotteryMachine.MODID + ":" + NAME);
	
	static {
		TYPE.setRegistryName(LotteryMachine.MODID, NAME);
	}
	
	public EntityBox(EntityType<? extends LivingEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Override
	protected void registerData() {
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return new SSpawnObjectPacket(this);
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return new ArrayList<ItemStack>();
	}

	@Override
	public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
	}

	@Override
	public HandSide getPrimaryHand() {
		return HandSide.RIGHT;
	}
}
