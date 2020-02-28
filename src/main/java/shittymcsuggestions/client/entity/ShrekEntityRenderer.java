package shittymcsuggestions.client.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.entity.ShrekEntity;

public class ShrekEntityRenderer extends MobEntityRenderer<ShrekEntity, ShrekEntityModel> {

    private static final Identifier SKIN = new Identifier(ShittyMinecraftSuggestions.MODID, "textures/entity/shrek.png");

    public ShrekEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new ShrekEntityModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(ShrekEntity entity) {
        return SKIN;
    }
}
