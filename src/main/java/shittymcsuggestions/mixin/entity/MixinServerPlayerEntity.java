package shittymcsuggestions.mixin.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ModSounds;
import shittymcsuggestions.criterion.ModCriterions;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity {

    public MixinServerPlayerEntity(World world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V", ordinal = 0))
    private void onOnDeath(DamageSource source, CallbackInfo ci) {
        ModCriterions.PLAYER_DEATH.trigger((ServerPlayerEntity) (Object) this, getPrimeAdversary(), source);
    }

    @Inject(method = "dropItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private void onDropItem(CallbackInfoReturnable<ItemEntity> ci) {
        if (random.nextInt(10) == 0) {
            world.playSoundFromEntity(null, this, ModSounds.YEET, SoundCategory.PLAYERS, 0.5f + random.nextFloat(), 1 + random.nextFloat());
        }
    }

}
