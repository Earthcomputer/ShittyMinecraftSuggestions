package shittymcsuggestions.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.DonkeyEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ModSounds;

@Mixin(DonkeyEntity.class)
public class MixinDonkeyEntity extends AbstractDonkeyEntity {

    protected MixinDonkeyEntity(EntityType<? extends AbstractDonkeyEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "createChild", at = @At("HEAD"))
    private void onCreateChild(PassiveEntity other, CallbackInfoReturnable<PassiveEntity> ci) {
        if (other instanceof DonkeyEntity) {
            world.playSound(null, getX(), getY(), getZ(), ModSounds.FUCKING_DONKEY, SoundCategory.NEUTRAL, 1, 1);
        }
    }

}
