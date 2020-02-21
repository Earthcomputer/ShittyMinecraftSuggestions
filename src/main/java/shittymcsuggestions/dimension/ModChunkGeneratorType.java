package shittymcsuggestions.dimension;

import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;

import java.util.function.Supplier;

public class ModChunkGeneratorType<C extends ChunkGeneratorConfig, T extends ChunkGenerator<C>> extends ChunkGeneratorType<C, T> {

    private final GeneratorFactory<C, T> factory;

    public ModChunkGeneratorType(GeneratorFactory<C, T> factory, boolean buffetScreenOption, Supplier<C> settingsSupplier) {
        super(null, buffetScreenOption, settingsSupplier);
        this.factory = factory;
    }

    @Override
    public T create(World world, BiomeSource biomeSource, C config) {
        return factory.create(world, biomeSource, config);
    }

    @FunctionalInterface
    public interface GeneratorFactory<C extends ChunkGeneratorConfig, T extends ChunkGenerator<C>> {
        T create(World world, BiomeSource biomeSource, C config);
    }
}
