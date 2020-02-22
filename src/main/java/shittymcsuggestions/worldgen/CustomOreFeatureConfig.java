package shittymcsuggestions.worldgen;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomOreFeatureConfig extends OreFeatureConfig {
    public final CustomTarget customTarget;

    public CustomOreFeatureConfig(CustomTarget target, BlockState state, int size) {
        super(Target.NATURAL_STONE, state, size);
        this.customTarget = target;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        Dynamic<T> dyn = super.serialize(ops);
        dyn.set("customTarget", new Dynamic<>(ops, ops.createString(customTarget.name)));
        return dyn;
    }

    public static CustomOreFeatureConfig deserialize(Dynamic<?> dyn) {
        OreFeatureConfig oreFeatureConfig = OreFeatureConfig.deserialize(dyn);
        CustomTarget customTarget = CustomTarget.BY_NAME.get(dyn.get("customTarget").asString(""));
        return new CustomOreFeatureConfig(customTarget, oreFeatureConfig.state, oreFeatureConfig.size);
    }

    public enum CustomTarget {
        HONEYCOMB("honeycomb", state -> state.getBlock() == Blocks.HONEYCOMB_BLOCK),
        ;

        private static final Map<String, CustomTarget> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(it -> it.name, it -> it));
        private final String name;
        private final Predicate<BlockState> predicate;
        CustomTarget(String name, Predicate<BlockState> predicate) {
            this.name = name;
            this.predicate = predicate;
        }

        public Predicate<BlockState> getPredicate() {
            return predicate;
        }
    }
}
