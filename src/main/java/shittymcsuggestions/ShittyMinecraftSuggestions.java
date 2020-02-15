package shittymcsuggestions;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import shittymcsuggestions.block.ModBlocks;
import shittymcsuggestions.criterion.ModCriterions;
import shittymcsuggestions.enchantment.ModEnchantments;
import shittymcsuggestions.entity.ModEntities;
import shittymcsuggestions.item.ModItems;
import shittymcsuggestions.mixin.BlockAccessor;
import shittymcsuggestions.mixin.BlockEntityTypeAccessor;
import shittymcsuggestions.mixin.ItemAccessor;
import shittymcsuggestions.mixin.SpawnEggItemAccessor;
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
		((SpawnEggItemAccessor) Items.SILVERFISH_SPAWN_EGG).setPrimaryColor(0xffb433);
		((SpawnEggItemAccessor) Items.SILVERFISH_SPAWN_EGG).setSecondaryColor(0xff8000);
		((BlockEntityTypeAccessor) BlockEntityType.HOPPER).setBlocks(ImmutableSet.<Block>builder()
				.addAll(((BlockEntityTypeAccessor) BlockEntityType.HOPPER).getBlocks())
				.add(ModBlocks.GRASSHOPPER)
				.build());
	}
}
