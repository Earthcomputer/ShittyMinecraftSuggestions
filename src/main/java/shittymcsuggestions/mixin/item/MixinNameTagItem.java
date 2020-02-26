package shittymcsuggestions.mixin.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.entity.NameTagHandler;

@Mixin(NameTagItem.class)
public class MixinNameTagItem {

    @Inject(method = "useOnEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private void onUseOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand, CallbackInfoReturnable<Boolean> ci) {
        if (player instanceof ServerPlayerEntity)
            NameTagHandler.onMobNamed(player, entity, stack.getName().getString());
    }

}
