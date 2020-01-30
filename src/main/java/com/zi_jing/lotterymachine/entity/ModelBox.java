package com.zi_jing.lotterymachine.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelBox extends EntityModel<EntityBox> {
	private final RendererModel bone;

	public ModelBox() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.cubeList.add(new net.minecraft.client.renderer.model.ModelBox(bone, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
	}
	
	@Override
	public void render(EntityBox entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
	   bone.render(scale);
	}
	
	@Override
	public void setRotationAngles(EntityBox entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		bone.rotateAngleX = headPitch * ((float)Math.PI / 180);
		bone.rotateAngleY = netHeadYaw * ((float)Math.PI / 180);
		bone.rotateAngleZ = 0.0F;
	}
}
