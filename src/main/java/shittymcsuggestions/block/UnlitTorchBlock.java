package shittymcsuggestions.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class UnlitTorchBlock extends TorchBlock {
    protected UnlitTorchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem().isIn(ItemTags.COALS)) {
            unlitTorchUse(world, player, pos, Blocks.TORCH.getDefaultState(), stack);
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // nop
    }

    static void unlitTorchUse(World world, PlayerEntity player, BlockPos pos, BlockState torchState, ItemStack stack) {
        if (!world.isClient)
            world.setBlockState(pos, torchState, 11);
        if (!player.abilities.creativeMode)
            stack.decrement(1);
        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1);
    }

    public static BlockState fromTorch(BlockState torch) {
        if (torch.getBlock() == Blocks.TORCH) {
            return ModBlocks.UNLIT_TORCH.getDefaultState();
        } else {
            return ModBlocks.UNLIT_WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, torch.get(WallTorchBlock.FACING));
        }
    }
}
