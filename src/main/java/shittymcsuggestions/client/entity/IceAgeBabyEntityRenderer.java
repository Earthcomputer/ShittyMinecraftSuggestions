package shittymcsuggestions.client.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.entity.IceAgeBabyEntity;
import shittymcsuggestions.entity.LoraxEntity;

public class IceAgeBabyEntityRenderer extends MobEntityRenderer<IceAgeBabyEntity, IceAgeBabyEntityModel> {
    private static final Identifier SKIN = new Identifier(ShittyMinecraftSuggestions.MODID, "textures/entity/ice_age_baby.png");

    public IceAgeBabyEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new IceAgeBabyEntityModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(IceAgeBabyEntity entity) { return SKIN; }
}
