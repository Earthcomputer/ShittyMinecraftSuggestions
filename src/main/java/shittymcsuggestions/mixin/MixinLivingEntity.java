package shittymcsuggestions.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "spawnConsumptionEffects",
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/util/UseAction;EAT:Lnet/minecraft/util/UseAction;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V", ordinal = 0))
    private void onEat(ItemStack stack, int particleCount, CallbackInfo ci) {
        if (!world.isClient && stack.getItem() == Items.MELON) {
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
