package shittymcsuggestions.item;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearHandler {

    private static boolean onShear(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (entity instanceof HorseEntity) {
            if (!world.isClient) {
                HorseEntity horse = (HorseEntity) entity;
                SkeletonHorseEntity skeletonHorse = EntityType.SKELETON_HORSE.create(world);
                if (skeletonHorse != null) {
                    int leatherCount = 1 + horse.getRandom().nextInt(3);
                    for (int i = 0; i < leatherCount; i++)
                        horse.dropItem(Items.LEATHER);
                    copyEntity(horse, skeletonHorse);
                    skeletonHorse.setOwnerUuid(horse.getOwnerUuid());
                    horse.remove();
                    world.spawnEntity(skeletonHorse);
                }
            }
            return true;
        }
        if (entity instanceof ZombieEntity) {
            if (!world.isClient) {
                ZombieEntity zombie = (ZombieEntity) entity;
                SkeletonEntity skeleton = EntityType.SKELETON.create(world);
                if (skeleton != null) {
                    int fleshCount = 1 + zombie.getRandom().nextInt(2);
                    for (int i = 0; i < fleshCount; i++)
                        zombie.dropItem(Items.ROTTEN_FLESH);
                    copyEntity(zombie, skeleton);
                    zombie.remove();
                    world.spawnEntity(skeleton);
                }
            }
            return true;
        }
        return false;
    }

    private static void copyEntity(MobEntity from, MobEntity to) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack stack = from.getEquippedStack(equipmentSlot);
            if (!stack.isEmpty()) {
                if (EnchantmentHelper.hasBindingCurse(stack)) {
                    to.equipStack(equipmentSlot, stack);
                } else {
                    from.dropStack(stack);
                }
            }
        }

        to.initialize(from.world, from.world.getLocalDifficulty(new BlockPos(from)), SpawnType.CONVERSION, null, null);
        to.copyPositionAndRotation(from);
        if (from.hasCustomName()) {
            to.setCustomName(from.getCustomName());
            to.setCustomNameVisible(from.isCustomNameVisible());
        }
        to.setInvulnerable(from.isInvulnerable());
    }

    public static void reigsterEvent() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!player.isSpectator()) {
                ItemStack shears = player.getStackInHand(hand);
                if (shears.getItem() == Items.SHEARS) {
                    if (onShear(player, world, hand, entity, hitResult)) {
                        if (!player.abilities.creativeMode)
                            shears.damage(1, player, p -> p.sendToolBreakStatus(hand));
                        player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

}
