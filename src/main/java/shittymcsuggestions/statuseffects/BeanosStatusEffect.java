package shittymcsuggestions.statuseffects;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AbstractEntityAttributeContainer;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.server.network.ServerPlayerEntity;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.entity.ExplodingArrowEntity;

public class BeanosStatusEffect extends ModStatusEffect {

    private static final AbilitySource ABILITY_SOURCE = Pal.getAbilitySource(ShittyMinecraftSuggestions.MODID, "beanos");

    protected BeanosStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AbstractEntityAttributeContainer attributes, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            Pal.grantAbility(player, VanillaAbilities.ALLOW_FLYING, ABILITY_SOURCE);
            Pal.grantAbility(player, VanillaAbilities.FLYING, ABILITY_SOURCE);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AbstractEntityAttributeContainer attributes, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            Pal.revokeAbility(player, VanillaAbilities.ALLOW_FLYING, ABILITY_SOURCE);
            Pal.revokeAbility(player, VanillaAbilities.FLYING, ABILITY_SOURCE);
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
