package shittymcsuggestions.mixin;

import net.minecraft.container.CraftingResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.criterion.ModCriterions;

@Mixin(CraftingResultSlot.class)
public class MixinCraftingResultSlot {

    @Shadow @Final private PlayerEntity player;

    @Inject(method = "onCrafted(Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onCraft(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;I)V"))
    private void onOnCrafted(ItemStack stack, CallbackInfo ci) {
        if (player instanceof ServerPlayerEntity) {
            ModCriterions.CRAFT_ITEM.trigger((ServerPlayerEntity) player, stack);
        }
    }

}
