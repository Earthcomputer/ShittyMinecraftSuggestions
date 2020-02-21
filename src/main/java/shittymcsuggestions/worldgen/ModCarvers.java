package shittymcsuggestions.worldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.NetherCaveCarver;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.mixin.CarverAccessor;

public class ModCarvers {

    public static final Carver<ProbabilityConfig> BEE_CAVE = new NetherCaveCarver(ProbabilityConfig::deserialize);
    static {
        //noinspection ConstantConditions
        ((CarverAccessor) BEE_CAVE).setHeightLimit(256);
    }

    private static void registerCarver(String name, Carver<?> carver) {
        Registry.register(Registry.CARVER, new Identifier(ShittyMinecraftSuggestions.MODID, name), carver);
    }

    public static void register() {
        registerCarver("bee_cave", BEE_CAVE);
    }

}
