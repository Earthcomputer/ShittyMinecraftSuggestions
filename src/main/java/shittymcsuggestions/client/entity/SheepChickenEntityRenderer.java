package shittymcsuggestions.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;
import shittymcsuggestions.entity.CowPigEntity;
import shittymcsuggestions.entity.SheepChickenEntity;

@Environment(EnvType.CLIENT)
public class SheepChickenEntityRenderer extends MobEntityRenderer<SheepChickenEntity, ChickenEntityModel<SheepChickenEntity>> {
    private static final Identifier SKIN = new Identifier("shittymcsuggestions:textures/entity/sheep_chicken.png");

    public SheepChickenEntityRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new ChickenEntityModel<>(), 0.3f);
    }

    @Override
    public Identifier getTexture(SheepChickenEntity entity) {
        return SKIN;
    }
}
