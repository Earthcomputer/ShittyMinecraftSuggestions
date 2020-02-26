package shittymcsuggestions.mixin.entity;

import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import shittymcsuggestions.item.ModItems;

@Mixin(ChickenEntity.class)
public abstract class MixinChickenEntity extends AnimalEntity {
    protected MixinChickenEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean interactMob(PlayerEntity player, Hand hand) {
        if (!this.isBaby()) {
            ItemStack playerHand = player.getStackInHand(hand);
            if (playerHand.getItem() == Items.BUCKET && this.isAlive()) {
                this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                playerHand.decrement(1);
                ItemStack chickenBucketItem = new ItemStack(ModItems.CHICKEN_BUCKET);
                if (this.hasCustomName()) {
                    chickenBucketItem.setCustomName(this.getCustomName());
                }
                if (!this.world.isClient) {
                    Criterions.FILLED_BUCKET.trigger((ServerPlayerEntity) player, chickenBucketItem);
                }
                if (playerHand.isEmpty()) {
                    player.setStackInHand(hand, chickenBucketItem);
                } else if (!player.inventory.insertStack(chickenBucketItem)) {
                    player.dropItem(chickenBucketItem, false);
                }

                this.remove();
                return true;
            }
        }
        return super.interactMob(player, hand);
    }
}
