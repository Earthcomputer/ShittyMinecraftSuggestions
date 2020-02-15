package shittymcsuggestions.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;

public class NameTagHandler {

    public static void onMobNamed(PlayerEntity player, LivingEntity target, String name) {
        if (target instanceof PigEntity && "Reuben".equalsIgnoreCase(name)) {
            target.damage(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
        }
    }

}
