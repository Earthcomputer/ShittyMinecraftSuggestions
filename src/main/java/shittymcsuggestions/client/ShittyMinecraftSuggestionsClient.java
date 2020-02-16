package shittymcsuggestions.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.client.entity.*;
import shittymcsuggestions.entity.ModEntities;

@Environment(EnvType.CLIENT)
public class ShittyMinecraftSuggestionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ModEntities.EXPLODING_ARROW, (dispatcher, ctx) -> new ExplodingArrowEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.CHICKEN_SHEEP, (dispatcher, ctx) -> new ChickenSheepEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.COW_PIG, (dispatcher, ctx) -> new CowPigEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.PIG_COW, (dispatcher, ctx) -> new PigCowEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.SHEEP_CHICKEN, (dispatcher, ctx) -> new SheepChickenEntityRenderer(dispatcher));

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.UNLIT_TORCH, ModBlocks.UNLIT_WALL_TORCH);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AETHER_PORTAL, RenderLayer.getTranslucent());
    }

}
