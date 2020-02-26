package shittymcsuggestions.mixin.entity;

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

    @Unique private boolean readingTag;

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    private void preReadFromTag(CallbackInfo ci) {
        readingTag = true;
    }

    @Inject(method = "readCustomDataFromTag", at = @At("RETURN"))
    private void postReadFromTag(CallbackInfo ci) {
        readingTag = false;
    }

    @Inject(method = "setStack", at = @At("HEAD"))
    private void onSetStack(ItemStack stack, CallbackInfo ci) {
        if (!readingTag) {
            age += getStackDespawnTime(getStack());
            age -= getStackDespawnTime(stack);
        }
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
