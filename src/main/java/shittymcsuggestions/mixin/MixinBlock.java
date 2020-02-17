package shittymcsuggestions.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ModSounds;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.block.UnlitTorchBlock;
import shittymcsuggestions.entity.ModDamageSource;
import shittymcsuggestions.item.ModItems;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    private void onCalcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ci) {
        Block block = (Block) (Object) this;
        boolean usingBedrockPick = player.getMainHandStack().getItem() == ModItems.BEDROCK_PICKAXE;
        boolean isBedrockPickOnly = false;
        if (block == Blocks.BEDROCK || block == ModBlocks.BEDROCK_PISTON || block == ModBlocks.BEDROCK_PISTON_HEAD) {
            isBedrockPickOnly = true;
        }

        if (isBedrockPickOnly) {
            Block likeBlock = usingBedrockPick ? Blocks.OBSIDIAN : Blocks.BEDROCK;
            float hardness = likeBlock.getDefaultState().getHardness(world, pos);
            int factor = 30;
            ci.setReturnValue(player.getBlockBreakingSpeed(likeBlock.getDefaultState()) / hardness / factor);
        }
    }

    @Inject(method = "onEntityCollision", at = @At("HEAD"))
    private void onOnEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (state.getBlock() == Blocks.TORCH || state.getBlock() == Blocks.WALL_TORCH) {
            if (entity instanceof PlayerEntity && entity.isSprinting()) {
                if (!world.isClient)
                    world.setBlockState(pos, UnlitTorchBlock.fromTorch(state), 11);
                world.playSound(null, pos, ModSounds.WHOOSH, SoundCategory.BLOCKS, 1, 1);
            }
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void onOnBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        //noinspection ConstantConditions
        if (!world.isClient && (Object) this instanceof AbstractGlassBlock && player.getMainHandStack().isEmpty()) {
            player.damage(ModDamageSource.GLASS, 2);
        }
    }

}
