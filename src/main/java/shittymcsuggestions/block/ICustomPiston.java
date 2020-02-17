package shittymcsuggestions.block;

import net.minecraft.block.PistonHeadBlock;

public interface ICustomPiston {

    default int getPushLimit() {
        return 12;
    }

    PistonHeadBlock getCustomPistonHeadBlock();

}
