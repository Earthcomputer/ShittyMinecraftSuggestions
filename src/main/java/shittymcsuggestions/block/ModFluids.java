package shittymcsuggestions.block;

import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModFluids {

    public static final BaseFluid HONEY = new HoneyFluid.Still();
    public static final BaseFluid FLOWING_HONEY = new HoneyFluid.Flowing();

    private static void registerFluid(String name, Fluid fluid) {
        Registry.register(Registry.FLUID, new Identifier(ShittyMinecraftSuggestions.MODID, name), fluid);
    }

    public static void register() {
        registerFluid("honey", HONEY);
        registerFluid("flowing_honey", FLOWING_HONEY);
    }

}
