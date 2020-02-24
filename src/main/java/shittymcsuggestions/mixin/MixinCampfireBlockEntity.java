package shittymcsuggestions.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public class MixinCampfireBlockEntity extends BlockEntity {

    public MixinCampfireBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    @Inject(method = "spawnItemsBeingCooked", at = @At("HEAD"), cancellable = true)
    private void onSpawnItemsBeingCooked(CallbackInfo ci) {
        // Fix for https://bugs.mojang.com/browse/MC-173067
        if (!hasWorld())
            ci.cancel();
    }

}
