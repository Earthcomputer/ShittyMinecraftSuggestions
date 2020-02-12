package shittymcsuggestions.criterion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class StatCriterion extends AbstractCriterion<StatCriterion.Conditions> {

    @Override
    public Identifier getId() {
        return new Identifier(ShittyMinecraftSuggestions.MODID, "stat");
    }

    @Override
    public Conditions conditionsFromJson(JsonObject obj, JsonDeserializationContext context) {
        if (!obj.has("name"))
            throw new JsonSyntaxException("Missing name");
        JsonElement nameElem = obj.get("name");
        if (!nameElem.isJsonPrimitive())
            throw new JsonSyntaxException("Expected name to be a stat, was " + JsonHelper.getType(nameElem));
        Stat<?> name = parseStat(nameElem.getAsString());
        NumberRange.IntRange count = NumberRange.IntRange.fromJson(obj.get("count"));
        return new Conditions(getId(), name, count);
    }

    private static Identifier parseDotIdentifier(String str) {
        int dotIndex = str.indexOf('.');
        if (dotIndex == -1)
            return new Identifier(str);
        else
            return new Identifier(str.substring(0, dotIndex), str.substring(dotIndex + 1));
    }

    @SuppressWarnings("unchecked")
    private static <T> Stat<T> parseStat(String str) {
        Identifier fullId = new Identifier(str);
        Identifier statTypeId = parseDotIdentifier(fullId.getNamespace());
        Identifier registryId = parseDotIdentifier(fullId.getPath());
        StatType<T> statType = (StatType<T>) Registry.STAT_TYPE.getOrEmpty(statTypeId).orElseThrow(() -> new JsonSyntaxException("Expected name to be a stat, was unknown string '" + str + "'"));
        T value = statType.getRegistry().getOrEmpty(registryId).orElseThrow(() -> new JsonSyntaxException("Expected name to be a stat, was unknown string '" + str + "'"));
        return statType.getOrCreateStat(value);
    }

    public void trigger(ServerPlayerEntity player, Stat<?> stat, int amount) {
        test(player.getAdvancementTracker(), conditions -> conditions.test(stat, amount));
    }

    public static class Conditions extends AbstractCriterionConditions {

        private Stat<?> name;
        private NumberRange.IntRange count;

        public Conditions(Identifier id, Stat<?> name, NumberRange.IntRange count) {
            super(id);
            this.name = name;
            this.count = count;
        }

        public boolean test(Stat<?> stat, int amount) {
            return stat == name && count.test(amount);
        }
    }

}
