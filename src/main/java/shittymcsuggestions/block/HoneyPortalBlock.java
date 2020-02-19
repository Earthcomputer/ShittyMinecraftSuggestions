package shittymcsuggestions.block;

import net.minecraft.block.Blocks;

import java.util.Collections;

public class HoneyPortalBlock extends NetherPortalLikeBlock {

    public HoneyPortalBlock(Settings settings) {
        super(Blocks.HONEYCOMB_BLOCK, Collections.singleton(Blocks.FIRE), settings);
    }
}
