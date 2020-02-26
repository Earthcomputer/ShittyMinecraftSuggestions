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

    public static final Block DRAGON_EGG_BLOCK = registerBlock("dragon_egg_block", new DragonEggBlockBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
            .strength(50, 1200)
            .build()), null);
    public static final Block BEANOS_BLOCK = registerBlock("beanos_block", new Block(FabricBlockSettings.of(Material.CAKE, MaterialColor.PURPLE)
            .sounds(ModSounds.BEANOS_BLOCK_SOUND)
            .build()), ItemGroup.DECORATIONS);
    public static final Block UNLIT_TORCH = registerBlock("unlit_torch", new UnlitTorchBlock(FabricBlockSettings.of(Material.PART)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.WOOD)
            .build()), null);
    public static final Block UNLIT_WALL_TORCH = registerBlock("unlit_wall_torch", new UnlitWallTorchBlock(FabricBlockSettings.of(Material.PART)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.WOOD)
            .dropsLike(UNLIT_TORCH)
            .build()), null);
    public static final Block GRASSHOPPER = registerBlock("grasshopper", new GrasshopperBlock(FabricBlockSettings.of(Material.ORGANIC, MaterialColor.GREEN)
            .strength(0.6f, 0.6f)
            .sounds(BlockSoundGroup.GRASS)
            .breakByTool(FabricToolTags.SHOVELS)
            .build()), ItemGroup.REDSTONE);
    public static final Block THICC_TORCH = registerBlock("thicc_torch", new ThiccTorchBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.YELLOW)
            .strength(2.0f,3.0f)
            .sounds(BlockSoundGroup.WOOD)
            .lightLevel(15)
            .nonOpaque()
            .build()), ItemGroup.DECORATIONS);
    public static final AetherPortalBlock AETHER_PORTAL = registerBlock("aether_portal", new AetherPortalBlock(FabricBlockSettings.of(Material.PORTAL)
            .noCollision()
            .strength(-1.0f, -1.0f)
            .sounds(BlockSoundGroup.GLASS)
            .lightLevel(11)
            .dropsNothing()
            .build()), null);
    public static final Block COMPACTED_HONEYCOMB_BLOCK = registerBlock("compacted_honeycomb_block", new Block(FabricBlockSettings.of(Material.CLAY, MaterialColor.ORANGE)
            .strength(1f, 1f)
            .sounds(BlockSoundGroup.CORAL)
            .breakByTool(FabricToolTags.PICKAXES)
            .build()), ItemGroup.DECORATIONS);
    public static final HoneyPortalBlock HONEY_PORTAL = registerBlock("honey_portal", new HoneyPortalBlock(FabricBlockSettings.of(Material.PORTAL)
            .noCollision()
            .strength(-1.0f, -1.0f)
            .sounds(BlockSoundGroup.GLASS)
            .lightLevel(8)
            .dropsNothing()
            .build()), null);
    public static final PistonBlock BEDROCK_PISTON = registerBlock("bedrock_piston", new BedrockPistonBlock(false, FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
            .strength(-1f, 3600000f)
            .build()), ItemGroup.REDSTONE);
    public static final PistonHeadBlock BEDROCK_PISTON_HEAD = registerBlock("bedrock_piston_head", new CustomPistonHeadBlock(BEDROCK_PISTON, null, FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
            .strength(-1f, 3600000f)
            .dropsNothing()
            .build()), null);
    public static final Block COMPRESSED_HONEY = registerBlock("compressed_honey", new Block(FabricBlockSettings.of(Material.CLAY, MaterialColor.ORANGE)
            .strength(0.6f, 0.6f)
            .sounds(BlockSoundGroup.HONEY)
            .breakByTool(FabricToolTags.PICKAXES)
            .build()), ItemGroup.DECORATIONS);
    public static final Block HONEY = registerBlock("honey", new HoneyFluidBlock(ModFluids.HONEY, FabricBlockSettings.copy(Blocks.WATER).build()), null);
    public static final Block WAX = registerBlock("wax", new WaxBlock(FabricBlockSettings.copy(Blocks.END_STONE)
            .sounds(BlockSoundGroup.CORAL)
            .build()), ItemGroup.BUILDING_BLOCKS);
    public static final Block DIRT_SLAB = registerBlock("dirt_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.DIRT)
            .breakByTool(FabricToolTags.SHOVELS)
            .build()), ItemGroup.BUILDING_BLOCKS);
    public static final Block CHEST_PLATE = registerBlock("chest_plate", new ModPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD)
            .strength(2.5f, 2.5f)
            .sounds(BlockSoundGroup.WOOD)
            .build()), ItemGroup.REDSTONE);
    public static final Block BEE_ORE = registerBlock("bee_ore", new Block(FabricBlockSettings.of(Material.STONE)
            .strength(3f, 3f)
            .breakByTool(FabricToolTags.PICKAXES, 3)
            .build()), ItemGroup.BUILDING_BLOCKS);

    private static <T extends Block> T registerBlock(String name, T block, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(ShittyMinecraftSuggestions.MODID, name), block);
        if (itemGroup != null) {
            Registry.register(Registry.ITEM,
                    new Identifier(ShittyMinecraftSuggestions.MODID, name),
                    new BlockItem(block, new Item.Settings().group(itemGroup)));
        }
        return block;
    }

    public static void register() {
        // load class
    }

}
