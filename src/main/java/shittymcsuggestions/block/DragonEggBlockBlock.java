package shittymcsuggestions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

    public static boolean createPortal(World world, BlockPos pos) {
        // find corners
        BlockPos corner1;
        int offset;
        for (offset = 0; offset < 21; offset++) {
            if (world.getBlockState(pos.west(offset)).getBlock() == ModBlocks.DRAGON_EGG_BLOCK)
                break;
        }
        corner1 = pos.west(offset - 1);
        if (offset == 21)
            return false;
        for (offset = 0; offset < 21; offset++) {
            if (world.getBlockState(pos.north(offset)).getBlock() == ModBlocks.DRAGON_EGG_BLOCK)
                break;
        }
        corner1 = corner1.north(offset - 1);
        if (offset == 21)
            return false;
        BlockPos corner2;
        for (offset = 0; offset < 21; offset++) {
            if (world.getBlockState(pos.east(offset)).getBlock() == ModBlocks.DRAGON_EGG_BLOCK)
                break;
        }
        corner2 = pos.east(offset - 1);
        if (offset == 21)
            return false;
        for (offset = 0; offset < 21; offset++) {
            if (world.getBlockState(pos.south(offset)).getBlock() == ModBlocks.DRAGON_EGG_BLOCK)
                break;
        }
        corner2 = corner2.south(offset - 1);
        if (offset == 21)
            return false;

        // are corners too far apart?
        if (corner2.getX() - corner1.getX() >= 21 || corner2.getZ() - corner1.getZ() >= 21)
            return false;

        // check if entirely air or fire
        for (BlockPos offsetPos : BlockPos.iterate(corner1, corner2)) {
            BlockState state = world.getBlockState(offsetPos);
            if (!state.isAir() && state.getBlock() != Blocks.FIRE)
                return false;
        }

        // check portal outline
        // north line
        for (BlockPos offsetPos : BlockPos.iterate(corner1.north(), new BlockPos(corner2.getX(), corner1.getY(), corner1.getZ() - 1)))
            if (world.getBlockState(offsetPos).getBlock() != ModBlocks.DRAGON_EGG_BLOCK)
                return false;
        // east line
        for (BlockPos offsetPos : BlockPos.iterate(corner2.east(), new BlockPos(corner2.getX() + 1, corner2.getY(), corner1.getZ())))
            if (world.getBlockState(offsetPos).getBlock() != ModBlocks.DRAGON_EGG_BLOCK)
                return false;
        // south line
        for (BlockPos offsetPos : BlockPos.iterate(corner2.south(), new BlockPos(corner1.getX(), corner2.getY(), corner2.getZ() + 1)))
            if (world.getBlockState(offsetPos).getBlock() != ModBlocks.DRAGON_EGG_BLOCK)
                return false;
        // west line
        for (BlockPos offsetPos : BlockPos.iterate(corner1.west(), new BlockPos(corner1.getX() - 1, corner1.getY(), corner2.getZ())))
            if (world.getBlockState(offsetPos).getBlock() != ModBlocks.DRAGON_EGG_BLOCK)
                return false;

        for (BlockPos offsetPos : BlockPos.iterate(corner1, corner2)) {
            world.setBlockState(offsetPos, Blocks.END_PORTAL.getDefaultState(), 18);
        }

        return true;
    }

}
