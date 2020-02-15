package shittymcsuggestions.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity {

    @Shadow private int age;

    @Shadow public abstract ItemStack getStack();

    @Inject(method = "setStack", at = @At("HEAD"))
    private void onSetStack(ItemStack stack, CallbackInfo ci) {
        age += getStackDespawnTime(getStack());
        age -= getStackDespawnTime(stack);
    }

    @Unique
    private int getStackDespawnTime(ItemStack stack) {
        if (stack.isFood()) {
            return 100;
        } else {
            return 6000;
        }
    }

}
