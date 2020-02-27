package shittymcsuggestions.block;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.poi.PointOfInterestType;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.mixin.accessor.PointOfInterestTypeAccessor;

import java.util.Set;

public class ModPoiTypes {

    public static final PointOfInterestType HONEY_PORTAL = registerPoi("honey_portal", statesOf(ModBlocks.HONEY_PORTAL));

    private static Set<BlockState> statesOf(Block block) {
        return ImmutableSet.copyOf(block.getStateManager().getStates());
    }

    private static PointOfInterestType registerPoi(String name, Set<BlockState> blocks) {
        PointOfInterestType type = PointOfInterestTypeAccessor.create(ShittyMinecraftSuggestions.MODID + ":" + name, blocks, 0, 0);
        Registry.register(Registry.POINT_OF_INTEREST_TYPE, new Identifier(ShittyMinecraftSuggestions.MODID, name), type);
        return PointOfInterestTypeAccessor.callSetup(type);
    }

    public static void register() {
        // load class
    }

}
