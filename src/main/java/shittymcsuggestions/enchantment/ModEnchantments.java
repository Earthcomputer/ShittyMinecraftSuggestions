package shittymcsuggestions.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModEnchantments {

    public static final Enchantment LIGHTER = new LighterEnchantment(Enchantment.Weight.COMMON, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

    private static void registerEnchantment(String name, Enchantment enchantment) {
        Registry.register(Registry.ENCHANTMENT, new Identifier(ShittyMinecraftSuggestions.MODID, name), enchantment);
    }

    public static void register() {
        registerEnchantment("lighter", LIGHTER);

        LighterEnchantment.addEvent();
    }

}
