package shittymcsuggestions.entity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import shittymcsuggestions.ModSounds;

public class SheepAttackGoal extends MeleeAttackGoal {

    public SheepAttackGoal(MobEntityWithAi sheep, double attackSpeed, boolean pauseWhenMobIdle) {
        super(sheep, attackSpeed, pauseWhenMobIdle);
    }

    @Override
    protected void attack(LivingEntity target, double distanceSq) {
        double maxDistanceSq = this.getSquaredMaxAttackDistance(target);
        if (distanceSq <= maxDistanceSq && this.ticksUntilAttack <= 0) {
            this.ticksUntilAttack = 20;
            this.mob.swingHand(Hand.MAIN_HAND);
            if (this.mob.tryAttack(target)) {
                mob.world.playSoundFromEntity(null, target, ModSounds.SHEEP_HIT, mob.getSoundCategory(), 1, 1);

                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
                        ItemStack stack = target.getEquippedStack(equipmentSlot);
                        if (!stack.isEmpty()) {
                            if (stack.isDamageable()) {
                                stack.setDamage(Math.max(0, stack.getMaxDamage() - 10));
                            } else {
                                target.dropStack(stack);
                                target.equipStack(equipmentSlot, ItemStack.EMPTY);
                            }
                        }
                    }
                }

                Vec3d flingVec = new Vec3d(target.getX() - mob.getX(), 0, target.getZ() - mob.getZ());
                flingVec = flingVec.normalize();
                flingVec = flingVec.add(0, 0.03, 0);
                flingVec = flingVec.multiply(10);
                target.setVelocity(target.getVelocity().add(flingVec));

                ((ISheep) mob).sms_setAngry(false);
            }
        }
    }
}
