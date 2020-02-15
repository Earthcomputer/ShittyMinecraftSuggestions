package shittymcsuggestions.entity;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModEntities {

    public static final EntityType<ExplodingArrowEntity> EXPLODING_ARROW = FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<ExplodingArrowEntity>) ExplodingArrowEntity::new)
            .size(EntityDimensions.fixed(0.5f, 0.5f))
            .build();
    public static final EntityType<ChickenSheepEntity> CHICKEN_SHEEP = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, ChickenSheepEntity::new)
            .size(EntityType.SHEEP.getDimensions())
            .build();
    public static final EntityType<CowPigEntity> COW_PIG = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, CowPigEntity::new)
            .size(EntityType.PIG.getDimensions())
            .build();
    public static final EntityType<PigCowEntity> PIG_COW = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, PigCowEntity::new)
            .size(EntityType.COW.getDimensions())
            .build();
    public static final EntityType<SheepChickenEntity> SHEEP_CHICKEN = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, SheepChickenEntity::new)
            .size(EntityType.CHICKEN.getDimensions())
            .build();

    private static void registerEntity(String name, EntityType<?> entity) {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(ShittyMinecraftSuggestions.MODID, name), entity);
    }

    public static void register() {
        registerEntity("exploding_arrow", EXPLODING_ARROW);
        registerEntity("chicken_sheep", CHICKEN_SHEEP);
        registerEntity("cow_pig", COW_PIG);
        registerEntity("pig_cow", PIG_COW);
        registerEntity("sheep_chicken", SHEEP_CHICKEN);
    }

}
