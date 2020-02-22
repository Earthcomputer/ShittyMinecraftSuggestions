package shittymcsuggestions.worldgen;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import shittymcsuggestions.ShittyMinecraftSuggestions;

public class ModStructures {

    public static final StructurePieceType BEE_NEST_PIECE = BeeNestGenerator.Piece::new;

    private static void registerPiece(String name, StructurePieceType piece) {
        Registry.register(Registry.STRUCTURE_PIECE, new Identifier(ShittyMinecraftSuggestions.MODID, name), piece);
    }

    public static void register() {
        registerPiece("bee_nest", BEE_NEST_PIECE);
    }

}
