package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.IEntity;

@Mixin(targets = "net.minecraft.entity.passive.FoxEntity$FoxSwimGoal")
public class MixinFoxSwimGoal {

    @Unique private FoxEntity that;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<init>(Lnet/minecraft/entity/passive/FoxEntity;)V", at = @At("RETURN"))
    private void captureThis(FoxEntity that, CallbackInfo ci) {
        this.that = that;
    }

    @Inject(method = "canStart()Z", at = @At("HEAD"), cancellable = true)
    private void onCanStart(CallbackInfoReturnable<Boolean> ci) {
        if (((IEntity) that).sms_isInHoney())
            ci.setReturnValue(true);
    }

}
