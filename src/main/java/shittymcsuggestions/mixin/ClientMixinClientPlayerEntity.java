package shittymcsuggestions.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.ModSounds;

@Mixin(ClientPlayerEntity.class)
public class ClientMixinClientPlayerEntity extends AbstractClientPlayerEntity {

    @Unique private static final double CHINA_VELOCITY = 0.25;
    @Unique private boolean hasReachedChinaVelocity;
    @Unique private int chinaBits;
    @Unique private int ticksSinceUpwardVelocity = 0;
    @Unique private boolean hasPlayedCavebee = true; // disable on first login

    public ClientMixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        chinaBits <<= 1;
        if (getVelocity().y <= -CHINA_VELOCITY)
            chinaBits |= 1;
        // the number of ticks out of the last 32 ticks that we were at the critical china velocity
        int chinaTicks = Integer.bitCount(chinaBits);
        ticksSinceUpwardVelocity++;
        if (getVelocity().y > 0)
            ticksSinceUpwardVelocity = 0;

        if (chinaTicks >= 11 && ticksSinceUpwardVelocity > 30) {
            if (!hasReachedChinaVelocity) {
                hasReachedChinaVelocity = true;
                world.playSoundFromEntity(this, this, ModSounds.CHINA, SoundCategory.PLAYERS, 1, 1);
            }
        } else if (chinaTicks < 8) {
            hasReachedChinaVelocity = false;
        }

        if (getY() <= 20) {
            if (!hasPlayedCavebee) {
                hasPlayedCavebee = true;
                world.playSoundFromEntity(this, this, ModSounds.CAVEBEE, SoundCategory.PLAYERS, 1, 1);
            }
        } else if (getY() > 22) {
            hasPlayedCavebee = false;
        }
    }

}
