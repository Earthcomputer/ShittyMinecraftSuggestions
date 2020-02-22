package shittymcsuggestions.worldgen;

import net.fabricmc.fabric.api.registry.CommandRegistry;
import shittymcsuggestions.mixin.LocateCommandAccessor;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.command.CommandManager.*;

public class LocateCommandEnhancement {

    private static final List<String> CUSTOM_STRUCTURES = new ArrayList<>();

    public static void register(String structure) {
        CUSTOM_STRUCTURES.add(structure);
    }

    public static void registerCommand() {
        CommandRegistry.INSTANCE.register(false, dispatcher -> {
                for (String structure : CUSTOM_STRUCTURES) {
                dispatcher.register(literal("locate")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(literal(structure)
                        .executes(ctx -> LocateCommandAccessor.callExecute(ctx.getSource(), structure))));
            }
        });
    }

}
