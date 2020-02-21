package shittymcsuggestions.dimension;

import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.CavesChunkGenerator;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;

public class HoneyChunkGenerator extends CavesChunkGenerator {
    public HoneyChunkGenerator(World world, BiomeSource biomeSource, CavesChunkGeneratorConfig config) {
        super(world, biomeSource, config);
    }

    @Override
    public int getMaxY() {
        return 255;
    }

    @Override
    public int getSeaLevel() {
        return 63;
    }
}
