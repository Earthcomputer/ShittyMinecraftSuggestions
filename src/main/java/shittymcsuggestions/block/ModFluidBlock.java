package shittymcsuggestions.block;

import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.BaseFluid;

public class ModFluidBlock extends FluidBlock {
    protected ModFluidBlock(BaseFluid fluid, Settings settings) {
        super(fluid, settings);
    }
}
