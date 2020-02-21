package shittymcsuggestions.dimension;

import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;

public class HoneyChunkGeneratorConfig extends CavesChunkGeneratorConfig {
    @Override
    public int getMaxY() {
        return 255;
    }
}
