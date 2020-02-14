package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.world.World;

/**
 * sheep skin, chicken model
 */
public class SheepChickenEntity extends SheepEntity {
    public SheepChickenEntity(EntityType<? extends SheepChickenEntity> entityType, World world) {
        super(entityType, world);
    }
}
