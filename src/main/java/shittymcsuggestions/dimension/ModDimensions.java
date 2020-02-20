package shittymcsuggestions.dimension;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModDimensions {

    public static final FabricDimensionType BEE = FabricDimensionType.builder()
            .defaultPlacer((oldEntity, destination, portalDir, horizontalOffset, verticalOffset) -> new BlockPattern.TeleportTarget(oldEntity.getPos(), oldEntity.getVelocity(), (int) oldEntity.yaw))
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
