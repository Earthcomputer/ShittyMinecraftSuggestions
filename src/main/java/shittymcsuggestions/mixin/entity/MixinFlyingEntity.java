package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.entity.IEntity;

@Mixin(FlyingEntity.class)
public class MixinFlyingEntity extends MobEntity {

    protected MixinFlyingEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void onTravel(Vec3d movementInput, CallbackInfo ci) {
        boolean cancel = false;

        if (((IEntity) this).sms_isInHoney()) {
            updateVelocity(0.02f, movementInput);
            move(MovementType.SELF, getVelocity());
            setVelocity(getVelocity().multiply(0.5));
            cancel = true;
        }

        if (cancel) {
            lastLimbDistance = limbDistance;
            double xDelta = getX() - prevX;
            double zDelta = getZ() - prevZ;
            float deltaLimbs = MathHelper.sqrt(xDelta * xDelta + zDelta * zDelta) * 4;
            if (deltaLimbs > 1) {
                deltaLimbs = 1;
            }

            limbDistance += (deltaLimbs - limbDistance) * 0.4;
            limbAngle += limbDistance;
            ci.cancel();
        }
    }

}
