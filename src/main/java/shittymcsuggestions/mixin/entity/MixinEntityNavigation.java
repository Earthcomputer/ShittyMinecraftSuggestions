package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.IEntity;

@Mixin(EntityNavigation.class)
public class MixinEntityNavigation {

    @Shadow @Final protected MobEntity entity;

    @Inject(method = "isInLiquid", at = @At("HEAD"), cancellable = true)
    private void onIsInLiquid(CallbackInfoReturnable<Boolean> ci) {
        if (((IEntity) entity).sms_isInHoney())
            ci.setReturnValue(true);
    }

}
