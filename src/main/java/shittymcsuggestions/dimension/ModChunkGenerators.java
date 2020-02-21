package shittymcsuggestions.dimension;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.*;
import shittymcsuggestions.ShittyMinecraftSuggestions;

import java.util.function.Supplier;

public class ModChunkGenerators {

    public static final ChunkGeneratorType<CavesChunkGeneratorConfig, CavesChunkGenerator> HONEY = registerChunkGenerator("honey", HoneyChunkGenerator::new, HoneyChunkGeneratorConfig::new, false);

    private static <C extends ChunkGeneratorConfig, T extends ChunkGenerator<C>> ChunkGeneratorType<C, T> registerChunkGenerator(String name, ModChunkGeneratorType.GeneratorFactory<C, T> factory, Supplier<C> config, boolean buffetScreenOption) {
        ChunkGeneratorType<C, T> generator = new ModChunkGeneratorType<>(factory, buffetScreenOption, config);
        Registry.register(Registry.CHUNK_GENERATOR_TYPE, new Identifier(ShittyMinecraftSuggestions.MODID, name), generator);
        return generator;
    }

    public static void register() {
        // load class
    }

}
