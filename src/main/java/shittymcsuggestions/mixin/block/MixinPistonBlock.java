package shittymcsuggestions.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shittymcsuggestions.block.ICustomPiston;

@Mixin(PistonBlock.class)
public class MixinPistonBlock {

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;PISTON_HEAD:Lnet/minecraft/block/Block;"))
    private Block getPistonHeadBlock() {
        return this instanceof ICustomPiston ? ((ICustomPiston) this).getCustomPistonHeadBlock() : Blocks.PISTON_HEAD;
    }

}
