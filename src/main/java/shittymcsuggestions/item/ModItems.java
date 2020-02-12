package shittymcsuggestions.item;

import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.block.ModBlocks;

public class ModItems {

    public static final Item BEANOS = new BeanosItem(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(20).alwaysEdible().build()));
    public static final Item BEDROCK_SWORD = new SwordItem(BedrockToolMaterial.INSTANCE, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item BEDROCK_PICKAXE = new ModPickaxeItem(BedrockToolMaterial.INSTANCE, 1, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BEDROCK_AXE = new ModAxeItem(BedrockToolMaterial.INSTANCE, 5, -3f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BEDROCK_SHOVEL = new ShovelItem(BedrockToolMaterial.INSTANCE, 1.5f, -3f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BEDROCK_HOE = new HoeItem(BedrockToolMaterial.INSTANCE, -3f, new Item.Settings().group(ItemGroup.TOOLS));

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(ShittyMinecraftSuggestions.MODID, name), item);
    }

    public static void register() {
        registerItem("dragon_egg_block", new BlockItem(ModBlocks.DRAGON_EGG_BLOCK,
                new Item.Settings().group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
        registerItem("beanos", BEANOS);
        registerItem("bedrock_sword", BEDROCK_SWORD);
        registerItem("bedrock_pickaxe", BEDROCK_PICKAXE);
        registerItem("bedrock_axe", BEDROCK_AXE);
        registerItem("bedrock_shovel", BEDROCK_SHOVEL);
        registerItem("bedrock_hoe", BEDROCK_HOE);
    }

}
