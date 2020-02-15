package shittymcsuggestions.mixin;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.block.ICustomHopper;

@Mixin(HopperBlockEntity.class)
public class MixinHopperBlockEntity extends BlockEntity {

    public MixinHopperBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    @Inject(method = "insert", at = @At("HEAD"), cancellable = true)
    private void onInsert(CallbackInfoReturnable<Boolean> ci) {
        BlockState state = getCachedState();
        Block block = state.getBlock();
        if (block instanceof ICustomHopper) {
            ICustomHopper hopper = (ICustomHopper) block;
            TriState result = hopper.doCustomInsertion(world, pos, state, (HopperBlockEntity) (Object) this);
            if (result != TriState.DEFAULT) {
                ci.setReturnValue(result.get());
            }
        }
    }

    @Inject(method = "getContainerName", at = @At("HEAD"), cancellable = true)
    private void onGetContainerName(CallbackInfoReturnable<Text> ci) {
        Block block = getCachedState().getBlock();
        if (block instanceof ICustomHopper) {
            String name = ((ICustomHopper) block).getCustomContainerName();
            if (name != null) {
                ci.setReturnValue(new TranslatableText(name));
            }
        }
    }

}
