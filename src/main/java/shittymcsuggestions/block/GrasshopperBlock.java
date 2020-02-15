package shittymcsuggestions.block;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class GrasshopperBlock extends HopperBlock implements ICustomHopper {

    public GrasshopperBlock(Settings settings) {
        super(settings);
    }

    @Override
    public TriState doCustomInsertion(World world, BlockPos pos, BlockState state, HopperBlockEntity hopper) {
        ItemStack toThrow = null;
        for (int slot = 0; slot < hopper.getInvSize(); slot++) {
            if (!hopper.getInvStack(slot).isEmpty()) {
                toThrow = hopper.takeInvStack(slot, 1);
                break;
            }
        }
        if (toThrow == null)
            return TriState.FALSE;

        Direction dir = state.get(FACING);
        double x = pos.getX() + 0.5 + 0.7 * dir.getOffsetX();
        double y = pos.getY() + 0.2 + 0.7 * dir.getOffsetY();
        double z = pos.getZ() + 0.5 + 0.7 * dir.getOffsetZ();
        ItemEntity item = new ItemEntity(world, x, y, z, toThrow);
        item.setVelocity(Vec3d.ZERO);
        world.spawnEntity(item);
        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, 1);

        return TriState.TRUE;
    }

    @Override
    public String getCustomContainerName() {
        return "container.grasshopper";
    }
}
