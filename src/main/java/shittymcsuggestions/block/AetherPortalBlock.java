package shittymcsuggestions.block;

import com.google.common.cache.LoadingCache;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class AetherPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;;
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public AetherPortalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        switch(state.get(AXIS)) {
            case Z:
                return Z_SHAPE;
            case X:
            default:
                return X_SHAPE;
        }
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
            entity.teleport(entity.getX(),2000, entity.getZ());
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,1200));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch(rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((Direction.Axis)state.get(AXIS)) {
                    case Z:
                        return (BlockState)state.with(AXIS, Direction.Axis.X);
                    case X:
                        return (BlockState)state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    public boolean createPortal(IWorld world, BlockPos pos) {
        AetherPortalBlock.AreaHelper areaHelper = this.createAreaHelper(world, pos);
        if (areaHelper != null) {
            areaHelper.createPortal();
            return true;
        } else {
            return false;
        }
    }

    public AetherPortalBlock.AreaHelper createAreaHelper(IWorld world, BlockPos pos) {
        AetherPortalBlock.AreaHelper areaHelper = new AetherPortalBlock.AreaHelper(world, pos, Direction.Axis.X);
        if (areaHelper.isValid() && areaHelper.foundPortalBlocks == 0) {
            return areaHelper;
        } else {
            AetherPortalBlock.AreaHelper areaHelper2 = new AetherPortalBlock.AreaHelper(world, pos, Direction.Axis.Z);
            return areaHelper2.isValid() && areaHelper2.foundPortalBlocks == 0 ? areaHelper2 : null;
        }
    }
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis axis = facing.getAxis();
        Direction.Axis axis2 = (Direction.Axis)state.get(AXIS);
        boolean bl = axis2 != axis && axis.isHorizontal();
        return !bl && neighborState.getBlock() != this && !(new NetherPortalBlock.AreaHelper(world, pos, axis2)).wasAlreadyValid() ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    public static BlockPattern.Result findPortal(IWorld iWorld, BlockPos world) {
        Direction.Axis axis = Direction.Axis.Z;
        AetherPortalBlock.AreaHelper areaHelper = new AetherPortalBlock.AreaHelper(iWorld, world, Direction.Axis.X);
        LoadingCache<BlockPos, CachedBlockPosition> loadingCache = BlockPattern.makeCache(iWorld, true);
        if (!areaHelper.isValid()) {
            axis = Direction.Axis.X;
            areaHelper = new AetherPortalBlock.AreaHelper(iWorld, world, Direction.Axis.Z);
        }

        if (!areaHelper.isValid()) {
            return new BlockPattern.Result(world, Direction.NORTH, Direction.UP, loadingCache, 1, 1, 1);
        } else {
            int[] is = new int[Direction.AxisDirection.values().length];
            Direction direction = areaHelper.negativeDir.rotateYCounterclockwise();
            BlockPos blockPos = areaHelper.lowerCorner.up(areaHelper.getHeight() - 1);
            Direction.AxisDirection[] var8 = Direction.AxisDirection.values();
            int var9 = var8.length;

            int var10;
            for(var10 = 0; var10 < var9; ++var10) {
                Direction.AxisDirection axisDirection = var8[var10];
                BlockPattern.Result result = new BlockPattern.Result(direction.getDirection() == axisDirection ? blockPos : blockPos.offset(areaHelper.negativeDir, areaHelper.getWidth() - 1), Direction.get(axisDirection, axis), Direction.UP, loadingCache, areaHelper.getWidth(), areaHelper.getHeight(), 1);

                for(int i = 0; i < areaHelper.getWidth(); ++i) {
                    for(int j = 0; j < areaHelper.getHeight(); ++j) {
                        CachedBlockPosition cachedBlockPosition = result.translate(i, j, 1);
                        if (!cachedBlockPosition.getBlockState().isAir()) {
                            ++is[axisDirection.ordinal()];
                        }
                    }
                }
            }

            Direction.AxisDirection axisDirection2 = Direction.AxisDirection.POSITIVE;
            Direction.AxisDirection[] var17 = Direction.AxisDirection.values();
            var10 = var17.length;

            for(int var18 = 0; var18 < var10; ++var18) {
                Direction.AxisDirection axisDirection3 = var17[var18];
                if (is[axisDirection3.ordinal()] < is[axisDirection2.ordinal()]) {
                    axisDirection2 = axisDirection3;
                }
            }

            return new BlockPattern.Result(direction.getDirection() == axisDirection2 ? blockPos : blockPos.offset(areaHelper.negativeDir, areaHelper.getWidth() - 1), Direction.get(axisDirection2, axis), Direction.UP, loadingCache, areaHelper.getWidth(), areaHelper.getHeight(), 1);
        }
    }

    public static class AreaHelper {
        private final IWorld world;
        private final Direction.Axis axis;
        private final Direction negativeDir;
        private final Direction positiveDir;
        private int foundPortalBlocks;
        private BlockPos lowerCorner;
        private int height;
        private int width;

        public AreaHelper(IWorld world, BlockPos pos, Direction.Axis axis) {
            this.world = world;
            this.axis = axis;
            if (axis == Direction.Axis.X) {
                this.positiveDir = Direction.EAST;
                this.negativeDir = Direction.WEST;
            } else {
                this.positiveDir = Direction.NORTH;
                this.negativeDir = Direction.SOUTH;
            }

            for(BlockPos blockPos = pos; pos.getY() > blockPos.getY() - 21 && pos.getY() > 0 && this.validStateInsidePortal(world.getBlockState(pos.down())); pos = pos.down()) {
            }

            int i = this.distanceToPortalEdge(pos, this.positiveDir) - 1;
            if (i >= 0) {
                this.lowerCorner = pos.offset(this.positiveDir, i);
                this.width = this.distanceToPortalEdge(this.lowerCorner, this.negativeDir);
                if (this.width < 2 || this.width > 21) {
                    this.lowerCorner = null;
                    this.width = 0;
                }
            }

            if (this.lowerCorner != null) {
                this.height = this.findHeight();
            }

        }

        protected int distanceToPortalEdge(BlockPos pos, Direction dir) {
            int i;
            for(i = 0; i < 22; ++i) {
                BlockPos blockPos = pos.offset(dir, i);
                if (!this.validStateInsidePortal(this.world.getBlockState(blockPos)) || this.world.getBlockState(blockPos.down()).getBlock() != Blocks.GLOWSTONE) {
                    break;
                }
            }

            Block block = this.world.getBlockState(pos.offset(dir, i)).getBlock();
            return block == Blocks.GLOWSTONE ? i : 0;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        protected int findHeight() {
            int i;
            label56:
            for(this.height = 0; this.height < 21; ++this.height) {
                for(i = 0; i < this.width; ++i) {
                    BlockPos blockPos = this.lowerCorner.offset(this.negativeDir, i).up(this.height);
                    BlockState blockState = this.world.getBlockState(blockPos);
                    if (!this.validStateInsidePortal(blockState)) {
                        break label56;
                    }

                    Block block = blockState.getBlock();
                    if (block == ModBlocks.AETHER_PORTAL) {
                        ++this.foundPortalBlocks;
                    }

                    if (i == 0) {
                        block = this.world.getBlockState(blockPos.offset(this.positiveDir)).getBlock();
                        if (block != Blocks.GLOWSTONE) {
                            break label56;
                        }
                    } else if (i == this.width - 1) {
                        block = this.world.getBlockState(blockPos.offset(this.negativeDir)).getBlock();
                        if (block != Blocks.GLOWSTONE) {
                            break label56;
                        }
                    }
                }
            }

            for(i = 0; i < this.width; ++i) {
                if (this.world.getBlockState(this.lowerCorner.offset(this.negativeDir, i).up(this.height)).getBlock() != Blocks.GLOWSTONE) {
                    this.height = 0;
                    break;
                }
            }

            if (this.height <= 21 && this.height >= 3) {
                return this.height;
            } else {
                this.lowerCorner = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        protected boolean validStateInsidePortal(BlockState state) {
            Block block = state.getBlock();
            return state.isAir() || block == Blocks.WATER || block == ModBlocks.AETHER_PORTAL;
        }

        public boolean isValid() {
            return this.lowerCorner != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void createPortal() {
            for(int i = 0; i < this.width; ++i) {
                BlockPos blockPos = this.lowerCorner.offset(this.negativeDir, i);

                for(int j = 0; j < this.height; ++j) {
                    this.world.setBlockState(blockPos.up(j), (BlockState)ModBlocks.AETHER_PORTAL.getDefaultState().with(AetherPortalBlock.AXIS, this.axis), 18);
                }
            }

        }

        private boolean portalAlreadyExisted() {
            return this.foundPortalBlocks >= this.width * this.height;
        }

        public boolean wasAlreadyValid() {
            return this.isValid() && this.portalAlreadyExisted();
        }
    }
}
