package shittymcsuggestions.mixin.accessor;

import net.minecraft.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpawnEggItem.class)
public interface SpawnEggItemAccessor {

    @Accessor
    @Mutable
    void setPrimaryColor(int primaryColor);

    @Accessor
    @Mutable
    void setSecondaryColor(int secondaryColor);

}
