package shittymcsuggestions.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class ThiccTorchBlock extends Block {
    private static final VoxelShape SHAPE = createCuboidShape(4, 0, 4, 12, 16, 12);
    private static final VoxelShape THICC_TORCH_SQUARE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), SHAPE, BooleanBiFunction.ONLY_FIRST);

    public ThiccTorchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        return SHAPE;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 10; i++) {
            double x = (double) pos.getX() + 0.3 + 0.4 * random.nextDouble();
            double y = (double) pos.getY() + 1.0 + 0.3 * random.nextDouble();
            double z = (double) pos.getZ() + 0.3 + 0.4 * random.nextDouble();
            world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return topCoversThiccSquare(world, pos.down()) && world.getBlockState(pos.down()).getBlock() != this;
    }

    public static boolean topCoversThiccSquare(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return !blockState.matches(BlockTags.LEAVES) && !VoxelShapes.matchesAnywhere(blockState.getCollisionShape(world, pos).getFace(Direction.UP), THICC_TORCH_SQUARE_SHAPE, BooleanBiFunction.ONLY_SECOND);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        return facing == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }
}