package shittymcsuggestions.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BeeEntity.class)
@Environment(EnvType.CLIENT)
public abstract class MixinBeeEntity extends AnimalEntity {
    protected MixinBeeEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 140 * 140;
    }
}
