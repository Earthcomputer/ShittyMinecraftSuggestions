package shittymcsuggestions.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.enums.PistonType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CustomPistonHeadBlock extends PistonHeadBlock {

    private final PistonBlock pistonBlock;
    private final PistonBlock stickyPistonBlock;

    public CustomPistonHeadBlock(PistonBlock pistonBlock, PistonBlock stickyPistonBlock, Settings settings) {
        super(settings);
        this.pistonBlock = pistonBlock;
        this.stickyPistonBlock = stickyPistonBlock;
    }

    @Override
    public void onBreak(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!world.isClient && playerEntity.abilities.creativeMode) {
            BlockPos blockPos2 = blockPos.offset(blockState.get(FACING).getOpposite());
            Block block = world.getBlockState(blockPos2).getBlock();
            if (block == pistonBlock || block == stickyPistonBlock) {
                world.removeBlock(blockPos2, false);
            }
        }

        world.playLevelEvent(playerEntity, 2001, blockPos, getRawIdFromState(blockState));
    }

    @Override
    public void onBlockRemoved(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.getBlock() != blockState2.getBlock()) {
            Direction direction = blockState.get(FACING).getOpposite();
            blockPos = blockPos.offset(direction);
            BlockState blockState3 = world.getBlockState(blockPos);
            if ((blockState3.getBlock() == pistonBlock || blockState3.getBlock() == stickyPistonBlock) && blockState3.get(PistonBlock.EXTENDED)) {
                dropStacks(blockState3, world, blockPos);
                world.removeBlock(blockPos, false);
            }

        }
    }

    @Override
    public boolean canPlaceAt(BlockState blockState, WorldView worldView, BlockPos blockPos) {
        Block block = worldView.getBlockState(blockPos.offset(blockState.get(FACING).getOpposite())).getBlock();
        return block == pistonBlock || block == stickyPistonBlock || block == Blocks.MOVING_PISTON;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ItemStack getPickStack(BlockView blockView, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(blockState.get(TYPE) == PistonType.STICKY ? stickyPistonBlock : pistonBlock);
    }
}
