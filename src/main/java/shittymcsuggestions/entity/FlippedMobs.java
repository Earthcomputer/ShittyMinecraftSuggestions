package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;

public class FlippedMobs {

    public static EntityType<?> getFlippedEntityType(EntityType<?> type) {
        if (type == EntityType.CHICKEN) {
            return ModEntities.CHICKEN_SHEEP;
        } else if (type == EntityType.PIG) {
            return ModEntities.PIG_COW;
        } else if (type == EntityType.COW) {
            return ModEntities.COW_PIG;
        } else if (type == EntityType.SHEEP) {
            return ModEntities.SHEEP_CHICKEN;
        } else {
            return type;
        }
    }

}
