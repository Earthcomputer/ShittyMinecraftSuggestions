package shittymcsuggestions.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
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
    public static final AetherPortalBlock AETHER_PORTAL = new AetherPortalBlock(FabricBlockSettings.of(Material.PORTAL)
            .noCollision()
            .strength(-1.0f, -1.0f)
            .sounds(BlockSoundGroup.GLASS)
            .lightLevel(11)
            .dropsNothing()
            .build());
    public static final Block COMPACTED_HONEYCOMB_BLOCK = new Block(FabricBlockSettings.of(Material.CLAY, MaterialColor.ORANGE)
            .strength(1f, 1f)
            .sounds(BlockSoundGroup.CORAL)
            .breakByTool(FabricToolTags.PICKAXES)
            .build());
    public static final HoneyPortalBlock HONEY_PORTAL = new HoneyPortalBlock(FabricBlockSettings.of(Material.PORTAL)
            .noCollision()
            .strength(-1.0f, -1.0f)
            .sounds(BlockSoundGroup.GLASS)
            .lightLevel(8)
            .dropsNothing()
            .build());
    public static final PistonBlock BEDROCK_PISTON = new BedrockPistonBlock(false, FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
            .strength(-1f, 3600000f)
            .build());
    public static final PistonHeadBlock BEDROCK_PISTON_HEAD = new CustomPistonHeadBlock(BEDROCK_PISTON, null, FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
            .strength(-1f, 3600000f)
            .dropsNothing()
            .build());
    public static final Block COMPRESSED_HONEY = new Block(FabricBlockSettings.of(Material.CLAY, MaterialColor.ORANGE)
            .strength(0.6f, 0.6f)
            .sounds(BlockSoundGroup.HONEY)
            .breakByTool(FabricToolTags.PICKAXES)
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
        registerBlock("aether_portal", AETHER_PORTAL, null);
        registerBlock("compacted_honeycomb_block", COMPACTED_HONEYCOMB_BLOCK, ItemGroup.DECORATIONS);
        registerBlock("honey_portal", HONEY_PORTAL, null);
        registerBlock("bedrock_piston", BEDROCK_PISTON, ItemGroup.REDSTONE);
        registerBlock("bedrock_piston_head", BEDROCK_PISTON_HEAD, null);
        registerBlock("compressed_honey", COMPRESSED_HONEY, ItemGroup.DECORATIONS);
    }

}
