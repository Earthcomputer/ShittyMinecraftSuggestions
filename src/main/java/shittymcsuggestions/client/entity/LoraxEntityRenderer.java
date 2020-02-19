package shittymcsuggestions.client.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.entity.LoraxEntity;

public class LoraxEntityRenderer extends MobEntityRenderer<LoraxEntity, LoraxEntityModel> {
    private static final Identifier SKIN = new Identifier(ShittyMinecraftSuggestions.MODID, "textures/entity/lorax.png");

    public LoraxEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new LoraxEntityModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(LoraxEntity entity) {
        return SKIN;
    }
}
