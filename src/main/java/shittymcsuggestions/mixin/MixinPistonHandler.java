package shittymcsuggestions.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import shittymcsuggestions.block.ICustomPiston;

@Mixin(PistonHandler.class)
public class MixinPistonHandler {

    @Shadow @Final private World world;
    @Shadow @Final private BlockPos posFrom;

    @ModifyConstant(method = "tryMove", constant = @Constant(intValue = 12))
    private int modifyPushLimit(int pushLimit) {
        Block pistonBlock = world.getBlockState(posFrom).getBlock();
        return pistonBlock instanceof ICustomPiston ? ((ICustomPiston) pistonBlock).getPushLimit() : pushLimit;
    }

}
