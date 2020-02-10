package shittymcsuggestions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DragonEggBlockBlock extends Block {

    public DragonEggBlockBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        teleport(world, pos, state);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        teleport(world, pos, state);
    }

    private void teleport(World world, BlockPos pos, BlockState state) {
        for (int attempt = 0; attempt < 1000; attempt++) {
            BlockPos targetPos = pos.add(world.random.nextInt(64) - world.random.nextInt(64),
                    world.random.nextInt(32) - world.random.nextInt(32),
                    world.random.nextInt(64) - world.random.nextInt(64));
            if (world.getBlockState(targetPos).isAir()) {
                if (world.isClient) {
                    for (int i = 0; i < 128; i++) {
                        double delta = world.random.nextDouble();
                        float velX = (world.random.nextFloat() - 0.5f) * 0.2f;
                        float velY = (world.random.nextFloat() - 0.5f) * 0.2f;
                        float velZ = (world.random.nextFloat() - 0.5f) * 0.2f;
                        double x = MathHelper.lerp(delta, pos.getX(), targetPos.getX());
                        double y = MathHelper.lerp(delta, pos.getY(), targetPos.getY());
                        double z = MathHelper.lerp(delta, pos.getZ(), targetPos.getZ());
                        world.addParticle(ParticleTypes.PORTAL, x, y, z, velX, velY, velZ);
                    }
                } else {
                    world.setBlockState(targetPos, state, 2);
                    world.removeBlock(pos, false);
                }
                break;
            }
        }
    }

}
