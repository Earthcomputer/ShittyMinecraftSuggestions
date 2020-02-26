package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.entity.ISheep;
import shittymcsuggestions.entity.NoPeacefulFollowTargetGoal;
import shittymcsuggestions.entity.SheepAttackGoal;
import shittymcsuggestions.mixin.accessor.GoalSelectorAccessor;

import java.util.UUID;

@Mixin(SheepEntity.class)
public abstract class MixinSheepEntity extends AnimalEntity implements ISheep {

    @Unique private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(
            UUID.fromString("4eef099f-46b5-4348-ac19-d63512aa18f7"),
            "Attacking speed boost",
            2,
            EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
        .setSerialize(false);

    @Unique private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique private NoPeacefulFollowTargetGoal<PlayerEntity> followPlayerGoal;
    @Unique private SheepAttackGoal attackPlayerGoal;
    @Unique private EscapeDangerGoal escapeDangerGoal;

    protected MixinSheepEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("RETURN"))
    private void onInitGoals(CallbackInfo ci) {
        followPlayerGoal = new NoPeacefulFollowTargetGoal<>(this, PlayerEntity.class, false);
        attackPlayerGoal = new SheepAttackGoal(this, 1, false);
        escapeDangerGoal = (EscapeDangerGoal) ((GoalSelectorAccessor) goalSelector).getGoals()
                .stream()
                .filter(it -> it.getGoal() instanceof EscapeDangerGoal)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find EscapeDangerGoal on sheep"))
                .getGoal();
    }

    @Inject(method = "readCustomDataFromTag", at = @At("RETURN"))
    private void onReadNbt(CompoundTag tag, CallbackInfo ci) {
        sms_setAngry(tag.getBoolean(ShittyMinecraftSuggestions.MODID + ":angry"));
    }

    @Inject(method = "writeCustomDataToTag", at = @At("RETURN"))
    private void onWriteNbt(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean(ShittyMinecraftSuggestions.MODID + ":angry", sms_isAngry());
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private void onSheared(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> ci) {
        if (!player.abilities.creativeMode) {
            setAttacker(player);
            sms_setAngry(true);
        }
    }

    @Inject(method = "initAttributes", at = @At("RETURN"))
    private void onInitAttributes(CallbackInfo ci) {
        getAttributes().register(EntityAttributes.ATTACK_DAMAGE).setBaseValue(5);
        getAttributeInstance(EntityAttributes.ATTACK_KNOCKBACK).setBaseValue(1.5);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    private void onInitDataTracker(CallbackInfo ci) {
        dataTracker.startTracking(ANGRY, false);
    }

    @Override
    public boolean sms_isAngry() {
        return dataTracker.get(ANGRY);
    }

    @Override
    public void sms_setAngry(boolean angry) {
        if (angry != sms_isAngry()) {
            dataTracker.set(ANGRY, angry);
            if (angry) {
                goalSelector.add(1, attackPlayerGoal);
                targetSelector.add(1, followPlayerGoal);
                goalSelector.remove(escapeDangerGoal);
            } else {
                goalSelector.remove(attackPlayerGoal);
                targetSelector.remove(followPlayerGoal);
                goalSelector.add(1, escapeDangerGoal);
                getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).removeModifier(ATTACKING_SPEED_BOOST);
            }
        }
    }

    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target);

        EntityAttributeInstance movementSpeed = getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (target == null) {
            movementSpeed.removeModifier(ATTACKING_SPEED_BOOST);
        } else {
            if (!movementSpeed.hasModifier(ATTACKING_SPEED_BOOST))
                movementSpeed.addModifier(ATTACKING_SPEED_BOOST);
        }
    }
}
