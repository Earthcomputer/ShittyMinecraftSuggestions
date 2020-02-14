package shittymcsuggestions.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import shittymcsuggestions.entity.ChickenSheepEntity;

@Environment(EnvType.CLIENT)
public class ChickenSheepEntityRenderer extends MobEntityRenderer<ChickenSheepEntity, ChickenSheepEntityModel<ChickenSheepEntity>> {
    private static final Identifier SKIN = new Identifier("shittymcsuggestions:textures/entity/chicken_sheep.png");

    public ChickenSheepEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new ChickenSheepEntityModel<>(), 0.7f);
    }

    @Override
    public Identifier getTexture(ChickenSheepEntity entity) {
        return SKIN;
    }
}
