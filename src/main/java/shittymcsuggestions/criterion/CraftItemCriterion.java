package shittymcsuggestions.criterion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class CraftItemCriterion extends AbstractCriterion<CraftItemCriterion.Conditions> {

    @Override
    public Identifier getId() {
        return new Identifier(ShittyMinecraftSuggestions.MODID, "craft_item");
    }

    @Override
    public Conditions conditionsFromJson(JsonObject obj, JsonDeserializationContext context) {
        ItemPredicate predicate = ItemPredicate.fromJson(obj.get("predicate"));
        return new Conditions(getId(), predicate);
    }

    public void trigger(ServerPlayerEntity player, ItemStack crafted) {
        test(player.getAdvancementTracker(), conditions -> conditions.test(crafted));
    }

    public static class Conditions extends AbstractCriterionConditions {

        private ItemPredicate predicate;

        public Conditions(Identifier id, ItemPredicate predicate) {
            super(id);
            this.predicate = predicate;
        }

        public boolean test(ItemStack crafted) {
            return predicate.test(crafted);
        }
    }

}
