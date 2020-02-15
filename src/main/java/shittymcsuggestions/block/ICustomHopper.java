package shittymcsuggestions.block;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICustomHopper {

    default TriState doCustomInsertion(World world, BlockPos pos, BlockState state, HopperBlockEntity hopper) {
        return TriState.DEFAULT;
    }

    default String getCustomContainerName() {
        return null;
    }

}
