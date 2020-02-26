package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ModSounds;

@Mixin(FishEntity.class)
public class MixinFishEntity extends WaterCreatureEntity {

    protected MixinFishEntity(EntityType<? extends WaterCreatureEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> ci) {
        //noinspection ConstantConditions
        if ((Object) this instanceof PufferfishEntity) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() == Items.CARROT && isAlive()) {
                playSound(ModSounds.AUEGH, 1, 1);
                if (!player.abilities.creativeMode)
                    stack.decrement(1);
            }
        }
    }

}
