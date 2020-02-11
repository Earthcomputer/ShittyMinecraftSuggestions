package shittymcsuggestions;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Items;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.item.ModItems;
import shittymcsuggestions.mixin.ItemAccessor;

public class ShittyMinecraftSuggestions implements ModInitializer {

	public static final String MODID = "shittymcsuggestions";

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
		ModSounds.register();

		((ItemAccessor) Items.MELON).setFoodComponent(FoodComponents.MELON_SLICE);
	}
}
