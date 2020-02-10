package shittymcsuggestions;

import net.fabricmc.api.ModInitializer;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.item.ModItems;

public class ShittyMinecraftSuggestions implements ModInitializer {

	public static final String MODID = "shittymcsuggestions";

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
	}
}
