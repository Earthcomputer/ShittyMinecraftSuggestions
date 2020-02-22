package shittymcsuggestions.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.ModSounds;

import java.util.HashMap;
import java.util.Map;

@Mixin(WallBlock.class)
public class MixinWallBlock extends Block {

    @Unique
    private static final Map<BlockSoundGroup, BlockSoundGroup> WALL_SOUND_GROUPS = new HashMap<>();

    public MixinWallBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstructor(CallbackInfo ci) {
        ((BlockAccessor) this).setSoundGroup(WALL_SOUND_GROUPS.computeIfAbsent(soundGroup, this::createWallSoundGroup));
    }

    @Unique
    private BlockSoundGroup createWallSoundGroup(BlockSoundGroup group) {
        return new BlockSoundGroup(
                group.volume,
                group.pitch,
                ModSounds.TRUMP_NO,
                group.getStepSound(),
                ModSounds.WALL,
                ((BlockSoundGroupAccessor) group).sms_getHitSound(),
                group.getFallSound()
        );
    }

}
