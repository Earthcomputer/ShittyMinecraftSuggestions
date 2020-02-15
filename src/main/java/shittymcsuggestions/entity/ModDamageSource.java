package shittymcsuggestions.entity;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {

    public static final DamageSource GLASS = new ModDamageSource("glass").setUnblockable().setBypassesArmor();

    protected ModDamageSource(String name) {
        super("shittymcsuggestions." + name);
    }

    @Override
    protected ModDamageSource setUnblockable() {
        return (ModDamageSource) super.setUnblockable();
    }
}
