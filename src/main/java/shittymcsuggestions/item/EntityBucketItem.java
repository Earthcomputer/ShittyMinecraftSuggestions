package shittymcsuggestions.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

import java.util.List;

public class EntityBucketItem extends BucketItem {
    private final EntityType<?> entity;

    public EntityBucketItem(EntityType<?> type, Fluid fluid, Item.Settings settings) {
        super(fluid, settings);
        this.entity = type;
    }

    @Override
    public void onEmptied(World world, ItemStack stack, BlockPos pos) {
        if (!world.isClient) {
            this.spawnEntity(world, stack, pos);
        }
    }

    private void spawnEntity(World world, ItemStack stack, BlockPos pos) {
        Entity entity = this.entity.spawnFromItemStack(world, stack, (PlayerEntity)null, pos, SpawnType.BUCKET, true, false);
//        for disabling despawning on entities that come from buckets
//        if (entity != null) {
//            ((FishEntity)entity.setFromBucket(true);
//        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack playerHand = user.getStackInHand(hand);
        BlockHitResult hitResult = (BlockHitResult)rayTrace(world, user, RayTraceContext.FluidHandling.NONE);
        BlockPos releasePos = hitResult.getBlockPos().offset(hitResult.getSide());
        if (playerHand.getItem() == ModItems.CHICKEN_BUCKET) {

            this.onEmptied(world, playerHand, releasePos);
            world.playSound(user, releasePos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.NEUTRAL, 1.0f, 1.0f);

            if (user instanceof ServerPlayerEntity) {
                Criterions.PLACED_BLOCK.trigger((ServerPlayerEntity)user, releasePos, playerHand);
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(this.getEmptiedStack(playerHand, user));
        } else {
            return TypedActionResult.fail(playerHand);
        }
    }
}
