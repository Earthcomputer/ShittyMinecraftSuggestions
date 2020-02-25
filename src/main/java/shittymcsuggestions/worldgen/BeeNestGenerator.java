package shittymcsuggestions.worldgen;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.util.Ellipsoid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BeeNestGenerator {

    private static final int MIN_Y = 80;
    private static final int MAX_Y = 100;

    private static final int LAKE_DEPTH = 6;
    private static final int ANGLE_PARTITION_COUNT = 16;
    private static final int ATTIC_HEIGHT = 9;

    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState WAX = ModBlocks.WAX.getDefaultState();
    private static final BlockState COMPACTED_HONEYCOMB = ModBlocks.COMPACTED_HONEYCOMB_BLOCK.getDefaultState();
    private static final BlockState HONEY = ModBlocks.HONEY.getDefaultState();

    public static void addPieces(BlockPos startingPos, List<StructurePiece> pieces, Random rand) {
        int centerY = MIN_Y + rand.nextInt(1 + MAX_Y - MIN_Y);
        int rx = 16 + rand.nextInt(24);
        int ry = 16 + rand.nextInt(24);
        int rz = 16 + rand.nextInt(24);
        pieces.add(new Piece(new BlockPos(startingPos.getX(), centerY, startingPos.getZ()), rx, ry, rz, rand));
    }

    public static class Piece extends StructurePiece {

        private Ellipsoid waxEllipsoid;
        private Ellipsoid innerEllipsoid;
        private List<Platform> platforms;

        public Piece(BlockPos center, int rx, int ry, int rz, Random rand) {
            super(ModStructures.BEE_NEST_PIECE, 0);
            this.boundingBox = new BlockBox(center.getX() - rx, center.getY() - ry, center.getZ() - rz, center.getX() + 1 + rx, center.getY() + 1 + ry, center.getZ() + 1 + rz);
            calcEllipsoids();
            createPlatforms(rand);
        }

        public Piece(StructureManager structureManager, CompoundTag tag) {
            super(ModStructures.BEE_NEST_PIECE, tag);
            calcEllipsoids();
            ListTag platforms = tag.getList("Platforms", NbtType.COMPOUND);
            this.platforms = new ArrayList<>(platforms.size());
            for (int i = 0; i < platforms.size(); i++)
                this.platforms.add(new Platform(platforms.getCompound(i)));
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            ListTag platforms = new ListTag();
            for (Platform platform : this.platforms)
                platforms.add(platform.toNbt());
            tag.put("Platforms", platforms);
        }

        private void calcEllipsoids() {
            waxEllipsoid = new Ellipsoid(
                    (this.boundingBox.maxX + this.boundingBox.minX) / 2.0,
                    (this.boundingBox.maxY + this.boundingBox.minY) / 2.0,
                    (this.boundingBox.maxZ + this.boundingBox.minZ) / 2.0,
                    (this.boundingBox.maxX - this.boundingBox.minX) / 2.0,
                    (this.boundingBox.maxY - this.boundingBox.minY) / 2.0,
                    (this.boundingBox.maxZ - this.boundingBox.minZ) / 2.0
            );
            double rMin = Math.min(Math.min(waxEllipsoid.getRx(), waxEllipsoid.getRy()), waxEllipsoid.getRz());
            innerEllipsoid = new Ellipsoid(
                    waxEllipsoid.getCenterX(),
                    waxEllipsoid.getCenterY(),
                    waxEllipsoid.getCenterZ(),
                    waxEllipsoid.getRx() * (rMin - 1.01) / rMin,
                    waxEllipsoid.getRy() * (rMin - 1.01) / rMin,
                    waxEllipsoid.getRz() * (rMin - 1.01) / rMin
            );
        }

        private void createPlatforms(Random rand) {
            platforms = new ArrayList<>();
            double[] angleWeights = new double[ANGLE_PARTITION_COUNT];
            Arrays.fill(angleWeights, 1);
            final int minY = boundingBox.minY + LAKE_DEPTH + 3;
            final int maxY = boundingBox.maxY - ATTIC_HEIGHT - 3;
            for (int y = minY; y <= maxY; y += MathHelper.ceil(MathHelper.minusDiv(y, minY - 1, maxY) * 3)) {
                double totalWeight = 0;
                for (int i = 0; i < ANGLE_PARTITION_COUNT; i++)
                    totalWeight += angleWeights[i];
                double randVal = rand.nextDouble() * totalWeight;
                int angle;
                for (angle = 0; angle < ANGLE_PARTITION_COUNT; angle++) {
                    randVal -= angleWeights[angle];
                    if (randVal < 0)
                        break;
                }

                double dy = y - innerEllipsoid.getCenterY();
                double ellipseScale = Math.sqrt(1 - (dy * dy) / (innerEllipsoid.getRy() * innerEllipsoid.getRy()));
                double ellipseSizeX = innerEllipsoid.getRx() * ellipseScale;
                double ellipseSizeZ = innerEllipsoid.getRz() * ellipseScale;
                double actualAngle = (angle + rand.nextDouble()) * Math.PI * 2 / ANGLE_PARTITION_COUNT;
                double centerX = innerEllipsoid.getCenterX() + ellipseSizeX * Math.cos(actualAngle);
                double centerZ = innerEllipsoid.getCenterZ() + ellipseSizeZ * Math.sin(actualAngle);
                double radius = (3 + platformSizeDistribution(rand, MathHelper.minusDiv(y, minY, maxY)) * 10) * (innerEllipsoid.getRx() + innerEllipsoid.getRz()) / 48;
                double rx = Math.max(4, radius + rand.nextDouble() * 2 - 1);
                double rz = Math.max(4, radius + rand.nextDouble() * 2 - 1);
                platforms.add(new Platform(centerX, y, centerZ, rx, rz));

                // eliminate zero weights
                double minWeight = Double.POSITIVE_INFINITY;
                for (int i = 0; i < ANGLE_PARTITION_COUNT; i++)
                    if (angleWeights[i] < minWeight && angleWeights[i] >= 0.01)
                        minWeight = angleWeights[i];
                if (Double.isInfinite(minWeight))
                    minWeight = 1;

                for (int i = 0; i < ANGLE_PARTITION_COUNT; i++) {
                    double weight = angleWeights[(angle + i) % ANGLE_PARTITION_COUNT];
                    if (weight < 0.01)
                        weight = minWeight;
                    // weights decay as you go up
                    weight /= ANGLE_PARTITION_COUNT / 2.0;
                    // give highest extra weight to the opposite side of the ellipse
                    int multiplier = i > ANGLE_PARTITION_COUNT / 2 ? ANGLE_PARTITION_COUNT - i : i;
                    weight *= multiplier;
                    angleWeights[(angle + i) % ANGLE_PARTITION_COUNT] = weight;
                }
            }
        }

        @Override
        public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos chunkPos) {
            placeWaxShell(world, box);
            placePlatforms(world, box);

            return true;
        }

        private void placeWaxShell(IWorld world, BlockBox currentBox) {
            //noinspection SuspiciousNameCombination - yarn derp
            for (BlockPos pos : BlockPos.iterate(currentBox.minX, boundingBox.minY, currentBox.minZ,
                                                 currentBox.maxX, boundingBox.maxY, currentBox.maxZ)) {
                if (waxEllipsoid.contains(pos)) {
                    if (innerEllipsoid.contains(pos)) {
                        if (pos.getY() <= boundingBox.minY + LAKE_DEPTH) {
                            world.setBlockState(pos, HONEY, 2);
                        } else if (pos.getY() == boundingBox.maxY - ATTIC_HEIGHT) {
                            world.setBlockState(pos, COMPACTED_HONEYCOMB, 2);
                        } else {
                            world.setBlockState(pos, CAVE_AIR, 2);
                        }
                    } else {
                        world.setBlockState(pos, WAX, 2);
                    }
                }
            }
        }

        private void placePlatforms(IWorld world, BlockBox currentBox) {
            for (Platform platform : platforms) {
                //noinspection SuspiciousNameCombination - yarn derp
                for (BlockPos pos : BlockPos.iterate(MathHelper.floor(platform.x - platform.rx), platform.y, MathHelper.floor(platform.z - platform.rz),
                                                     MathHelper.ceil(platform.x + platform.rx), platform.y, MathHelper.ceil(platform.z + platform.rz))) {
                    if (currentBox.contains(pos) && waxEllipsoid.contains(pos)) {
                        double dx = pos.getX() - platform.x;
                        double dz = pos.getZ() - platform.z;
                        if ((dx * dx) / (platform.rx * platform.rx) + (dz * dz) / (platform.rz * platform.rz) <= 1) {
                            world.setBlockState(pos, COMPACTED_HONEYCOMB, 2);
                        }
                    }
                }
            }
        }
    }

    private static double platformSizeDistribution(Random rand, double yDelta) {
        return MathHelper.clamp(yDelta + 0.125 * rand.nextGaussian(), 0, 1);
    }

    private static class Platform {
        private double x;
        private int y;
        private double z;
        private double rx, rz;

        public Platform(double x, int y, double z, double rx, double rz) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.rx = rx;
            this.rz = rz;
        }

        public Platform(CompoundTag tag) {
            this.x = tag.getDouble("x");
            this.y = tag.getInt("y");
            this.z = tag.getDouble("z");
            this.rx = tag.getDouble("rx");
            this.rz = tag.getDouble("rz");
        }

        public CompoundTag toNbt() {
            CompoundTag tag = new CompoundTag();
            tag.putDouble("x", x);
            tag.putInt("y", y);
            tag.putDouble("z", z);
            tag.putDouble("rx", rx);
            tag.putDouble("rz", rz);
            return tag;
        }
    }
}
