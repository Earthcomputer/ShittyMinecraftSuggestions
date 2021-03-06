package shittymcsuggestions.mixin.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.ModSounds;
import shittymcsuggestions.entity.IEntity;
import shittymcsuggestions.entity.PortalCooldownHelper;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ClientPlayerEntity.class)
@Environment(EnvType.CLIENT)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {

    @Shadow @Final protected MinecraftClient client;

    @Shadow public abstract void closeContainer();

    @Shadow public float nextNauseaStrength;
    @Unique private static final double CHINA_VELOCITY = 0.25;
    @Unique private boolean hasReachedChinaVelocity;
    @Unique private int chinaBits;
    @Unique private int ticksSinceUpwardVelocity = 0;
    @Unique private boolean hasPlayedCavebee = true; // disable on first login

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
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

        if (chinaTicks >= 11 && ticksSinceUpwardVelocity > 30 && getY() < world.getSeaLevel()) {
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
                if (this.getName().getString().equalsIgnoreCase("samnrad"))
                    world.playSoundFromEntity(this, this, ModSounds.CAVEBEE, SoundCategory.PLAYERS, 1, 1);
            }
        } else if (getY() > 22) {
            hasPlayedCavebee = false;
        }
    }

    @Inject(method = "updateNausea",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z")),
            at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, ordinal = 2, target = "Lnet/minecraft/client/network/ClientPlayerEntity;nextNauseaStrength:F"),
            cancellable = true)
    private void customUpdateNausea(CallbackInfo ci) {
        List<PortalCooldownHelper<?>> cooldownHelpers = ((IEntity) this).sms_getPortalCooldownHelpers().values().stream()
                .filter(it -> it.isInPortalWarmingUp() && it.getPortalBlock().causesNausea())
                .collect(Collectors.toList());
        if (!cooldownHelpers.isEmpty()) {
            if (client.currentScreen != null && !client.currentScreen.isPauseScreen()) {
                if (client.currentScreen instanceof ContainerScreen) {
                    closeContainer();
                }
                client.openScreen(null);
            }

            if (nextNauseaStrength == 0) {
                SoundEvent triggerSound = null;
                for (PortalCooldownHelper<?> cooldownHelper : cooldownHelpers) {
                    if (cooldownHelper.getPortalBlock().getTriggerSound() != null) {
                        triggerSound = cooldownHelper.getPortalBlock().getTriggerSound();
                        break;
                    }
                }
                if (triggerSound != null) {
                    client.getSoundManager().play(PositionedSoundInstance.master(triggerSound, random.nextFloat() * 0.4f + 0.8f));
                }
            }

            nextNauseaStrength += 0.0125f;
            if (nextNauseaStrength >= 1)
                nextNauseaStrength = 1;

            cooldownHelpers.forEach(it -> it.setInPortalWarmingUp(false));

            tickNetherPortalCooldown();
            ci.cancel();
        }
    }

    @Inject(method = "updateNausea", at = @At("TAIL"))
    private void postUpdateNausea(CallbackInfo ci) {
        if (nextNauseaStrength == 0)
            ((IEntity) this).sms_setCustomPortalOverlay(null);
    }

}
