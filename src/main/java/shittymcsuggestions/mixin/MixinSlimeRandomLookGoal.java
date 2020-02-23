package shittymcsuggestions.mixin;

import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.IEntity;

@Mixin(targets = "net.minecraft.entity.mob.SlimeEntity$RandomLookGoal")
public class MixinSlimeRandomLookGoal {

    @Shadow @Final private SlimeEntity slime;

    @Inject(method = "canStart()Z", at = @At("HEAD"), cancellable = true)
    private void onCanStart(CallbackInfoReturnable<Boolean> ci) {
        if (slime.getTarget() == null
                && ((IEntity) slime).sms_isInHoney()
                && slime.getMoveControl() != null
                && slime.getMoveControl().getClass().getDeclaringClass() == SlimeEntity.class)
            ci.setReturnValue(true);
    }

}
