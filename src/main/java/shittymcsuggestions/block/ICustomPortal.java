package shittymcsuggestions.block;

import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface ICustomPortal {

    /**
     * The number of ticks the entity has to be in the portal before it teleports
     */
    default int getMaxPortalTime(Entity entity) {
        return entity.getMaxNetherPortalTime();
    }

    /**
     * The number of ticks the entity needs to be outside the portal before it can use the portal again
     */
    default int getPortalCooldown(Entity entity) {
        return entity.getDefaultNetherPortalCooldown();
    }

    /**
     * Called before teleporting the entity, does custom things sensitive to the portal's position,
     * such as saving the portal's angle.
     */
    default void preTeleportEntity(Entity entity, BlockPos portalPos) {
    }

    /**
     * Teleports the entity to the other dimension
     */
    void teleportEntity(Entity entity);

    /**
     * Given a block position inside the portal, find the portal {@link BlockPattern}.
     */
    default BlockPattern.Result resolvePortal(World world, BlockPos pos) {
        return new BlockPattern.Result(pos, Direction.NORTH, Direction.UP, BlockPattern.makeCache(world, true), 1, 1, 1);
    }

}
