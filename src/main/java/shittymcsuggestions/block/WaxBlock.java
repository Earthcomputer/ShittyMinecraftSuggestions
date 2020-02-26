package shittymcsuggestions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import shittymcsuggestions.mixin.accessor.BeeEntityAccessor;

public class WaxBlock extends Block {
    public static final BooleanProperty SPAWNS_BEE = BooleanProperty.of("spawns_bee");


    public WaxBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SPAWNS_BEE, false));
    }

    public void onStacksDropped(BlockState state, World world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        if (!world.isClient && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0 && state.get(SPAWNS_BEE)) {
            BeeEntity beeEntity = EntityType.BEE.create(world);
            //noinspection ConstantConditions
            ((BeeEntityAccessor)beeEntity).callSetAnger(800);
            beeEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(beeEntity);
            beeEntity.playSpawnEffects();
        }
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SPAWNS_BEE);
    }

}
