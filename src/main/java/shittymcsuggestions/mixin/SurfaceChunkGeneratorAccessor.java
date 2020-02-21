package shittymcsuggestions.mixin;

import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SurfaceChunkGenerator.class)
public interface SurfaceChunkGeneratorAccessor {

    @Mutable
    @Accessor
    void setNoiseSizeY(int noiseSizeY);

}
