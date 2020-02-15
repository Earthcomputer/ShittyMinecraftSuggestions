package shittymcsuggestions.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
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

public class ThiccTorchBlock extends FacingBlock {
    private static final VoxelShape EAST = createCuboidShape(0, 4, 4, 15, 12, 12);
    private static final VoxelShape WEST = createCuboidShape(1, 4, 4, 16, 12, 12);
    private static final VoxelShape UP = createCuboidShape(4, 0, 4, 12, 15, 12);
    private static final VoxelShape DOWN = createCuboidShape(4, 1, 4, 12, 16, 12);
    private static final VoxelShape NORTH = createCuboidShape(4, 4, 1, 12, 12, 16);
    private static final VoxelShape SOUTH = createCuboidShape(4,4, 0, 12, 12, 15);

    public ThiccTorchBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.UP));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return (BlockState)state.with(FACING, mirror.apply((Direction)state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        switch(((Direction)state.get(FACING))) {
            case UP:
            default:
                return UP;
            case DOWN:
                return DOWN;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));
        return blockState.getBlock() == this && blockState.get(FACING) == direction ? (BlockState)this.getDefaultState().with(FACING, direction.getOpposite()) : (BlockState)this.getDefaultState().with(FACING, direction);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 10; i++) {
            Direction dir = state.get(FACING);
            double x, y, z;
            if (dir.getOffsetX() == 0) {
                x = pos.getX() + 0.3 + 0.4 * random.nextDouble();
            } else {
                x = pos.getX() + 0.5 + 0.4 * dir.getOffsetX() + 0.3 * dir.getOffsetX() * random.nextDouble();
            }
            if (dir.getOffsetY() == 0) {
                y = pos.getY() + 0.3 + 0.4 * random.nextDouble();
            } else {
                y = pos.getY() + 0.5 + 0.4 * dir.getOffsetY() + 0.3 * dir.getOffsetY() * random.nextDouble();
            }
            if (dir.getOffsetZ() == 0) {
                z = pos.getZ() + 0.3 + 0.4 * random.nextDouble();
            } else {
                z = pos.getZ() + 0.5 + 0.4 * dir.getOffsetZ() + 0.3 * dir.getOffsetZ() * random.nextDouble();
            }
            world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos placedOn = pos.offset(state.get(FACING).getOpposite());
        return coversThiccSquare(state, world, placedOn) && world.getBlockState(placedOn).getBlock() != this;
    }


    public static boolean coversThiccSquare(BlockState state, BlockView world, BlockPos pos) {
        Direction dir = state.get(FACING);
        BlockState blockState = world.getBlockState(pos);
        VoxelShape thiccShape;
        boolean placingOnWall = false;
        if (dir == Direction.DOWN) {
            thiccShape = DOWN;
        } else if (dir == Direction.SOUTH) {
            thiccShape = SOUTH;
        } else if (dir == Direction.EAST) {
            thiccShape = EAST;
        } else if (dir == Direction.WEST) {
            thiccShape = WEST;
        } else if (dir == Direction.NORTH) {
            thiccShape = NORTH;
        } else {
            thiccShape = UP;
            if (blockState.matches(BlockTags.WALLS)) {
                placingOnWall = true;
            }
        }
        VoxelShape THICC_TORCH_SQUARE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), thiccShape, BooleanBiFunction.ONLY_FIRST);
        return placingOnWall || (!blockState.matches(BlockTags.LEAVES) && !VoxelShapes.matchesAnywhere(blockState.getCollisionShape(world, pos).getFace(dir), THICC_TORCH_SQUARE_SHAPE, BooleanBiFunction.ONLY_SECOND));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        return facing == state.get(FACING).getOpposite() && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient) {
            if (state.getBlock() == ModBlocks.THICC_TORCH && !entity.isFireImmune()) {
                if (!(entity instanceof PlayerEntity && ((PlayerEntity) entity).isCreative())) {
                    Direction dir = state.get(FACING);
                    if (dir == Direction.DOWN && Math.abs(Math.abs(entity.getBoundingBox().y2) - Math.abs(pos.getY())) <= 1 / 16.0) {
                        entity.setOnFireFor(5);
                    }
                    if (dir == Direction.UP && Math.abs(Math.abs(entity.getBoundingBox().y1) - Math.abs(pos.getY())) >= 15 / 16.0) {
                        entity.setOnFireFor(5);
                    }
                    if (dir == Direction.WEST && Math.abs(Math.abs(entity.getBoundingBox().x2) - Math.abs(pos.getX())) <= 1 / 16.0) {
                        entity.setOnFireFor(5);
                    }
                    if (dir == Direction.EAST && Math.abs(Math.abs(entity.getBoundingBox().x1) - Math.abs(pos.getX())) >= 15 / 16.0) {
                        entity.setOnFireFor(5);
                    }
                    if (dir == Direction.NORTH && Math.abs(Math.abs(entity.getBoundingBox().z2) - Math.abs(pos.getZ())) <= 1 / 16.0) {
                        entity.setOnFireFor(5);
                    }
                    if (dir == Direction.SOUTH && Math.abs(Math.abs(entity.getBoundingBox().z1) - Math.abs(pos.getZ())) >= 15.0 / 16) {
                        entity.setOnFireFor(5);
                    }
                }
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }
}