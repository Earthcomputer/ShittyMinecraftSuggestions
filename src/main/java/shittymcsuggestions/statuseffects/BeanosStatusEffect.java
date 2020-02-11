package shittymcsuggestions.statuseffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AbstractEntityAttributeContainer;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.server.network.ServerPlayerEntity;
import shittymcsuggestions.entity.ExplodingArrowEntity;

public class BeanosStatusEffect extends ModStatusEffect {

    protected BeanosStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AbstractEntityAttributeContainer attributes, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            player.abilities.allowFlying = true;
            player.abilities.flying = true;
            player.sendAbilitiesUpdate();
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AbstractEntityAttributeContainer attributes, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            player.interactionManager.getGameMode().setAbilitites(player.abilities);
            player.sendAbilitiesUpdate();
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int i) {
        return duration % 2 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int i) {
        ExplodingArrowEntity arrow = new ExplodingArrowEntity(entity, entity.world);
        arrow.setProperties(entity, entity.pitch, entity.yaw, 0, 3, 1);
        entity.world.spawnEntity(arrow);
    }
}
