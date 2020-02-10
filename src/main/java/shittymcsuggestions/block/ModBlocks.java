package shittymcsuggestions.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModBlocks {

    public static final Block DRAGON_EGG_BLOCK = new DragonEggBlockBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
            .strength(50, 1200)
            .build());

    private static void registerBlock(String name, Block block) {
        registerBlock(name, block, true);
    }

    private static void registerBlock(String name, Block block, boolean createItem) {
        Registry.register(Registry.BLOCK, new Identifier(ShittyMinecraftSuggestions.MODID, name), block);
        if (createItem) {
            Registry.register(Registry.ITEM,
                    new Identifier(ShittyMinecraftSuggestions.MODID, name),
                    new BlockItem(block, new Item.Settings().group(ItemGroup.MISC)));
        }
    }

    public static void register() {
        registerBlock("dragon_egg_block", DRAGON_EGG_BLOCK, false);
    }

}
