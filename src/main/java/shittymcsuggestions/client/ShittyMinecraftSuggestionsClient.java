package shittymcsuggestions.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.block.ModFluids;
import shittymcsuggestions.client.entity.*;
import shittymcsuggestions.entity.ModEntities;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ShittyMinecraftSuggestionsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ModEntities.EXPLODING_ARROW, (dispatcher, ctx) -> new ExplodingArrowEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.CHICKEN_SHEEP, (dispatcher, ctx) -> new ChickenSheepEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.COW_PIG, (dispatcher, ctx) -> new CowPigEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.PIG_COW, (dispatcher, ctx) -> new PigCowEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.SHEEP_CHICKEN, (dispatcher, ctx) -> new SheepChickenEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntities.LORAX, (dispatcher, ctx) -> new LoraxEntityRenderer(dispatcher));

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.UNLIT_TORCH, ModBlocks.UNLIT_WALL_TORCH);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AETHER_PORTAL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HONEY_PORTAL, RenderLayer.getTranslucent());

        setupFluidRendering(ModFluids.HONEY, ModFluids.FLOWING_HONEY, "honey", 0x8a7129);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.HONEY, ModFluids.FLOWING_HONEY);
    }

    private static void setupFluidRendering(final Fluid still, final Fluid flowing, final String textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(ShittyMinecraftSuggestions.MODID, "block/" + textureFluidId + "_still");
        final Identifier flowingSpriteId = new Identifier(ShittyMinecraftSuggestions.MODID, "block/" + textureFluidId + "_flow");

        // If they're not already present, add the sprites to the block atlas
        //noinspection deprecation
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            @Override
            public void apply(ResourceManager resourceManager) {
                //noinspection deprecation
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler() {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }

}
