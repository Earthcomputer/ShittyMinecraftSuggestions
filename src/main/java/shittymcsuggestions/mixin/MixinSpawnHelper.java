package shittymcsuggestions.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shittymcsuggestions.entity.FlippedMobs;

@Mixin(SpawnHelper.class)
public class MixinSpawnHelper {

    @Redirect(method = "spawnEntitiesInChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;"))
    private static Entity redirectCreateEntity1(EntityType<?> type, World world) {
        return redirectCreateEntity(type, world);
    }

    @Redirect(method = "populateEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;"))
    private static Entity redirectCreateEntity2(EntityType<?> type, World world) {
        return redirectCreateEntity(type, world);
    }

    @Unique
    private static Entity redirectCreateEntity(EntityType<?> type, World world) {
        if (world.random.nextInt(4) == 0)
            return FlippedMobs.getFlippedEntityType(type).create(world);
        else
            return type.create(world);
    }

}
