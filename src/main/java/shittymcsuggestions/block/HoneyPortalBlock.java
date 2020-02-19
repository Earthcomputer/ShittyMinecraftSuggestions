package shittymcsuggestions.block;

import net.minecraft.block.Blocks;

import java.util.Collections;

public class HoneyPortalBlock extends NetherPortalLikeBlock {

    public HoneyPortalBlock(Settings settings) {
        super(ModBlocks.COMPACTED_HONEYCOMB_BLOCK, Collections.singleton(Blocks.FIRE), settings);
    }
}
