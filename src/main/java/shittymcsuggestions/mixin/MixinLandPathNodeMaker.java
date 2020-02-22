package shittymcsuggestions.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.block.ModFluidTags;

@Mixin(LandPathNodeMaker.class)
public class MixinLandPathNodeMaker {

    @Inject(method = "getBasicPathNodeType", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;matches(Lnet/minecraft/tag/Tag;)Z", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onGetBasicPathNodeType(BlockView world, int x, int y, int z, CallbackInfoReturnable<PathNodeType> ci,
                                               BlockPos pos, BlockState state, Block block, Material material, FluidState fluidState) {
        if (block == ModBlocks.THICC_TORCH)
            ci.setReturnValue(PathNodeType.DAMAGE_FIRE);
        else if (fluidState.matches(ModFluidTags.HONEY))
            ci.setReturnValue(PathNodeType.WATER);
    }

    @Unique private static Block capturedBlock;

    @Inject(method = "method_59", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;CACTUS:Lnet/minecraft/block/Block;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void captureBlock(BlockView world, int x, int y, int z, PathNodeType type, CallbackInfoReturnable<PathNodeType> ci,
                                     BlockPos.PooledMutable pos, int dx, int dy, int dz, Block block) {
        capturedBlock = block;
    }

    @ModifyVariable(method = "method_59", at = @At(value = "STORE", ordinal = 2))
    private static PathNodeType modifyPathNodeType(PathNodeType type) {
        if (capturedBlock == ModBlocks.THICC_TORCH)
            return PathNodeType.DANGER_FIRE;
        return type;
    }

}
