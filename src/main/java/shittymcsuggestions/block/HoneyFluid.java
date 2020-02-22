package shittymcsuggestions.block;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import shittymcsuggestions.item.ModItems;

public abstract class HoneyFluid extends AbstractFluid {

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_HONEY;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.HONEY;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.HONEY_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.HONEY.getDefaultState().with(Properties.LEVEL_15, method_15741(state));
    }

    public static class Still extends HoneyFluid {
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends HoneyFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

}
