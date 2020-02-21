package shittymcsuggestions.mixin;

import net.minecraft.world.gen.carver.Carver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Carver.class)
public interface CarverAccessor {

    @Mutable
    @Accessor
    void setHeightLimit(int heightLimit);

}
