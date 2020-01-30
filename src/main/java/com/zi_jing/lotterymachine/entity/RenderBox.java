package com.zi_jing.lotterymachine.entity;

import com.zi_jing.lotterymachine.LotteryMachine;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderBox extends EntityRenderer<EntityBox>{

	public RenderBox(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBox entity) {
		return new ResourceLocation(LotteryMachine.MODID + ":textures/entity/box.png");
	}
	
}
