package shittymcsuggestions.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.block.AetherPortalBlock;
import shittymcsuggestions.block.ModBlocks;

@Mixin(BucketItem.class)
public class MixinBucketItem {
    @Final
    @Shadow
    private Fluid fluid;

    @Inject(method = "placeFluid", at = @At("HEAD"), cancellable = true)
    private void onPlaceFluid(PlayerEntity player, World world, BlockPos pos ,BlockHitResult hitResult, CallbackInfoReturnable<Boolean> ci) {
        if (!(this.fluid instanceof WaterFluid)) {
            ci.setReturnValue(false);
        }
        if (((AetherPortalBlock) ModBlocks.AETHER_PORTAL).createPortal(world,pos)) {
            ci.setReturnValue(true);
        }
    }
}
