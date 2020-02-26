package shittymcsuggestions.mixin.accessor;

import net.minecraft.world.gen.chunk.CavesChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CavesChunkGenerator.class)
public interface CavesChunkGeneratorAccessor {

    @Mutable
    @Accessor
    void setNoiseFalloff(double[] noiseFalloff);

    @Invoker
    double[] callBuildNoiseFalloff();

}
