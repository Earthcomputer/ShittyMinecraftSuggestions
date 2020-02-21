package shittymcsuggestions.worldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModBiomes {

    public static final Biome BEE = new BeeBiome();

    private static void registerBiome(String name, Biome biome) {
        Registry.register(Registry.BIOME, new Identifier(ShittyMinecraftSuggestions.MODID, name), biome);
    }

    public static void register() {
        registerBiome("bee", BEE);
    }

}
