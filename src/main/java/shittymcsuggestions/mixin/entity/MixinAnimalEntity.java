package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.passive.AnimalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class MixinAnimalEntity {

    @Inject(method = "canBreedWith", at = @At("RETURN"), cancellable = true)
    private void onCanBreedWith(AnimalEntity other, CallbackInfoReturnable<Boolean> ci) {
        if (ci.getReturnValueZ() && ((AnimalEntity) (Object) this).isBaby() && other.isBaby()) {
            ci.setReturnValue(false);
        }
    }

}
