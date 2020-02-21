package shittymcsuggestions.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import shittymcsuggestions.entity.ModEntities;

@Mixin(ThreadedAnvilChunkStorage.class)
public class MixinThreadedAnvilChunkStorage {

    @ModifyVariable(method = "loadEntity", ordinal = 0, at = @At(value = "STORE", ordinal = 0))
    private int modifyTrackingDistance(int oldDistance, Entity entity) {
        return ModEntities.getTrackingDistance(entity, oldDistance);
    }

}
