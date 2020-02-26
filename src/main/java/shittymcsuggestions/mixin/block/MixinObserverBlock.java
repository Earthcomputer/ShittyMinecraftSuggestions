package shittymcsuggestions.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ObserverBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.ModSounds;

import java.util.Random;

@Mixin(ObserverBlock.class)
public class MixinObserverBlock {

    @Inject(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerTickScheduler;schedule(Lnet/minecraft/util/math/BlockPos;Ljava/lang/Object;I)V"))
    private void onObserverActivate(BlockState state, ServerWorld world, BlockPos pos, Random rand, CallbackInfo ci) {
        world.playSound(null, pos, ModSounds.OWO, SoundCategory.BLOCKS, 1, 1);
    }

}
