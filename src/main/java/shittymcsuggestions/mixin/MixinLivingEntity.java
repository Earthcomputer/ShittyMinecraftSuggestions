package shittymcsuggestions.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ModSounds;
import shittymcsuggestions.block.ModFluidTags;
import shittymcsuggestions.entity.IEntity;
import shittymcsuggestions.entity.ModDamageSource;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot equipmentSlot);

    @Shadow public float lastLimbDistance;

    @Shadow public float limbDistance;

    @Shadow public float limbAngle;

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow protected abstract void swimUpward(Tag<Fluid> fluid);

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "spawnConsumptionEffects",
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/util/UseAction;EAT:Lnet/minecraft/util/UseAction;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V", ordinal = 0))
    private void onEat(ItemStack stack, int particleCount, CallbackInfo ci) {
        if (!world.isClient) {
            if (stack.getItem() == Items.MELON) {
                LlamaSpitEntity spit = EntityType.LLAMA_SPIT.create(world);
                if (spit != null) {
                    Vec3d rotationVec = getRotationVec(1).normalize();
                    spit.updatePosition(getX() + rotationVec.x, getEyeY() + rotationVec.y, getZ() + rotationVec.z);
                    spit.setVelocity(rotationVec.x, rotationVec.y, rotationVec.z, 1.5f, 10f);
                    world.spawnEntity(spit);
                }
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;knockbackVelocity:F", ordinal = 0), cancellable = true)
    private void onDamage(DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> ci) {
        if (damageSource == DamageSource.FALL
                && (getEquippedStack(EquipmentSlot.MAINHAND).getItem() == Items.SHEARS
                || getEquippedStack(EquipmentSlot.OFFHAND).getItem() == Items.SHEARS)) {
            ci.setReturnValue(damage(ModDamageSource.FALL_SHEARS, Float.MAX_VALUE));
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void onOnDeath(DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> ci) {
        if (damageSource == DamageSource.FALL || damageSource == ModDamageSource.FALL_SHEARS) {
            world.playSound(null, getX(), getY(), getZ(), ModSounds.DEATH_BY_FALL, getSoundCategory(), 1, 1);
        }
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isTouchingWater()Z"), cancellable = true)
    private void onTravel(Vec3d movementInput, CallbackInfo ci) {
        boolean cancel = false;

        //noinspection ConstantConditions
        if (!((Object) this instanceof PlayerEntity) || !((PlayerEntity) (Object) this).abilities.flying) {
            if (((IEntity) this).sms_isInHoney()) {
                double fallMultiplier = hasStatusEffect(StatusEffects.SLOW_FALLING) ? 0.01 : 0.08;
                double oldY = this.getY();
                updateVelocity(0.02f, movementInput);
                move(MovementType.SELF, getVelocity());
                setVelocity(getVelocity().multiply(0.5));
                if (!hasNoGravity()) {
                    setVelocity(getVelocity().add(0, -fallMultiplier / 4, 0));
                }

                Vec3d velocity = getVelocity();
                if (horizontalCollision && doesNotCollide(velocity.x, velocity.y + 0.6 - getY() + oldY, velocity.z)) {
                    setVelocity(velocity.x, 0.3, velocity.z);
                }
                cancel = true;
            }
        }

        if (cancel) {
            lastLimbDistance = limbDistance;
            double xDelta = getX() - prevX;
            double zDelta = getZ() - prevZ;
            double yDelta = this instanceof Flutterer ? getY() - prevY : 0;
            double deltaLimbs = MathHelper.sqrt(xDelta * xDelta + yDelta * yDelta + zDelta * zDelta) * 4;
            if (deltaLimbs > 1) {
                deltaLimbs = 1;
            }

            limbDistance += (deltaLimbs - limbDistance) * 0.4F;
            limbAngle += limbDistance;
            ci.cancel();
        }
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInLava()Z"))
    private void onTickMovement(CallbackInfo ci) {
        if (((IEntity) this).sms_isInHoney()) {
            swimUpward(ModFluidTags.HONEY);
        }
    }

}
