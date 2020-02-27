package shittymcsuggestions.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Accessor
    void setLastNetherPortalPosition(BlockPos pos);

    @Accessor
    void setLastNetherPortalDirectionVector(Vec3d vec);

    @Accessor
    void setLastNetherPortalDirection(Direction dir);

}
