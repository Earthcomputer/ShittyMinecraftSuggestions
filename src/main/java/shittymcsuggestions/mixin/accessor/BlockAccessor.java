package shittymcsuggestions.mixin.accessor;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Block.class)
public interface BlockAccessor {

    @Accessor
    @Mutable
    void setSoundGroup(BlockSoundGroup group);

    @Accessor
    void setDropTableId(Identifier dropTableId);

}
