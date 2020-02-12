package shittymcsuggestions.criterion;

import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.mixin.CriterionsAccessor;

public class ModCriterions {

    public static final CraftItemCriterion CRAFT_ITEM = new CraftItemCriterion();
    public static final StatCriterion STAT = new StatCriterion();
    public static final OnKilledCriterion PLAYER_DEATH = new OnKilledCriterion(new Identifier(ShittyMinecraftSuggestions.MODID, "player_death"));

    private static void registerCriterion(Criterion<?> criterion) {
        CriterionsAccessor.callRegister(criterion);
    }

    public static void register() {
        registerCriterion(CRAFT_ITEM);
        registerCriterion(STAT);
        registerCriterion(PLAYER_DEATH);
    }

}
