package shittymcsuggestions.mixin;

import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.IEntity;

@Mixin(targets = "net.minecraft.entity.passive.FoxEntity$FoxSwimGoal")
public class MixinFoxSwimGoal {

    @Shadow(aliases = "field_17976") @Final FoxEntity this$0;

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    private void onCanStart(CallbackInfoReturnable<Boolean> ci) {
        if (((IEntity) this$0).sms_isInHoney())
            ci.setReturnValue(true);
    }

}
