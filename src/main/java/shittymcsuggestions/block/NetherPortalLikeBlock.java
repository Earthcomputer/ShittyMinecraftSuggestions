package shittymcsuggestions.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Set;

public class NetherPortalLikeBlock extends NetherPortalBlock {

    private final Block frameBlock;
    private final Set<Block> replaceableBlocks;

    public NetherPortalLikeBlock(Block frameBlock, Set<Block> replaceableBlocks, Settings settings) {
        super(settings);
        this.frameBlock = frameBlock;
        this.replaceableBlocks = replaceableBlocks;
    }

    @Override
    public void scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        // no spawning zombie pigmen
    }

    @Override
    public boolean createPortalAt(IWorld world, BlockPos pos) {
        AreaHelper areaHelper = doCreateAreaHelper(world, pos);
        if (areaHelper != null) {
            areaHelper.createPortal();
            return true;
        } else {
            return false;
        }
    }

    private AreaHelper doCreateAreaHelper(IWorld world, BlockPos pos) {
        AreaHelper areaHelper = createAreaHelper0(world, pos, Direction.Axis.X);
        if (areaHelper.isValid() && areaHelper.foundPortalBlocks == 0)
            return areaHelper;

        areaHelper = createAreaHelper0(world, pos, Direction.Axis.Z);
        if (areaHelper.isValid() && areaHelper.foundPortalBlocks == 0)
            return areaHelper;

        return null;
    }

    @Deprecated // don't accidentally call the vanilla method
    @Override
    public NetherPortalBlock.AreaHelper createAreaHelper(IWorld world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    protected AreaHelper createAreaHelper0(IWorld world, BlockPos pos, Direction.Axis axis) {
        return new AreaHelper(frameBlock, this, replaceableBlocks, world, pos, axis);
    }

    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        Direction.Axis updateAxis = direction.getAxis();
        Direction.Axis portalAxis = blockState.get(AXIS);
        boolean perpendicular = portalAxis != updateAxis && updateAxis.isHorizontal();
        return !perpendicular && blockState2.getBlock() != this && !(createAreaHelper0(iWorld, blockPos, portalAxis)).wasAlreadyValid() ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Deprecated // to stop you accidentally calling the wrong method from the superclass
    public static BlockPattern.Result findPortal(IWorld world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
            onEntityInPortal(world, pos, state, entity);
        }
    }

    protected void onEntityInPortal(World world, BlockPos pos, BlockState state, Entity entity) {
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        SoundEvent portalSound = getPortalSound();
        if (portalSound != null && rand.nextInt(100) == 0) {
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, portalSound, SoundCategory.BLOCKS, 0.5f, rand.nextFloat() * 0.4f + 0.8f, false);
        }

        ParticleEffect particleType = getPortalParticleType();
        if (particleType != null) {
            for (int i = 0; i < 4; ++i) {
                double x = (double)pos.getX() + rand.nextFloat();
                double y = (double)pos.getY() + rand.nextFloat();
                double z = (double)pos.getZ() + rand.nextFloat();
                double xVel = (rand.nextFloat() - 0.5) * 0.5;
                double yVel = (rand.nextFloat() - 0.5) * 0.5;
                double zVel = (rand.nextFloat() - 0.5) * 0.5;
                int sideOffset = rand.nextInt(2) * 2 - 1;
                if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
                    x = pos.getX() + 0.5 + 0.25 * sideOffset;
                    xVel = rand.nextFloat() * 2 * sideOffset;
                } else {
                    z = pos.getZ() + 0.5 + 0.25 * sideOffset;
                    zVel = rand.nextFloat() * 2 * sideOffset;
                }

                world.addParticle(ParticleTypes.PORTAL, x, y, z, xVel, yVel, zVel);
            }
        }
    }

    protected SoundEvent getPortalSound() {
        return null;
    }

    protected ParticleEffect getPortalParticleType() {
        return null;
    }

    public static class AreaHelper {
        protected final Block frameBlock;
        protected final NetherPortalLikeBlock portalBlock;
        protected final Set<Block> replaceableBlocks;
        protected final IWorld world;
        protected final Direction.Axis axis;
        protected final Direction negativeDir;
        protected final Direction positiveDir;
        protected int foundPortalBlocks;
        protected BlockPos lowerCorner;
        protected int height;
        protected int width;

        public AreaHelper(Block frameBlock, NetherPortalLikeBlock portalBlock, Set<Block> replaceableBlocks, IWorld world, BlockPos pos, Direction.Axis axis) {
            this.frameBlock = frameBlock;
            this.portalBlock = portalBlock;
            this.replaceableBlocks = replaceableBlocks;
            this.world = world;
            this.axis = axis;
            if (axis == Direction.Axis.X) {
                this.positiveDir = Direction.EAST;
                this.negativeDir = Direction.WEST;
            } else {
                this.positiveDir = Direction.NORTH;
                this.negativeDir = Direction.SOUTH;
            }

            BlockPos originalPos = pos;
            while (pos.getY() > originalPos.getY() - 21 && pos.getY() > 0 && validStateInsidePortal(world.getBlockState(pos.down()))) {
                pos = pos.down();
            }

            int distanceToPositiveEdge = distanceToPortalEdge(pos, positiveDir) - 1;
            if (distanceToPositiveEdge >= 0) {
                lowerCorner = pos.offset(positiveDir, distanceToPositiveEdge);
                width = distanceToPortalEdge(lowerCorner, negativeDir);
                if (width < 2 || width > 21) {
                    lowerCorner = null;
                    width = 0;
                }
            }

            if (lowerCorner != null) {
                height = findHeight();
            }

        }

        protected int distanceToPortalEdge(BlockPos pos, Direction dir) {
            int distance;
            for (distance = 0; distance < 22; distance++) {
                BlockPos offsetPos = pos.offset(dir, distance);
                if (!validStateInsidePortal(world.getBlockState(offsetPos)) || world.getBlockState(offsetPos.down()).getBlock() != frameBlock) {
                    break;
                }
            }

            Block block = world.getBlockState(pos.offset(dir, distance)).getBlock();
            return block == frameBlock ? distance : 0;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        protected int findHeight() {
            outer:
            for (height = 0; height < 21; height++) {
                for (int dx = 0; dx < width; dx++) {
                    BlockPos cornerPos = lowerCorner.offset(negativeDir, dx).up(height);
                    BlockState cornerState = world.getBlockState(cornerPos);
                    if (!validStateInsidePortal(cornerState)) {
                        break outer;
                    }

                    Block block = cornerState.getBlock();
                    if (block == portalBlock) {
                        foundPortalBlocks++;
                    }

                    if (dx == 0) {
                        block = world.getBlockState(cornerPos.offset(positiveDir)).getBlock();
                        if (block != frameBlock) {
                            break outer;
                        }
                    } else if (dx == width - 1) {
                        block = world.getBlockState(cornerPos.offset(negativeDir)).getBlock();
                        if (block != frameBlock) {
                            break outer;
                        }
                    }
                }
            }

            for (int dx = 0; dx < width; dx++) {
                if (world.getBlockState(lowerCorner.offset(negativeDir, dx).up(height)).getBlock() != frameBlock) {
                    height = 0;
                    break;
                }
            }

            if (height <= 21 && height >= 3) {
                return height;
            } else {
                lowerCorner = null;
                width = 0;
                height = 0;
                return 0;
            }
        }

        protected boolean validStateInsidePortal(BlockState state) {
            Block block = state.getBlock();
            return state.isAir() || replaceableBlocks.contains(block) || block == portalBlock;
        }

        public boolean isValid() {
            return lowerCorner != null && width >= 2 && width <= 21 && height >= 3 && height <= 21;
        }

        public void createPortal() {
            for (int dx = 0; dx < width; dx++) {
                BlockPos offsetPos = lowerCorner.offset(negativeDir, dx);

                for (int dy = 0; dy < height; dy++) {
                    world.setBlockState(offsetPos.up(dy), portalBlock.getDefaultState().with(AetherPortalBlock.AXIS, axis), 18);
                }
            }

        }

        private boolean portalAlreadyExisted() {
            return foundPortalBlocks >= width * height;
        }

        public boolean wasAlreadyValid() {
            return isValid() && portalAlreadyExisted();
        }
    }
}
