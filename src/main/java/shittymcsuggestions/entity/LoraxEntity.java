package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import shittymcsuggestions.ModSounds;

import java.util.Random;

public class LoraxEntity extends ZombieEntity {

    public LoraxEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.345);
    }

    @Override
    protected boolean burnsInDaylight() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_LORAX_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ENTITY_LORAX_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_LORAX_DEATH;
    }

    protected SoundEvent getStepSound() {
        return ModSounds.ENTITY_LORAX_STEP;
    }

    public static void spawnLorax(World world, PlayerEntity player, Random rand) {
        double angle = rand.nextDouble() * 2 * Math.PI;
        double distance = 5 + rand.nextDouble() * 10;
        BlockPos pos = new BlockPos(player.getX() + distance * Math.cos(angle), 0, player.getZ() + distance * Math.sin(angle));
        pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);

        LoraxEntity lorax = ModEntities.LORAX.create(world);
        if (lorax != null) {
            LightningEntity lightning = new LightningEntity(world, pos.getX(), pos.getY(), pos.getZ(), true);
            ((ServerWorld) world).addLightning(lightning);

            lorax.initialize(world, world.getLocalDifficulty(pos), SpawnType.TRIGGERED, null, null);
            lorax.updatePositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), rand.nextFloat() * 2 * (float)Math.PI, 0);
            lorax.setAttacker(player);
            world.spawnEntity(lorax);
        }
    }
}
