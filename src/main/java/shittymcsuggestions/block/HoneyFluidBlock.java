package shittymcsuggestions.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shittymcsuggestions.entity.IEntity;

public class HoneyFluidBlock extends ModFluidBlock {
    protected HoneyFluidBlock(BaseFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        ((IEntity) entity).sms_setInHoney();
    }
}
