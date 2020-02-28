package shittymcsuggestions.dimension;

import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import shittymcsuggestions.block.ICustomPortal;

import java.util.Comparator;

public class CustomPortalForcer<T extends Block & ICustomPortal> implements EntityPlacer {

    protected static final int PREFERENCE_ILLEGAL_SPAWN = -1;
    protected static final int PREFERENCE_OBSTRUCTED = 0;
    protected static final int PREFERENCE_UNEVEN_FLOOR = 1;
    protected static final int PREFERENCE_GOOD = 2;

    protected final PointOfInterestType poiType;
    protected final T portalBlock;
    protected final Block frameBlock;
    protected final double scale;

    public CustomPortalForcer(PointOfInterestType poiType, T portalBlock, Block frameBlock) {
        this(poiType, portalBlock, frameBlock, 1);
    }

    public CustomPortalForcer(PointOfInterestType poiType, T portalBlock, Block frameBlock, double scale) {
        this.poiType = poiType;
        this.portalBlock = portalBlock;
        this.frameBlock = frameBlock;
        this.scale = scale;
    }

    @Override
    public BlockPattern.TeleportTarget placeEntity(Entity teleported,
                                                   ServerWorld destination,
                                                   Direction portalDir,
                                                   double horizontalOffset,
                                                   double verticalOffset) {
        if (scale != 1)
            teleported.refreshPositionAndAngles(teleported.getX() * scale, teleported.getY(), teleported.getZ() * scale, teleported.yaw, teleported.pitch);

        BlockPattern.TeleportTarget result = findPortal(teleported, destination, portalDir, horizontalOffset, verticalOffset);
        if (result == null && canCreatePortal(teleported)) {
            searchAndCreatePortal(teleported, destination);
            result = findPortal(teleported, destination, portalDir, horizontalOffset, verticalOffset);
        }

        if (result == null) {
            result = new BlockPattern.TeleportTarget(teleported.getPos(), teleported.getVelocity(), (int) teleported.yaw);
        }

        return result;
    }

    protected boolean canCreatePortal(Entity teleported) {
        return teleported instanceof PlayerEntity;
    }

    protected BlockPattern.TeleportTarget findPortal(Entity teleported,
                                                     ServerWorld destination,
                                                     Direction portalDir,
                                                     double horizontalOffset,
                                                     double verticalOffset) {
        BlockPos centerPos = new BlockPos(teleported);
        PointOfInterestStorage poiStorage = destination.getPointOfInterestStorage();
        poiStorage.method_22439(destination, centerPos, 128);
        return poiStorage.method_22383(it -> it == poiType, centerPos, 128, PointOfInterestStorage.OccupationStatus.ANY)
                .min(Comparator.<PointOfInterest>comparingDouble(it -> it.getPos().getSquaredDistance(centerPos))
                        .thenComparingInt(it -> it.getPos().getY()))
                .map(poi -> {
                    BlockPos pos = poi.getPos();
                    destination.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(pos), 3, pos);
                    BlockPattern.Result pattern = portalBlock.resolvePortal(destination, pos);
                    return pattern.getTeleportTarget(portalDir, pos, verticalOffset, teleported.getVelocity(), horizontalOffset);
                })
                .orElse(null);
    }

    /**
     * Gets the preference value for a portal to spawn in a given position. The location within range with the highest
     * preference is always chosen for portal spawning. This method may freely change the input portalPos to save on
     * BlockPos allocations.
     */
    protected int getPortalSpawnPreference(ServerWorld destination, BlockPos.Mutable portalPos, Direction.Axis axis) {
        int x = portalPos.getX();
        int y = portalPos.getY();
        int z = portalPos.getZ();
        if (y >= destination.getEffectiveHeight() - 5)
            return PREFERENCE_ILLEGAL_SPAWN;

        int mx = axis == Direction.Axis.X ? 0 : 1;
        int mz = axis == Direction.Axis.X ? 1 : 0;
        for (int dx = 0; dx < 4; dx++) {
            for (int dy = 1; dy < 5; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockState state = destination.getBlockState(portalPos.set(x + dx * mx + dz * mz, y + dy, z + dx * mz + dz * mx));
                    if (!state.getMaterial().isReplaceable() || state.getMaterial().isLiquid()) {
                        return PREFERENCE_OBSTRUCTED;
                    }
                }
            }
        }

        int groundCount = 0;
        for (int dx = 0; dx < 4; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (destination.getBlockState(portalPos.set(x + dx * mx + dz * mz, y, z + dx * mz + dz * mx)).getMaterial().isSolid()) {
                    groundCount++;
                }
            }
        }
        if (groundCount < 6)
            return PREFERENCE_OBSTRUCTED;
        else if (groundCount < 12)
            return PREFERENCE_UNEVEN_FLOOR;
        else
            return PREFERENCE_GOOD;
    }

    protected void searchAndCreatePortal(Entity teleported, ServerWorld destination) {
        int centerX = MathHelper.floor(teleported.getX());
        int centerZ = MathHelper.floor(teleported.getZ());

        double minDistanceSq = Double.POSITIVE_INFINITY;
        int maxPreference = PREFERENCE_ILLEGAL_SPAWN;
        BlockPos.Mutable bestPortalPos = new BlockPos.Mutable(teleported);
        Direction.Axis bestPortalAxis = Direction.Axis.X;
        final Direction.Axis[] allowedAxes = {Direction.Axis.X, Direction.Axis.Z};

        BlockPos.Mutable portalPos = new BlockPos.Mutable();
        for (int dx = -16; dx <= 16; dx++) {
            for (int dz = -16; dz <= 16; dz++) {
                for (int y = 0; y < destination.getEffectiveHeight(); y++) {
                    for (Direction.Axis axis : allowedAxes) {
                        int preference = getPortalSpawnPreference(destination, portalPos.set(centerX + dx, y, centerZ + dz), axis);
                        if (preference >= maxPreference) {
                            double distanceSq = teleported.squaredDistanceTo(centerX + dx, y, centerZ + dz);
                            if (preference > maxPreference || distanceSq < minDistanceSq) {
                                minDistanceSq = distanceSq;
                                maxPreference = preference;
                                bestPortalPos.set(centerX + dx, y, centerZ + dz);
                                bestPortalAxis = axis;
                            }
                        }
                    }
                }
            }
        }

        createPortal(destination, bestPortalPos, bestPortalAxis, maxPreference);
    }

    protected void createPortal(ServerWorld destination, BlockPos portalPos, Direction.Axis axis, int preference) {
        if (preference == PREFERENCE_ILLEGAL_SPAWN)
            return;

        BlockState frameState = frameBlock.getDefaultState();
        BlockState portalState = portalBlock.getDefaultState().with(NetherPortalBlock.AXIS, axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);

        Direction horizontalDir = axis == Direction.Axis.X ? Direction.SOUTH : Direction.EAST;
        Direction portalDir = horizontalDir.rotateYClockwise();
        for (int x = 0; x < 4; x++) {
            for (int z = -1; z <= 1; z++) {
                if (z == 0 || preference == PREFERENCE_OBSTRUCTED) {
                    BlockPos bottomPos = portalPos.offset(horizontalDir, x).offset(portalDir, z);
                    for (int y = 0; y < 5; y++) {
                        BlockPos pos = bottomPos.up(y);
                        if (z != 0 && y > 0) {
                            destination.setBlockState(pos, Blocks.AIR.getDefaultState(), 18);
                        } else if (x == 0 || x == 3 || y == 0 || y == 4) {
                            destination.setBlockState(pos, frameState, 18);
                        } else {
                            destination.setBlockState(pos, portalState, 18);
                        }
                    }
                }
            }
        }
    }

}
