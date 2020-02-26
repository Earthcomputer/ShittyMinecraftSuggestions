package shittymcsuggestions.mixin.accessor;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StructurePool.class)
public interface StructurePoolAccessor {

    @Accessor
    List<StructurePoolElement> getElements();

    @Accessor
    ImmutableList<Pair<StructurePoolElement, Integer>> getElementCounts();

    @Mutable
    @Accessor
    void setElementCounts(ImmutableList<Pair<StructurePoolElement, Integer>> elementCounts);

    @Accessor
    StructurePool.Projection getProjection();

}
