package shittymcsuggestions.mixin.accessor;

import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.Criterions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Criterions.class)
public interface CriterionsAccessor {

    @Invoker
    static <T extends Criterion<?>> T callRegister(T criterion) {
        return criterion;
    }

}
