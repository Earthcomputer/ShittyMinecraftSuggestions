package shittymcsuggestions.enchantment;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import shittymcsuggestions.mixin.ItemUsageContextAccessor;

public class LighterEnchantment extends Enchantment {

    protected LighterEnchantment(Weight weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMaximumPower(int level) {
        return 50;
    }

    public static void addEvent() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (EnchantmentHelper.getLevel(ModEnchantments.LIGHTER, stack) <= 0)
                return ActionResult.PASS;

            ItemUsageContext usageContext = new ItemUsageContext(player, hand, hitResult);
            //noinspection ConstantConditions
            ((ItemUsageContextAccessor) usageContext).setStack(new ItemStack(Items.TORCH));

            if (!Items.TORCH.useOnBlock(usageContext).isAccepted())
                return ActionResult.PASS;

            if (!world.isClient && !player.abilities.creativeMode)
                stack.damage(1, player, p -> p.sendToolBreakStatus(hand));

            return ActionResult.SUCCESS;
        });
    }
}
