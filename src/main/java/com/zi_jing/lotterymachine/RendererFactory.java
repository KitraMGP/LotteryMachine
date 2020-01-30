package com.zi_jing.lotterymachine;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RendererFactory<T extends Entity> implements IRenderFactory<T> {

	private final Class<? extends EntityRenderer<T>> renderClass;

	public RendererFactory(Class<? extends EntityRenderer<T>> renderClass) {
		this.renderClass = renderClass;
	}
	
	@Override
	public EntityRenderer<? super T> createRenderFor(EntityRendererManager manager) {
		try {
			return renderClass.getConstructor(EntityRendererManager.class).newInstance(manager);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
