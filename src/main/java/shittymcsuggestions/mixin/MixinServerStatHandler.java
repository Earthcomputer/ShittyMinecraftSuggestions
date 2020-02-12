package shittymcsuggestions.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.criterion.ModCriterions;

@Mixin(ServerStatHandler.class)
public class MixinServerStatHandler {

    @Inject(method = "setStat", at = @At("TAIL"))
    private void onSetStat(PlayerEntity player, Stat<?> stat, int amount, CallbackInfo ci) {
        ModCriterions.STAT.trigger((ServerPlayerEntity) player, stat, amount);
    }

}
