package shittymcsuggestions.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class CustomOreFeature extends Feature<CustomOreFeatureConfig> {

    public CustomOreFeature(Function<Dynamic<?>, ? extends CustomOreFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random rand, BlockPos pos, CustomOreFeatureConfig config) {
        float angle = rand.nextFloat() * (float) Math.PI;
        float hRadius = (float)config.size / 8.0F;
        int yRadius = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double x1 = pos.getX() + MathHelper.sin(angle) * hRadius;
        double x2 = pos.getX() - MathHelper.sin(angle) * hRadius;
        double z1 = pos.getZ() + MathHelper.cos(angle) * hRadius;
        double z2 = pos.getZ() - MathHelper.cos(angle) * hRadius;
        double y1 = pos.getY() + rand.nextInt(3) - 2;
        double y2 = pos.getY() + rand.nextInt(3) - 2;
        int x = pos.getX() - MathHelper.ceil(hRadius) - yRadius;
        int y = pos.getY() - 2 - yRadius;
        int z = pos.getZ() - MathHelper.ceil(hRadius) - yRadius;
        int size = 2 * (MathHelper.ceil(hRadius) + yRadius);
        int ySize = 2 * (2 + yRadius);

        for (int dx = x; dx <= x + size; dx++) {
            for (int dz = z; dz <= z + size; dz++) {
                if (y <= world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, dx, dz)) {
                    return generateVeinPart(world, rand, config, x1, x2, z1, z2, y1, y2, x, y, z, size, ySize);
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(IWorld world, Random random, CustomOreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int hSize, int ySize) {
        int placedCount = 0;
        BitSet blocksPlaced = new BitSet(hSize * ySize * hSize);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        double[] spheres = new double[config.size * 4];

        // Decide on some spheres
        double sphereX;
        double sphereY;
        double sphereZ;
        double sphereRadius;
        for (int i = 0; i < config.size; ++i) {
            float f = (float)i / (float)config.size;
            sphereX = MathHelper.lerp(f, startX, endX);
            sphereY = MathHelper.lerp(f, startY, endY);
            sphereZ = MathHelper.lerp(f, startZ, endZ);
            sphereRadius = random.nextDouble() * (double)config.size / 16.0D;
            double r = ((double)(MathHelper.sin((float) Math.PI * f) + 1.0F) * sphereRadius + 1.0D) / 2.0D;
            spheres[i * 4] = sphereX;
            spheres[i * 4 + 1] = sphereY;
            spheres[i * 4 + 2] = sphereZ;
            spheres[i * 4 + 3] = r;
        }

        // Prevent gaps in the middle of the vein
        for (int i = 0; i < config.size - 1; ++i) {
            if (spheres[i * 4 + 3] > 0.0D) {
                for (int n = i + 1; n < config.size; ++n) {
                    if (spheres[n * 4 + 3] > 0.0D) {
                        sphereX = spheres[i * 4] - spheres[n * 4];
                        sphereY = spheres[i * 4 + 1] - spheres[n * 4 + 1];
                        sphereZ = spheres[i * 4 + 2] - spheres[n * 4 + 2];
                        sphereRadius = spheres[i * 4 + 3] - spheres[n * 4 + 3];
                        if (sphereRadius * sphereRadius > sphereX * sphereX + sphereY * sphereY + sphereZ * sphereZ) {
                            if (sphereRadius > 0.0D) {
                                spheres[n * 4 + 3] = -1.0D;
                            } else {
                                spheres[i * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        // Place the spheres
        for (int i = 0; i < config.size; ++i) {
            sphereRadius = spheres[i * 4 + 3];
            if (sphereRadius >= 0.0D) {
                sphereX = spheres[i * 4];
                sphereY = spheres[i * 4 + 1];
                sphereZ = spheres[i * 4 + 2];
                int minX = Math.max(MathHelper.floor(sphereX - sphereRadius), x);
                int minY = Math.max(MathHelper.floor(sphereY - sphereRadius), y);
                int minZ = Math.max(MathHelper.floor(sphereZ - sphereRadius), z);
                int maxX = Math.max(MathHelper.floor(sphereX + sphereRadius), minX);
                int maxY = Math.max(MathHelper.floor(sphereY + sphereRadius), minY);
                int maxZ = Math.max(MathHelper.floor(sphereZ + sphereRadius), minZ);

                for(int dx = minX; dx <= maxX; ++dx) {
                    double scaledX = ((double)dx + 0.5D - sphereX) / sphereRadius;
                    if (scaledX * scaledX < 1.0D) {
                        for(int dy = minY; dy <= maxY; ++dy) {
                            double scaledY = ((double)dy + 0.5D - sphereY) / sphereRadius;
                            if (scaledX * scaledX + scaledY * scaledY < 1.0D) {
                                for(int dz = minZ; dz <= maxZ; ++dz) {
                                    double scaledZ = ((double)dz + 0.5D - sphereZ) / sphereRadius;
                                    if (scaledX * scaledX + scaledY * scaledY + scaledZ * scaledZ < 1.0D) {
                                        int index = dx - x + (dy - y) * hSize + (dz - z) * hSize * ySize;
                                        if (!blocksPlaced.get(index)) {
                                            blocksPlaced.set(index);
                                            pos.set(dx, dy, dz);
                                            if (config.customTarget.getPredicate().test(world.getBlockState(pos))) {
                                                world.setBlockState(pos, config.state, 2);
                                                placedCount++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return placedCount > 0;
    }
}
