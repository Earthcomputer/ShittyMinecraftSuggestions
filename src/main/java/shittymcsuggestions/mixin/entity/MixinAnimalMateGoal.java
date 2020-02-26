package shittymcsuggestions.mixin.entity;

import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalMateGoal.class)
public class MixinAnimalMateGoal {

    @Shadow @Final protected AnimalEntity animal;

    @Shadow protected AnimalEntity mate;

    @Shadow @Final protected World world;

    @Inject(method = "breed", at = @At("HEAD"), cancellable = true)
    private void onBreed(CallbackInfo ci) {
        if (animal.isBaby() ^ mate.isBaby() || animal.getRandom().nextInt(100) == 0) {
            CreeperEntity creeper = EntityType.CREEPER.create(world);
            if (creeper == null) return;

            ServerPlayerEntity player = animal.getLovingPlayer();
            if (player == null)
                player = mate.getLovingPlayer();

            if (player != null) {
                player.incrementStat(Stats.ANIMALS_BRED);
                Criterions.BRED_ANIMALS.trigger(player, animal, mate, null);
            }

            animal.setBreedingAge(6000);
            mate.setBreedingAge(6000);
            animal.resetLoveTicks();
            mate.resetLoveTicks();
            creeper.refreshPositionAndAngles(animal.getX(), animal.getY(), animal.getZ(), 0.0F, 0.0F);
            world.spawnEntity(creeper);
            if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                world.spawnEntity(new ExperienceOrbEntity(world, animal.getX(), animal.getY(), animal.getZ(), animal.getRandom().nextInt(7) + 1));
            }

            ci.cancel();
        }
    }

}
