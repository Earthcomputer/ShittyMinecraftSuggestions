package shittymcsuggestions.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import shittymcsuggestions.block.ModBlocks;

import java.util.Random;
import java.util.function.Function;

public class BeeSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState HONEYCOMB_BLOCK = Blocks.HONEYCOMB_BLOCK.getDefaultState();
    private static final BlockState HONEY_BLOCK = Blocks.HONEY_BLOCK.getDefaultState();
    private static final BlockState COMPRESSED_HONEY = ModBlocks.COMPRESSED_HONEY.getDefaultState();
    private long seed;
    private OctavePerlinNoiseSampler beachNoise;

    public BeeSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function) {
        super(function);
    }

    @Override
    public void generate(Random rand,
                         Chunk chunk,
                         Biome biome,
                         int x, int z,
                         int height,
                         double noise,
                         BlockState defaultBlock, BlockState defaultFluid,
                         int seaLevel,
                         long seed,
                         TernarySurfaceConfig surfaceBlocks) {
        int highestBottomSurfaceBlock = seaLevel + 1;
        int xInChunk = x & 15;
        int zInChunk = z & 15;
        final double beachNoiseScale = 1.0 / 32;
        boolean isBeach = this.beachNoise.sample(x * beachNoiseScale, z * beachNoiseScale, 0) * 75 + rand.nextDouble() > 0;
        int surfaceBlocksDepth = (int) (noise / 3 + 3 + rand.nextDouble() * 0.25);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int surfaceBlocksPlaced = -1;
        BlockState topSurfaceBlock = HONEYCOMB_BLOCK;
        BlockState bottomSurfaceBlock = HONEYCOMB_BLOCK;

        for(int y = 255; y >= 0; y--) {
            pos.set(xInChunk, y, zInChunk);
            BlockState existingState = chunk.getBlockState(pos);
            if (existingState.getBlock() == null || existingState.isAir()) {
                surfaceBlocksPlaced = -1;
            } else {
                if (existingState.getBlock() == defaultBlock.getBlock()) {
                    if (surfaceBlocksPlaced != -1) {
                        if (surfaceBlocksPlaced > 0) {
                            --surfaceBlocksPlaced;
                            chunk.setBlockState(pos, bottomSurfaceBlock, false);
                        }
                    } else {
                        if (surfaceBlocksDepth <= 0) {
                            topSurfaceBlock = CAVE_AIR;
                            bottomSurfaceBlock = HONEYCOMB_BLOCK;
                        } else if (y >= highestBottomSurfaceBlock - 4 && y <= highestBottomSurfaceBlock + 1) {
                            topSurfaceBlock = HONEYCOMB_BLOCK;
                            bottomSurfaceBlock = HONEYCOMB_BLOCK;
                            if (isBeach) {
                                topSurfaceBlock = HONEY_BLOCK;
                                bottomSurfaceBlock = COMPRESSED_HONEY;
                            }
                        }

                        if (y < highestBottomSurfaceBlock && (topSurfaceBlock == null || topSurfaceBlock.isAir())) {
                            topSurfaceBlock = defaultFluid;
                        }

                        surfaceBlocksPlaced = surfaceBlocksDepth;
                        if (y >= highestBottomSurfaceBlock - 1) {
                            chunk.setBlockState(pos, topSurfaceBlock, false);
                        } else {
                            chunk.setBlockState(pos, bottomSurfaceBlock, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.beachNoise == null) {
            this.beachNoise = new OctavePerlinNoiseSampler(new ChunkRandom(seed), 3, 0);
        }

        this.seed = seed;
    }
}
