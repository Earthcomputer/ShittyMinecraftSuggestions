package shittymcsuggestions.statuseffects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModStatusEffects {

    public static StatusEffect BEANOS = new BeanosStatusEffect(StatusEffectType.BENEFICIAL, 0x8132a8);

    public static void registerEffect(String name, StatusEffect effect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(ShittyMinecraftSuggestions.MODID, name), effect);
    }

    public static void register() {
        registerEffect("beanos", BEANOS);
    }

}
