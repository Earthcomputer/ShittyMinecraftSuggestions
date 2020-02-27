package shittymcsuggestions.dimension;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.block.ModBlocks;

public class ModDimensions {

    public static final FabricDimensionType BEE = FabricDimensionType.builder()
            .defaultPlacer(ModBlocks.HONEY_PORTAL.forcer.get())
            .factory(HoneyDimension::new)
            .skyLight(false)
            .buildAndRegister(id("bee"));

    private static Identifier id(String name) {
        return new Identifier(ShittyMinecraftSuggestions.MODID, name);
    }

    public static void register() {
        // Load class
    }

}
