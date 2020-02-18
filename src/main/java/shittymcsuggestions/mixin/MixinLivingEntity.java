package shittymcsuggestions.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import shittymcsuggestions.entity.ModDamageSource;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot equipmentSlot);

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
}
