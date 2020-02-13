package shittymcsuggestions.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.client.entity.ExplodingArrowEntityRenderer;
import shittymcsuggestions.entity.ModEntities;

@Environment(EnvType.CLIENT)
public class ShittyMinecraftSuggestionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ModEntities.EXPLODING_ARROW, (dispatcher, ctx) -> new ExplodingArrowEntityRenderer(dispatcher));

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.UNLIT_TORCH, ModBlocks.UNLIT_WALL_TORCH);
    }

}
