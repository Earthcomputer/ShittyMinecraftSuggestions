package shittymcsuggestions.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.ModSounds;

@Mixin(CreeperEntity.class)
public class ClientMixinCreeperEntity extends HostileEntity {

    protected ClientMixinCreeperEntity(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/mob/CreeperEntity;lastFuseTime:I"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && isPlayerStaring(player) && random.nextInt(10) == 0) {
            SoundEvent sound = player.inventory.armor.get(3).getItem() == Items.CARVED_PUMPKIN ? ModSounds.AWRAPE : ModSounds.AWMAN;
            world.playSound(getX(), getY(), getZ(), sound, SoundCategory.HOSTILE, 1, 1, true);
        }
    }

    @Unique
    private boolean isPlayerStaring(PlayerEntity playerEntity) {
        Vec3d playerRotVec = playerEntity.getRotationVec(1.0F).normalize();
        Vec3d thisPosRelativeToPlayer = new Vec3d(getX() - playerEntity.getX(), getEyeY() - playerEntity.getEyeY(), getZ() - playerEntity.getZ());
        double distance = thisPosRelativeToPlayer.length();
        thisPosRelativeToPlayer = thisPosRelativeToPlayer.normalize();
        double dot = playerRotVec.dotProduct(thisPosRelativeToPlayer);
        return dot > 1.0D - 0.025D / distance && playerEntity.canSee(this);
    }

}
