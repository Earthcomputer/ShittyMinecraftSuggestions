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
     * Teleports the entity to the other dimension
     */
    void teleportEntity(Entity entity);

    /**
     * Given a block position inside the portal, find the portal {@link BlockPattern}.
     */
    default BlockPattern.Result findPortal(World world, BlockPos pos) {
        return new BlockPattern.Result(pos, Direction.SOUTH, Direction.UP, null, 1, 1, 1);
    }

}
