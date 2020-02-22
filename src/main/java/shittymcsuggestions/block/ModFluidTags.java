package shittymcsuggestions.block;

import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModFluidTags {

    public static final Tag<Fluid> HONEY = tag("honey");

    private static Tag<Fluid> tag(String name) {
        return new FluidTags.CachingTag(new Identifier(ShittyMinecraftSuggestions.MODID, name));
    }

}
