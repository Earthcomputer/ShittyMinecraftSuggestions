package shittymcsuggestions.worldgen;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;

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

        addCarver(GenerationStep.Carver.AIR, configureCarver(ModCarvers.BEE_CAVE, new ProbabilityConfig(0.2f)));
        addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.BEE, 10, 1, 3));
    }
}
