package shittymcsuggestions.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BedrockPistonBlock extends PistonBlock implements ICustomPiston {

    private static final int ACTION_EXTEND = 0;

    public BedrockPistonBlock(boolean sticky, Settings settings) {
        super(sticky, settings);
    }

    @Override
    public boolean onBlockAction(BlockState blockState, World world, BlockPos blockPos, int type, int data) {
        if (type == ACTION_EXTEND)
            return super.onBlockAction(blockState, world, blockPos, type, data);
        else
            return false;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState blockState) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public int getPushLimit() {
        return 144;
    }

    @Override
    public PistonHeadBlock getCustomPistonHeadBlock() {
        return ModBlocks.BEDROCK_PISTON_HEAD;
    }
}
