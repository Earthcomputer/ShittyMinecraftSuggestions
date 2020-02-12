package shittymcsuggestions.item;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class ModPickaxeItem extends PickaxeItem {
    protected ModPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
