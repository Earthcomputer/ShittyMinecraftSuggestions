package shittymcsuggestions.item;

import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.block.ModFluids;
import shittymcsuggestions.entity.ModEntities;

public class ModItems {

    public static final BlockItem UNLIT_TORCH = new WallStandingBlockItem(ModBlocks.UNLIT_TORCH, ModBlocks.UNLIT_WALL_TORCH,
            new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final Item BEANOS = new BeanosItem(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(20).alwaysEdible().build()));
    public static final Item BEDROCK_SWORD = new SwordItem(BedrockToolMaterial.INSTANCE, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item BEDROCK_PICKAXE = new ModPickaxeItem(BedrockToolMaterial.INSTANCE, 1, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BEDROCK_AXE = new ModAxeItem(BedrockToolMaterial.INSTANCE, 5, -3f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BEDROCK_SHOVEL = new ShovelItem(BedrockToolMaterial.INSTANCE, 1.5f, -3f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BEDROCK_HOE = new HoeItem(BedrockToolMaterial.INSTANCE, -3f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item CHICKEN_NUGGET = new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(FoodComponents.MELON_SLICE));
    public static final Item CHICKEN_SHEEP_SPAWN_EGG = new SpawnEggItem(ModEntities.CHICKEN_SHEEP, 0xffb5b5, 0xe7e7e7, new Item.Settings().group(ItemGroup.MISC));
    public static final Item COW_PIG_SPAWN_EGG = new SpawnEggItem(ModEntities.COW_PIG, 0xdb635f, 0xf0a5a2, new Item.Settings().group(ItemGroup.MISC));
    public static final Item PIG_COW_SPAWN_EGG = new SpawnEggItem(ModEntities.PIG_COW, 0xa1a1a1, 0x443626, new Item.Settings().group(ItemGroup.MISC));
    public static final Item SHEEP_CHICKEN_SPAWN_EGG = new SpawnEggItem(ModEntities.SHEEP_CHICKEN, 0xff0000, 0xa1a1a1, new Item.Settings().group(ItemGroup.MISC));
    public static final Item LORAX_SPAWN_EGG = new SpawnEggItem(ModEntities.LORAX, 0xff7d00, 0xfef364, new Item.Settings().group(ItemGroup.MISC));
    public static final Item LORAX_MOUSTACHE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CHICKEN_BUCKET = new EntityBucketItem(EntityType.CHICKEN, Fluids.EMPTY, (new Item.Settings()).maxCount(1).group(ItemGroup.MISC));
    public static final Item HONEY_BUCKET = new BucketItem(ModFluids.HONEY, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroup.MISC));

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(ShittyMinecraftSuggestions.MODID, name), item);
    }

    public static void register() {
        registerItem("dragon_egg_block", new BlockItem(ModBlocks.DRAGON_EGG_BLOCK,
                new Item.Settings().group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
        registerItem("unlit_torch", UNLIT_TORCH);
        registerItem("beanos", BEANOS);
        registerItem("bedrock_sword", BEDROCK_SWORD);
        registerItem("bedrock_pickaxe", BEDROCK_PICKAXE);
        registerItem("bedrock_axe", BEDROCK_AXE);
        registerItem("bedrock_shovel", BEDROCK_SHOVEL);
        registerItem("bedrock_hoe", BEDROCK_HOE);
        registerItem("chicken_nugget", CHICKEN_NUGGET);
        registerItem("chicken_sheep_spawn_egg", CHICKEN_SHEEP_SPAWN_EGG);
        registerItem("cow_pig_spawn_egg", COW_PIG_SPAWN_EGG);
        registerItem("pig_cow_spawn_egg", PIG_COW_SPAWN_EGG);
        registerItem("sheep_chicken_spawn_egg", SHEEP_CHICKEN_SPAWN_EGG);
        registerItem("lorax_spawn_egg", LORAX_SPAWN_EGG);
        registerItem("lorax_moustache", LORAX_MOUSTACHE);
        registerItem("chicken_bucket", CHICKEN_BUCKET);
        registerItem("honey_bucket", HONEY_BUCKET);
    }

}
