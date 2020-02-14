package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;

/**
 * chicken skin, sheep model
 */
public class ChickenSheepEntity extends ChickenEntity {
    public ChickenSheepEntity(EntityType<? extends ChickenSheepEntity> entityType, World world) {
        super(entityType, world);
    }
}
