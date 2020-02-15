package shittymcsuggestions.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ModSounds;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModBlocks {

    public static final Block DRAGON_EGG_BLOCK = new DragonEggBlockBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
            .strength(50, 1200)
            .build());
    public static final Block BEANOS_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE, MaterialColor.PURPLE)
            .sounds(ModSounds.BEANOS_BLOCK_SOUND)
            .build());
    public static final Block UNLIT_TORCH = new UnlitTorchBlock(FabricBlockSettings.of(Material.PART)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.WOOD)
            .build());
    public static final Block UNLIT_WALL_TORCH = new UnlitWallTorchBlock(FabricBlockSettings.of(Material.PART)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.WOOD)
            .dropsLike(UNLIT_TORCH)
            .build());
    public static final Block GRASSHOPPER = new GrasshopperBlock(FabricBlockSettings.of(Material.ORGANIC, MaterialColor.GREEN)
            .strength(0.6f, 0.6f)
            .sounds(BlockSoundGroup.GRASS)
            .breakByTool(FabricToolTags.SHOVELS)
            .build());
    public static final Block THICC_TORCH = new ThiccTorchBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.YELLOW)
            .strength(2.0f,3.0f)
            .sounds(BlockSoundGroup.WOOD)
            .lightLevel(15)
            .nonOpaque()
            .build());

    private static void registerBlock(String name, Block block, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(ShittyMinecraftSuggestions.MODID, name), block);
        if (itemGroup != null) {
            Registry.register(Registry.ITEM,
                    new Identifier(ShittyMinecraftSuggestions.MODID, name),
                    new BlockItem(block, new Item.Settings().group(itemGroup)));
        }
    }

    public static void register() {
        registerBlock("dragon_egg_block", DRAGON_EGG_BLOCK, null);
        registerBlock("beanos_block", BEANOS_BLOCK, ItemGroup.DECORATIONS);
        registerBlock("unlit_torch", UNLIT_TORCH, null);
        registerBlock("unlit_wall_torch", UNLIT_WALL_TORCH, null);
        registerBlock("grasshopper", GRASSHOPPER, ItemGroup.REDSTONE);
        registerBlock("thicc_torch", THICC_TORCH, ItemGroup.DECORATIONS);
    }

}
