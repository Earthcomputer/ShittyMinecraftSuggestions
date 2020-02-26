package shittymcsuggestions.mixin.accessor;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeeEntity.class)
public interface BeeEntityAccessor {

    @Invoker
    void callSetAnger(int ticks);

}
