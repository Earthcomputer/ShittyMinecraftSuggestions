package shittymcsuggestions.block;

import net.minecraft.entity.Entity;

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

}
