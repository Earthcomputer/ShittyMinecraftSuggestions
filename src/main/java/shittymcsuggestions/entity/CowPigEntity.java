package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;

/**
 * cow skin, pig model
 */
public class CowPigEntity extends CowEntity {
    public CowPigEntity(EntityType<? extends CowPigEntity> entityType, World world) {
        super(entityType, world);
    }
}
