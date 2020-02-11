package shittymcsuggestions.item;

import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import shittymcsuggestions.ModSounds;
import shittymcsuggestions.entity.ExplodingArrowEntity;
import shittymcsuggestions.mixin.LivingEntityAccessor;

public class BeanosItem extends Item {

    public BeanosItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 2 * super.getMaxUseTime(stack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            player.getHungerManager().eat(this, stack);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.BEANOS, SoundCategory.PLAYERS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
            if (player instanceof ServerPlayerEntity) {
                Criterions.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
            }
        }

        ((LivingEntityAccessor) user).callApplyFoodEffects(stack, world, user);
        if (!(user instanceof PlayerEntity) || !((PlayerEntity) user).abilities.creativeMode)
            stack.decrement(1);

        ExplodingArrowEntity arrow = new ExplodingArrowEntity(user, world);
        arrow.setProperties(user, user.pitch, user.yaw, 0, 3, 1);
        world.spawnEntity(arrow);

        return stack;
    }
}
