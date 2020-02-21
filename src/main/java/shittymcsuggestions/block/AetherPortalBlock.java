package shittymcsuggestions.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;

public class AetherPortalBlock extends NetherPortalLikeBlock {

    public AetherPortalBlock(Settings settings) {
        super(Blocks.GLOWSTONE, Collections.singleton(Blocks.WATER), settings);
    }

    @Override
    protected void onEntityInPortal(World world, BlockPos pos, BlockState state, Entity entity) {
        teleportEntity(entity);
    }

    @Override
    public void teleportEntity(Entity entity) {
        entity.teleport(entity.getX(),2000, entity.getZ());
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,1200, 1));
        }
    }
}
