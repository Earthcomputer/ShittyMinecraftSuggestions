package shittymcsuggestions.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import shittymcsuggestions.ShittyMinecraftSuggestions;

import java.util.function.Function;

public class BeeNestFeature extends AbstractTempleFeature<DefaultFeatureConfig> {

    public BeeNestFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public String getName() {
        return ShittyMinecraftSuggestions.MODID + ":bee_nest";
    }

    @Override
    public int getRadius() {
        return 3;
    }

    @Override
    protected int getSeedModifier() {
        return 87654321;
    }

    public static class Start extends StructureStart {

        public Start(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
            super(feature, chunkX, chunkZ, box, references, l);
        }

        @Override
        public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome) {
            BlockPos startingPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            BeeNestGenerator.addPieces(startingPos, children, random);
            setBoundingBoxFromChildren();
        }
    }
}
