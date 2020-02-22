package shittymcsuggestions.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
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

    private static void registerFeature(String name, Feature<?> feature) {
        Registry.register(Registry.FEATURE, new Identifier(ShittyMinecraftSuggestions.MODID, name), feature);
    }

    public static void register() {
        registerFeature("thicc_torch", THICC_TORCH_FEATURE);
        registerFeature("custom_ore", CUSTOM_ORE_FEATURE);
    }

}
