package shittymcsuggestions.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.block.ModBlocks;

public class ModItems {

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(ShittyMinecraftSuggestions.MODID, name), item);
    }

    public static void register() {
        registerItem("dragon_egg_block", new BlockItem(ModBlocks.DRAGON_EGG_BLOCK,
                new Item.Settings().group(ItemGroup.BUILDING_BLOCKS).maxCount(1)));
    }

}
