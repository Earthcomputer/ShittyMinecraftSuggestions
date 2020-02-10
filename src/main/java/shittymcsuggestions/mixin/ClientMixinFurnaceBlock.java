package shittymcsuggestions.mixin;

import net.minecraft.block.FurnaceBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FurnaceBlock.class)
public class ClientMixinFurnaceBlock {

    @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V", ordinal = 0))
    private void redirectCrackleSound(World world, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean bl) {
        // nop, replaced with serverside handling
    }

}
