package shittymcsuggestions.item;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

public class ModAxeItem extends AxeItem {
    protected ModAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
