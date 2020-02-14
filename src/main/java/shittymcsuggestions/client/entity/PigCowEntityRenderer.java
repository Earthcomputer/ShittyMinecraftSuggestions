package shittymcsuggestions.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.util.Identifier;
import shittymcsuggestions.entity.PigCowEntity;

@Environment(EnvType.CLIENT)
public class PigCowEntityRenderer extends MobEntityRenderer<PigCowEntity, CowEntityModel<PigCowEntity>> {
    private static final Identifier SKIN = new Identifier("shittymcsuggestions:textures/entity/pig_cow.png");

    public PigCowEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new CowEntityModel<>(), 0.7f);
    }

    @Override
    public Identifier getTexture(PigCowEntity entity) {
        return SKIN;
    }
}
