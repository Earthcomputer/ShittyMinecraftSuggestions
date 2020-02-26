package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class MixinPigEntity extends AnimalEntity {

    protected MixinPigEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Shadow public abstract boolean isBreedingItem(ItemStack stack);

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> ci) {
        //noinspection ConstantConditions
        if ((Object) this instanceof PigEntity) {
            ItemStack stack = player.getStackInHand(hand);
            if (!world.isClient && isBreedingItem(stack) && isBaby() && canEat()) {
                eat(player, stack);
                lovePlayer(player);
                player.swingHand(hand, true);
                ci.setReturnValue(true);
            }
        }
    }

}
