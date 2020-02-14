package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.world.World;

/**
 * pig skin, cow model
 */
public class PigCowEntity extends PigEntity {
    public PigCowEntity(EntityType<? extends PigCowEntity> entityType, World world) {
        super(entityType, world);
    }
}
