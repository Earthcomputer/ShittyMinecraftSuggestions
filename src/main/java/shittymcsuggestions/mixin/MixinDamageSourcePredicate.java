package shittymcsuggestions.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSourcePredicate.class)
public abstract class MixinDamageSourcePredicate {

    @Shadow private static Boolean getBoolean(JsonObject obj, String name) {
        return null;
    }

    @Unique private Boolean isStarvation;

    @Inject(method = "test(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/entity/damage/DamageSource;)Z", at = @At("RETURN"), cancellable = true)
    private void onTest(ServerWorld world, Vec3d pos, DamageSource source, CallbackInfoReturnable<Boolean> ci) {
        if (ci.getReturnValueZ() && isStarvation != null && isStarvation != (source == DamageSource.STARVE))
            ci.setReturnValue(false);
    }

    @Inject(method = "deserialize", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private static void onDeserialize(JsonElement json, CallbackInfoReturnable<DamageSourcePredicate> ci) {
        //noinspection ConstantConditions
        ((MixinDamageSourcePredicate) (Object) ci.getReturnValue()).isStarvation = getBoolean(json.getAsJsonObject(), "is_starvation");
    }

}
