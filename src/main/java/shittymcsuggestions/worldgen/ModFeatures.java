package shittymcsuggestions.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.block.ModBlocks;

public class ModFeatures {

    private static final BlockState THICC_TORCH = ModBlocks.THICC_TORCH.getDefaultState();

    public static final RandomPatchFeatureConfig THICC_TORCH_CONFIG = new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(THICC_TORCH), new SimpleBlockPlacer())
            .tries(128)
            .spreadX(14)
            .spreadZ(14)
            .cannotProject()
            .build();

    public static final Feature<RandomPatchFeatureConfig> THICC_TORCH_FEATURE = new ThiccTorchFeature(RandomPatchFeatureConfig::deserialize);
    public static final Feature<CustomOreFeatureConfig> CUSTOM_ORE_FEATURE = new CustomOreFeature(CustomOreFeatureConfig::deserialize);
    public static final StructureFeature<DefaultFeatureConfig> BEE_NEST_FEATURE = new BeeNestFeature(DefaultFeatureConfig::deserialize);
    public static final StructureFeature<DefaultFeatureConfig> MCDONALDS_FEATURE = new McDonaldsFeature(DefaultFeatureConfig::deserialize);

    private static void registerFeature(String name, Feature<?> feature) {
        Identifier id = new Identifier(ShittyMinecraftSuggestions.MODID, name);
        Registry.register(Registry.FEATURE, id, feature);
        if (feature instanceof StructureFeature) {
            StructureFeature<?> structure = (StructureFeature<?>) feature;
            Registry.register(Registry.STRUCTURE_FEATURE, id, structure);
            Feature.STRUCTURES.put(structure.getName(), structure);
            LocateCommandEnhancement.register(structure.getName());
        }
    }

    public static void register() {
        registerFeature("thicc_torch", THICC_TORCH_FEATURE);
        registerFeature("custom_ore", CUSTOM_ORE_FEATURE);
        registerFeature("bee_nest", BEE_NEST_FEATURE);
        registerFeature("mcdonalds", MCDONALDS_FEATURE);
    }

}
