package shittymcsuggestions.worldgen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import shittymcsuggestions.block.ModBlocks;

public class BeeBiome extends Biome {
    protected BeeBiome() {
        super(new Biome.Settings()
                .configureSurfaceBuilder(ModSurfaceBuilders.BEE, ModSurfaceBuilders.BEE_CONFIG)
                .precipitation(Precipitation.NONE)
                .category(Category.NONE)
                .depth(0.1f)
                .scale(0.2f)
                .temperature(1.0f)
                .downfall(0)
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .parent(null));

        addStructureFeature(ModFeatures.BEE_NEST_FEATURE.configure(FeatureConfig.DEFAULT));
        addCarver(GenerationStep.Carver.AIR, configureCarver(ModCarvers.BEE_CAVE, new ProbabilityConfig(0.2f)));
        addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModFeatures.BEE_NEST_FEATURE.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));
        addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModFeatures.THICC_TORCH_FEATURE.configure(ModFeatures.THICC_TORCH_CONFIG).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(3, 0, 0, 256))));
        addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModFeatures.CUSTOM_ORE_FEATURE.configure(new CustomOreFeatureConfig(CustomOreFeatureConfig.CustomTarget.HONEYCOMB, ModBlocks.COMPACTED_HONEYCOMB_BLOCK.getDefaultState(), 14)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(32, 10, 20, 256))));
        addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.BEE, 10, 1, 3));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public int getGrassColorAt(double x, double z) {
        return Biomes.JUNGLE.getGrassColorAt(x, z);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public int getFoliageColor() {
        return Biomes.JUNGLE.getFoliageColor();
    }
}
