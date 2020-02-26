package shittymcsuggestions.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.block.AetherPortalBlock;
import shittymcsuggestions.block.DragonEggBlockBlock;
import shittymcsuggestions.block.ModBlocks;

@Mixin(FireBlock.class)
public class MixinFireBlock {

    @Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
    private void onOnBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl, CallbackInfo ci) {
        if (DragonEggBlockBlock.createPortal(world, blockPos))
            ci.cancel();
        else if (ModBlocks.HONEY_PORTAL.createPortalAt(world, blockPos))
            ci.cancel();
    }

}
