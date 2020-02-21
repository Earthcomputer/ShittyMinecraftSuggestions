package shittymcsuggestions.worldgen;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModSurfaceBuilders {

    public static final TernarySurfaceConfig BEE_CONFIG = new TernarySurfaceConfig(Blocks.HONEYCOMB_BLOCK.getDefaultState(), Blocks.HONEYCOMB_BLOCK.getDefaultState(), Blocks.HONEYCOMB_BLOCK.getDefaultState());

    public static final BeeSurfaceBuilder BEE = new BeeSurfaceBuilder(TernarySurfaceConfig::deserialize);

    private static void registerSurfaceBuilder(String name, SurfaceBuilder<?> surfaceBuilder) {
        Registry.register(Registry.SURFACE_BUILDER, new Identifier(ShittyMinecraftSuggestions.MODID, name), surfaceBuilder);
    }

    public static void register() {
        registerSurfaceBuilder("bee", BEE);
    }

}
