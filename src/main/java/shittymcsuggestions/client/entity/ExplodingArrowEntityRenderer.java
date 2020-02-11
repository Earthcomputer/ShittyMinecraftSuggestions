package shittymcsuggestions.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

@Environment(EnvType.CLIENT)
public class ExplodingArrowEntityRenderer extends ArrowEntityRenderer {

    public ExplodingArrowEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

}
