package shittymcsuggestions.block;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import shittymcsuggestions.dimension.ModDimensions;
import shittymcsuggestions.entity.PortalCooldownHelper;

import java.util.Collections;

public class HoneyPortalBlock extends NetherPortalLikeBlock {

    public HoneyPortalBlock(Settings settings) {
        super(ModBlocks.COMPACTED_HONEYCOMB_BLOCK, Collections.singleton(Blocks.FIRE), settings);
    }

    @Override
    protected void onEntityInPortal(World world, BlockPos pos, BlockState state, Entity entity) {
        PortalCooldownHelper.getInstance(entity, this).onEntityInPortal();
    }

    @Override
    public void teleportEntity(Entity entity) {
        DimensionType targetDim = entity.world.getDimension().getType() == ModDimensions.BEE ? DimensionType.OVERWORLD : ModDimensions.BEE;
        FabricDimensions.teleport(entity, targetDim, ModDimensions.BEE.getDefaultPlacement());
    }
}
