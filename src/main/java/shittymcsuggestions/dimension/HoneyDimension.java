package shittymcsuggestions.dimension;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.biome.source.FixedBiomeSourceConfig;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import shittymcsuggestions.worldgen.ModBiomes;

public class HoneyDimension extends Dimension {

    private static final Vec3d FOG_COLOR = new Vec3d(0.54, 0.44, 0.16);

    public HoneyDimension(World world, DimensionType type) {
        super(world, type, 0.5f);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        CavesChunkGeneratorConfig config = ModChunkGenerators.HONEY.createSettings();
        config.setDefaultBlock(Blocks.HONEYCOMB_BLOCK.getDefaultState());
        FixedBiomeSourceConfig biomeConfig = BiomeSourceType.FIXED.getConfig(world.getLevelProperties()).setBiome(ModBiomes.BEE);
        return ModChunkGenerators.HONEY.create(world, BiomeSourceType.FIXED.applyConfig(biomeConfig), config);
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean checkMobSpawnValidity) {
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int x, int z, boolean checkMobSpawnValidity) {
        return null;
    }

    @Override
    public float getSkyAngle(long worldTime, float tickDelta) {
        return 0.5f;
    }

    @Override
    public boolean hasVisibleSky() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float skyAngle, float tickDelta) {
        return FOG_COLOR;
    }

    @Override
    public boolean canPlayersSleep() {
        return false;
    }

    @Override
    public boolean isFogThick(int x, int z) {
        return false;
    }

    @Override
    public DimensionType getType() {
        return ModDimensions.BEE;
    }
}
