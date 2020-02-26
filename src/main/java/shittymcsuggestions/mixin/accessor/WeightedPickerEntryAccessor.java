package shittymcsuggestions.mixin.accessor;

import net.minecraft.util.WeightedPicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WeightedPicker.Entry.class)
public interface WeightedPickerEntryAccessor {

    @Accessor
    int getWeight();

}
