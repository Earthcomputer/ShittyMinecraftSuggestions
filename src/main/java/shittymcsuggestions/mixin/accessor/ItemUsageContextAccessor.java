package shittymcsuggestions.mixin.accessor;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemUsageContext.class)
public interface ItemUsageContextAccessor {

    @Accessor
    @Mutable
    void setStack(ItemStack stack);

}
