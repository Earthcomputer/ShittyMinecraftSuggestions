package shittymcsuggestions.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import shittymcsuggestions.block.ThiccTorchBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ThiccTorchFeature extends Feature<RandomPatchFeatureConfig> {

    private static final ThreadLocal<List<BlockState>> VALID_STATES = ThreadLocal.withInitial(() -> new ArrayList<>(6));

    public ThiccTorchFeature(Function<Dynamic<?>, ? extends RandomPatchFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random rand, BlockPos pos, RandomPatchFeatureConfig config) {
        BlockState state = config.stateProvider.getBlockState(rand, pos);
        BlockPos centerPos = config.project ? world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos) : pos;

        int count = 0;
        BlockPos.Mutable placementPos = new BlockPos.Mutable();
        List<BlockState> validStates = VALID_STATES.get();
        for (int i = 0; i < config.tries; i++) {
            placementPos.set(centerPos).setOffset(
                    rand.nextInt(config.spreadX + 1) - rand.nextInt(config.spreadX + 1),
                    rand.nextInt(config.spreadY + 1) - rand.nextInt(config.spreadY + 1),
                    rand.nextInt(config.spreadZ + 1) - rand.nextInt(config.spreadZ + 1)
            );
            if (world.isAir(placementPos) || config.canReplace && world.getBlockState(placementPos).getMaterial().isReplaceable()) {
                try {
                    for (Direction dir : Direction.values()) {
                        BlockState placementState = state.with(ThiccTorchBlock.FACING, dir.getOpposite());
                        BlockPos posBelow = placementPos.offset(dir);
                        // Prevent thicc torches generating across chunk borders
                        if (placementPos.getX() >> 4 == posBelow.getX() >> 4 && placementPos.getZ() >> 4 == posBelow.getZ() >> 4) {
                            BlockState stateBelow = world.getBlockState(posBelow);
                            if (placementState.canPlaceAt(world, placementPos) && (config.whitelist.isEmpty() || config.whitelist.contains(stateBelow.getBlock())) && !config.blacklist.contains(stateBelow)) {
                                validStates.add(placementState);
                            }
                        }
                    }
                    if (!validStates.isEmpty()) {
                        config.blockPlacer.method_23403(world, placementPos, validStates.get(rand.nextInt(validStates.size())), rand);
                        count++;
                    }
                } finally {
                    validStates.clear();
                }
            }
        }

        return count > 0;
    }
}
