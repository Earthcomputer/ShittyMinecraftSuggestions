package shittymcsuggestions.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import shittymcsuggestions.ShittyMinecraftSuggestions;

import java.util.Random;
import java.util.function.Function;

public class McDonaldsFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
    public McDonaldsFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    protected int getSeedModifier() {
        return 4564563;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public String getName() {
        return ShittyMinecraftSuggestions.MODID + ":mcdonalds";
    }

    @Override
    public int getRadius() {
        return 2;
    }

    @Override
    public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkX, int chunkZ, Biome biome) {
        return chunkGenerator instanceof OverworldChunkGenerator && super.shouldStartAt(biomeAccess, chunkGenerator, random, chunkX, chunkZ, biome);
    }

    public static class Start extends StructureStart {

        public Start(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
            super(feature, chunkX, chunkZ, box, references, l);
        }

        @Override
        public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome) {
            int blockX = chunkX * 16;
            int blockZ = chunkZ * 16;

            BlockRotation rotation = BlockRotation.random(random);
            int oppositeX = 31, oppositeZ = 31;
            if (rotation == BlockRotation.CLOCKWISE_90) {
                oppositeX = -31;
            } else if (rotation == BlockRotation.CLOCKWISE_180) {
                oppositeX = oppositeZ = -31;
            } else if (rotation == BlockRotation.COUNTERCLOCKWISE_90) {
                oppositeZ = -31;
            }
            BlockBox fullBox = BlockBox.create(blockX, 0, blockZ, blockX + oppositeX, 0, blockZ + oppositeZ);
            int averageY = 0;
            //noinspection SuspiciousNameCombination - yarn derp
            for (BlockPos pos : BlockPos.iterate(fullBox.minX, fullBox.minY, fullBox.minZ, fullBox.maxX, fullBox.maxY, fullBox.maxZ)) {
                averageY += chunkGenerator.getHeightInGround(pos.getX(), pos.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
            }
            averageY /= 32 * 32;

            BlockPos pos = new BlockPos(blockX, averageY, blockZ);
            McDonaldsGenerator.addPieces(structureManager, pos, rotation, children);
            setBoundingBoxFromChildren();
        }

        @Override
        public void generateStructure(IWorld world, ChunkGenerator<?> chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos) {
            super.generateStructure(world, chunkGenerator, random, blockBox, chunkPos);

            BlockPos.Mutable pos = new BlockPos.Mutable();
            int topY = boundingBox.minY;
            for (int x = boundingBox.minX; x <= boundingBox.maxX; x++) {
                for (int z = boundingBox.minZ; z <= boundingBox.maxZ; z++) {
                    pos.set(x, topY, z);
                    if (!world.isAir(pos) && boundingBox.contains(pos)) {
                        for (int y = topY - 1; y > 1; y--) {
                            pos.setY(y);
                            BlockState state = world.getBlockState(pos);
                            if (state.getMaterial().isLiquid()) {
                                world.setBlockState(pos, Blocks.OAK_PLANKS.getDefaultState(), 2);
                                break;
                            } else if (state.getMaterial().isReplaceable()) {
                                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
                            } else if (state.getBlock() == Blocks.GRASS_BLOCK) {
                                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
