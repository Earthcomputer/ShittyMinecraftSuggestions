package shittymcsuggestions.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.WeightedPicker;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import shittymcsuggestions.entity.FlippedMobs;
import shittymcsuggestions.mixin.accessor.WeightedPickerEntryAccessor;

import java.util.List;
import java.util.Random;

@Mixin(SpawnHelper.class)
public class MixinSpawnHelper {

    @Unique private static ThreadLocal<Biome.SpawnEntry> actualSpawnEntry = new ThreadLocal<>();

    @Redirect(method = "spawnEntitiesInChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;"))
    private static Entity redirectCreateEntity1(EntityType<?> type, World world) {
        if (world.random.nextInt(4) == 0)
            return FlippedMobs.getFlippedEntityType(type).create(world);
        else
            return type.create(world);
    }

    @Redirect(method = "populateEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/WeightedPicker;getRandom(Ljava/util/Random;Ljava/util/List;)Lnet/minecraft/util/WeightedPicker$Entry;"))
    private static WeightedPicker.Entry redirectChoosePackType(Random rand, List<? extends WeightedPicker.Entry> list) {
        WeightedPicker.Entry entry = WeightedPicker.getRandom(rand, list);
        actualSpawnEntry.set((Biome.SpawnEntry) entry);
        return entry;
    }

    @ModifyVariable(method = "populateEntities", ordinal = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SpawnHelper;getEntitySpawnPos(Lnet/minecraft/world/WorldView;Lnet/minecraft/entity/EntityType;II)Lnet/minecraft/util/math/BlockPos;"))
    private static Biome.SpawnEntry flipEntityType(Biome.SpawnEntry entry, IWorld world, Biome biome, int chunkX, int chunkZ, Random rand) {
        if (rand.nextInt(4) == 0) {
            EntityType<?> newType = FlippedMobs.getFlippedEntityType(actualSpawnEntry.get().type);
            if (newType != entry.type) {
                return new Biome.SpawnEntry(newType, ((WeightedPickerEntryAccessor) entry).getWeight(), entry.minGroupSize, entry.maxGroupSize);
            }
        }
        return entry;
    }

}
