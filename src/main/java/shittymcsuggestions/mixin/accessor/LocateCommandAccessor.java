package shittymcsuggestions.mixin.accessor;

import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LocateCommand.class)
public interface LocateCommandAccessor {

    @Invoker
    static int callExecute(ServerCommandSource source, String name) {
        return 0;
    }

}
