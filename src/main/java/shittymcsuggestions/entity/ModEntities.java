package shittymcsuggestions.entity;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.dimension.ModDimensions;

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
    public static final EntityType<LoraxEntity> LORAX = FabricEntityTypeBuilder.create(EntityCategory.MONSTER, LoraxEntity::new)
            .size(EntityDimensions.fixed(0.6f, 1f))
            .build();
    public static final EntityType<ShrekEntity> SHREK = FabricEntityTypeBuilder.create(EntityCategory.MONSTER, ShrekEntity::new)
            .size(EntityDimensions.fixed(2, 3.5f))
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
        registerEntity("lorax", LORAX);
        registerEntity("shrek", SHREK);
    }

    public static int getTrackingDistance(Entity entity, int oldDistance) {
        if (entity instanceof BeeEntity && entity.dimension == ModDimensions.BEE) {
            return oldDistance * 2;
        }

        return oldDistance;
    }

}
