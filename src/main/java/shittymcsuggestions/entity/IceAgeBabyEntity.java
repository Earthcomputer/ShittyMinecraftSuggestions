package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import shittymcsuggestions.ModSounds;

import java.util.Random;

public class IceAgeBabyEntity extends PigEntity {

    public IceAgeBabyEntity(EntityType<? extends PigEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.345);
    }


    public static void spawnIceAgeBaby(World world, PlayerEntity player, Random rand) {
        double angle = rand.nextDouble() * 2 * Math.PI;
        double distance = 5 + rand.nextDouble() * 10;
        BlockPos pos = new BlockPos(player.getX() + distance * Math.cos(angle), 0, player.getZ() + distance * Math.sin(angle));
        pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);

        IceAgeBabyEntity iceBaby = ModEntities.ICE_AGE_BABY.create(world);
        if (iceBaby != null) {
            LightningEntity lightning = new LightningEntity(world, pos.getX(), pos.getY(), pos.getZ(), true);
            ((ServerWorld) world).addLightning(lightning);

            iceBaby.initialize(world, world.getLocalDifficulty(pos), SpawnType.TRIGGERED, null, null);
            iceBaby.updatePositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), rand.nextFloat() * 2 * (float)Math.PI, 0);
            iceBaby.setAttacker(player);
            world.spawnEntity(iceBaby);
        }
    }
}
