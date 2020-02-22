package shittymcsuggestions.mixin;

import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.IEntity;

@Mixin(StayAboveWaterTask.class)
public class MixinStayAboveWaterTask {

    @Inject(method = "shouldRun", at = @At("HEAD"), cancellable = true)
    private void onShouldRun(ServerWorld world, MobEntity entity, CallbackInfoReturnable<Boolean> ci) {
        if (((IEntity) entity).sms_isInHoney())
            ci.setReturnValue(true);
    }

}
