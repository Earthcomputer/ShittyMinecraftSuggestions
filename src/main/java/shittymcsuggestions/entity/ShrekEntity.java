package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class ShrekEntity extends MobEntity {
    protected ShrekEntity(EntityType<? extends ShrekEntity> type, World world) {
        super(type, world);
    }
}
