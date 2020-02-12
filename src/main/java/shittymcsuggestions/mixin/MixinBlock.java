package shittymcsuggestions.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.item.ModItems;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    private void onCalcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ci) {
        if ((Object) this == Blocks.BEDROCK && player.getMainHandStack().getItem() == ModItems.BEDROCK_PICKAXE) {
            float hardness = Blocks.OBSIDIAN.getDefaultState().getHardness(world, pos);
            int factor = 30;
            ci.setReturnValue(player.getBlockBreakingSpeed(Blocks.OBSIDIAN.getDefaultState()) / hardness / factor);
        }
    }

}
