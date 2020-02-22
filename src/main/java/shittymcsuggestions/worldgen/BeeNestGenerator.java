package shittymcsuggestions.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import shittymcsuggestions.block.ModBlocks;

import java.util.List;
import java.util.Random;

public class BeeNestGenerator {

    private static final int MIN_Y = 80;
    private static final int MAX_Y = 100;

    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState WAX = ModBlocks.WAX.getDefaultState();

    public static void addPieces(BlockPos startingPos, List<StructurePiece> pieces, Random rand) {
        int centerY = MIN_Y + rand.nextInt(1 + MAX_Y - MIN_Y);
        int rx = 16 + rand.nextInt(24);
        int ry = 16 + rand.nextInt(24);
        int rz = 16 + rand.nextInt(24);
        pieces.add(new Piece(new BlockPos(startingPos.getX(), centerY, startingPos.getZ()), rx, ry, rz));
    }

    public static class Piece extends StructurePiece {

        public Piece(BlockPos center, int rx, int ry, int rz) {
            super(ModStructures.BEE_NEST_PIECE, 0);
            this.boundingBox = new BlockBox(center.getX() - rx, center.getY() - ry, center.getZ() - rz, center.getX() + 1 + rx, center.getY() + 1 + ry, center.getZ() + 1 + rz);
        }

        public Piece(StructureManager structureManager, CompoundTag tag) {
            super(ModStructures.BEE_NEST_PIECE, tag);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
        }

        @Override
        public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos chunkPos) {
            double centerX = (this.boundingBox.maxX + this.boundingBox.minX) / 2.0;
            double centerY = (this.boundingBox.maxY + this.boundingBox.minY) / 2.0;
            double centerZ = (this.boundingBox.maxZ + this.boundingBox.minZ) / 2.0;
            double rx = (this.boundingBox.maxX - this.boundingBox.minX) / 2.0;
            double ry = (this.boundingBox.maxY - this.boundingBox.minY) / 2.0;
            double rz = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2.0;
            double rMin = Math.min(Math.min(rx, ry), rz);
            // inner ellipse
            double irx = rx * (rMin - 1.01) / rMin;
            double iry = ry * (rMin - 1.01) / rMin;
            double irz = rz * (rMin - 1.01) / rMin;

            //noinspection SuspiciousNameCombination - yarn derp
            for (BlockPos pos : BlockPos.iterate(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ)) {
                double dx = pos.getX() + 0.5 - centerX;
                double dy = pos.getY() + 0.5 - centerY;
                double dz = pos.getZ() + 0.5 - centerZ;
                if ((dx * dx) / (rx * rx) + (dy * dy) / (ry * ry) + (dz * dz) / (rz * rz) <= 1) {
                    if ((dx * dx) / (irx * irx) + (dy * dy) / (iry * iry) + (dz * dz) / (irz * irz) <= 1) {
                        world.setBlockState(pos, AIR, 2);
                    } else {
                        world.setBlockState(pos, WAX, 2);
                    }
                }
            }

            return true;
        }
    }
}
