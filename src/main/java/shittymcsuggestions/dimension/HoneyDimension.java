package shittymcsuggestions.dimension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.biome.source.FixedBiomeSourceConfig;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;

public class HoneyDimension extends Dimension {

    private static final Vec3d FOG_COLOR = new Vec3d(0.54, 0.44, 0.16);

    public HoneyDimension(World world, DimensionType type) {
        super(world, type, 0.1f);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        FlatChunkGeneratorConfig config = FlatChunkGeneratorConfig.getDefaultConfig();
        FixedBiomeSourceConfig biomeConfig = BiomeSourceType.FIXED.getConfig(world.getLevelProperties()).setBiome(Biomes.JUNGLE);
        return ChunkGeneratorType.FLAT.create(world, BiomeSourceType.FIXED.applyConfig(biomeConfig), config);
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean bl) {
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int i, int j, boolean bl) {
        return null;
    }

    @Override
    public float getSkyAngle(long l, float f) {
        return 0.5f;
    }

    @Override
    public boolean hasVisibleSky() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float f, float g) {
        return FOG_COLOR;
    }

    @Override
    public boolean canPlayersSleep() {
        return false;
    }

    @Override
    public boolean isFogThick(int i, int j) {
        return false;
    }

    @Override
    public DimensionType getType() {
        return ModDimensions.BEE;
    }
}
