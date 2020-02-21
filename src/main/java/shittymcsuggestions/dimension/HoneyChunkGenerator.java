package shittymcsuggestions.dimension;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.WeightedPicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.CavesChunkGenerator;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HoneyChunkGenerator extends CavesChunkGenerator {

    private static ThreadLocal<List<Integer>> validYCoordsCache = ThreadLocal.withInitial(ArrayList::new);

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

    public void populateEntities(ChunkRegion world) {
        int x = world.getCenterChunkX();
        int z = world.getCenterChunkZ();
        int blockX = x << 4;
        int blockZ = z << 4;
        Biome biome = world.getBiome((new ChunkPos(x, z)).getCenterBlockPos());
        ChunkRandom rand = new ChunkRandom();
        rand.setSeed(world.getSeed(), blockX, blockZ);

        List<Biome.SpawnEntry> spawnEntries = biome.getEntitySpawnList(EntityCategory.CREATURE);
        if (spawnEntries.isEmpty())
            return;

        BlockPos.Mutable spawnPos = new BlockPos.Mutable();
        List<Integer> validYCoords = validYCoordsCache.get();

        while (rand.nextFloat() < biome.getMaxSpawnLimit()) {
            Biome.SpawnEntry entry = WeightedPicker.getRandom(rand, spawnEntries);
            if (!entry.type.isSummonable())
                continue;
            int packSize = entry.minGroupSize + rand.nextInt(1 + entry.maxGroupSize - entry.minGroupSize);
            EntityData data = null;
            int spawnX = blockX + rand.nextInt(16);
            int spawnZ = blockZ + rand.nextInt(16);
            int initialSpawnX = spawnX;
            int initialSpawnZ = spawnZ;
            float entityWidth = entry.type.getWidth();
            double boundingCenterX = MathHelper.clamp(spawnX, blockX + entityWidth, blockX + 16 - entityWidth);
            double boundingCenterZ = MathHelper.clamp(spawnZ, blockZ + entityWidth, blockZ + 16 - entityWidth);

            for (int i = 0; i < packSize; i++) {
                boolean spawned = false;
                for (int j = 0; !spawned && j < 4; j++) {
                    int spawnY;
                    try {
                        for (int y = 1; y < 256; y++) {
                            spawnPos.set(spawnX, y, spawnZ);
                            if (canSpawn(world, spawnPos, boundingCenterX, boundingCenterZ, entry.type, rand)) {
                                validYCoords.add(y);
                            }
                        }
                        spawnY = validYCoords.isEmpty() ? -1 : validYCoords.get(rand.nextInt(validYCoords.size()));
                    } finally {
                        validYCoords.clear();
                    }

                    if (spawnY != -1) {
                        Entity entity;
                        try {
                            //noinspection deprecation it's okay on this line because the mob doesn't actually access the world
                            entity = entry.type.create(world.getWorld());
                        } catch (Exception e) {
                            LogManager.getLogger().warn("Failed to create mob", e);
                            continue;
                        }
                        if (entity instanceof MobEntity) {
                            MobEntity mob = (MobEntity) entity;
                            mob.refreshPositionAndAngles(boundingCenterX, spawnY, boundingCenterZ, rand.nextFloat() * 360, 0);
                            if (mob.canSpawn(world, SpawnType.CHUNK_GENERATION) && mob.canSpawn(world)) {
                                data = mob.initialize(world, world.getLocalDifficulty(new BlockPos(mob)), SpawnType.CHUNK_GENERATION, data, null);
                                world.spawnEntity(mob);
                                spawned = true;
                            }
                        }
                    }
                }
            }

            spawnX += rand.nextInt(5) - rand.nextInt(5);
            spawnZ += rand.nextInt(5) - rand.nextInt(5);
            while (spawnX < blockX || spawnX >= blockX + 16 || spawnZ < blockZ || spawnZ >= blockZ + 16) {
                spawnX = initialSpawnX + rand.nextInt(5) - rand.nextInt(5);
                spawnZ = initialSpawnZ + rand.nextInt(5) - rand.nextInt(5);
            }
        }
    }

    private static boolean canSpawn(IWorld world, BlockPos pos, double boundingCenterX, double boundingCenterZ, EntityType<?> entityType, Random rand) {
        if (!SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, pos, entityType))
            return false;

        if (!world.doesNotCollide(entityType.createSimpleBoundingBox(boundingCenterX, pos.getY(), boundingCenterZ)))
            return false;

        BlockPos posBelow = pos.down();
        boolean isHoneycomb = world.getBlockState(posBelow).getBlock() == Blocks.HONEYCOMB_BLOCK;
        if (isHoneycomb)
            world.setBlockState(posBelow, Blocks.GRASS_BLOCK.getDefaultState(), 18);
        boolean spawnRestrictionAllows = SpawnRestriction.canSpawn(entityType, world, SpawnType.CHUNK_GENERATION, pos, rand);
        if (isHoneycomb)
            world.setBlockState(posBelow, Blocks.HONEYCOMB_BLOCK.getDefaultState(), 18);
        return spawnRestrictionAllows;
    }
}
