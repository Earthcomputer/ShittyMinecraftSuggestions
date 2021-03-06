package shittymcsuggestions;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.mixin.accessor.BlockSoundGroupAccessor;

public class ModSounds {

    public static final SoundEvent AUEGH = registerSound("auegh");
    public static final SoundEvent AWMAN = registerSound("awman");
    public static final SoundEvent AWRAPE = registerSound("awrape");
    public static final SoundEvent LAMBSAUCE = registerSound("lambsauce");
    public static final SoundEvent WALL = registerSound("wall");
    public static final SoundEvent TRUMP_NO = registerSound("trump_no");
    public static final SoundEvent CHINA = registerSound("china");
    public static final SoundEvent WATERMELON_SCREAM = registerSound("watermelon_scream");
    public static final SoundEvent BEANOS_BLOCK = registerSound("beanos_block");
    public static final SoundEvent BEANOS = registerSound("beanos");
    public static final SoundEvent WHOOSH = registerSound("whoosh");
    public static final SoundEvent OWO = registerSound("owo");
    public static final SoundEvent CAVEBEE = registerSound("cavebee");
    public static final SoundEvent FUCKING_DONKEY = registerSound("fuckingdonkey");
    public static final SoundEvent SHEEP_HIT = registerSound("sheephit");
    public static final SoundEvent DEATH_BY_FALL = registerSound("deathbyfall");
    public static final SoundEvent YEET = registerSound("yeet");
    public static final SoundEvent DISPENSER = registerSound("dispenser");

    public static final SoundEvent ENTITY_LORAX_AMBIENT = registerSound("entity.shittymcsuggestions.lorax.ambient");
    public static final SoundEvent ENTITY_LORAX_HURT = registerSound("entity.shittymcsuggestions.lorax.hurt");
    public static final SoundEvent ENTITY_LORAX_DEATH = registerSound("entity.shittymcsuggestions.lorax.death");
    public static final SoundEvent ENTITY_LORAX_STEP = registerSound("entity.shittymcsuggestions.lorax.step");

    public static final BlockSoundGroup BEANOS_BLOCK_SOUND = new BlockSoundGroup(1, 1,
            ((BlockSoundGroupAccessor) BlockSoundGroup.STONE).sms_getBreakSound(),
            BEANOS_BLOCK,
            BlockSoundGroup.STONE.getPlaceSound(),
            ((BlockSoundGroupAccessor) BlockSoundGroup.STONE).sms_getHitSound(),
            BEANOS_BLOCK);

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
