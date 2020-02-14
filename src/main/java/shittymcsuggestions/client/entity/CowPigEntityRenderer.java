package shittymcsuggestions.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;
import shittymcsuggestions.entity.CowPigEntity;

@Environment(EnvType.CLIENT)
public class CowPigEntityRenderer extends MobEntityRenderer<CowPigEntity, PigEntityModel<CowPigEntity>> {
    private static final Identifier SKIN = new Identifier("shittymcsuggestions:textures/entity/cow_pig.png");

    public CowPigEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new PigEntityModel<>(), 0.7f);
    }

    @Override
    public Identifier getTexture(CowPigEntity entity) {
        return SKIN;
    }
}
