package shittymcsuggestions.item;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class BedrockToolMaterial implements ToolMaterial {

    public static final BedrockToolMaterial INSTANCE = new BedrockToolMaterial();

    @Override
    public int getDurability() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getMiningSpeed() {
        return 4;
    }

    @Override
    public float getAttackDamage() {
        return 1;
    }

    @Override
    public int getMiningLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.BEDROCK);
    }
}
