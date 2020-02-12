package shittymcsuggestions;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.criterion.ModCriterions;
import shittymcsuggestions.enchantment.ModEnchantments;
import shittymcsuggestions.entity.ModEntities;
import shittymcsuggestions.item.ModItems;
import shittymcsuggestions.mixin.BlockAccessor;
import shittymcsuggestions.mixin.ItemAccessor;
import shittymcsuggestions.statuseffects.ModStatusEffects;

public class ShittyMinecraftSuggestions implements ModInitializer {

	public static final String MODID = "shittymcsuggestions";

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
		ModEntities.register();
		ModSounds.register();
		ModEnchantments.register();
		ModStatusEffects.register();
		ModCriterions.register();

		((BlockAccessor) Blocks.BEDROCK).setDropTableId(new Identifier(MODID, "blocks/bedrock"));
		((ItemAccessor) Items.MELON).setFoodComponent(FoodComponents.MELON_SLICE);
	}
}
