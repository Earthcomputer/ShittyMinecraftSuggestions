package shittymcsuggestions.mixin;

import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.IEntity;

@Mixin(SwimGoal.class)
public class MixinSwimGoal {

    @Shadow @Final private MobEntity mob;

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    private void onCanStart(CallbackInfoReturnable<Boolean> ci) {
        if (((IEntity) mob).sms_isInHoney())
            ci.setReturnValue(true);
    }

}
