package shittymcsuggestions;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static final SoundEvent AUEGH = registerSound("auegh");
    public static final SoundEvent AWMAN = registerSound("awman");
    public static final SoundEvent LAMBSAUCE = registerSound("lambsauce");
    public static final SoundEvent WALL = registerSound("wall");

    private static SoundEvent registerSound(String name) {
        Identifier id = new Identifier(ShittyMinecraftSuggestions.MODID, name);
        SoundEvent sound = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, sound);
        return sound;
    }

    public static void register() {
        // load class
    }

}