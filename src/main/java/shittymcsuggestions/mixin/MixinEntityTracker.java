package shittymcsuggestions.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.ModEntities;

@Mixin(targets = "net.minecraft.server.world.ThreadedAnvilChunkStorage$EntityTracker")
public class MixinEntityTracker {

    @Unique private ThreadLocal<Entity> entityInstnace = new ThreadLocal<>();

    @ModifyVariable(method = "getMaxTrackDistance", at = @At(value = "STORE", ordinal = 0))
    private Entity captureEntityInstance(Entity instance) {
        entityInstnace.set(instance);
        return instance;
    }

    @ModifyVariable(method = "getMaxTrackDistance", ordinal = 1, at = @At(value = "STORE", ordinal = 0))
    private int modifyTrackingDistance(int oldDistance) {
        return ModEntities.getTrackingDistance(entityInstnace.get(), oldDistance);
    }

    @Inject(method = "getMaxTrackDistance", at = @At("RETURN"))
    private void postGetMaxTrackDistance(CallbackInfoReturnable<Integer> ci) {
        entityInstnace.set(null);
    }

}
