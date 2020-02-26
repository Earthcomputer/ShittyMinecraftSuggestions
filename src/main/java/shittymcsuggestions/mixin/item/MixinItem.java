package shittymcsuggestions.mixin.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ModSounds;

@Mixin(Item.class)
public class MixinItem {

    @Inject(method = "getEatSound", at = @At("HEAD"), cancellable = true)
    private void onGetEatSound(CallbackInfoReturnable<SoundEvent> ci) {
        if ((Object) this == Items.MELON)
            ci.setReturnValue(ModSounds.WATERMELON_SCREAM);
    }

}
