package shittymcsuggestions.entity;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModEntities {

    public static final EntityType<ExplodingArrowEntity> EXPLODING_ARROW = FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<ExplodingArrowEntity>) ExplodingArrowEntity::new)
            .size(EntityDimensions.fixed(0.5f, 0.5f))
            .build();

    private static void registerEntity(String name, EntityType<?> entity) {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(ShittyMinecraftSuggestions.MODID, name), entity);
    }

    public static void register() {
        registerEntity("exploding_arrow", EXPLODING_ARROW);
    }

}
