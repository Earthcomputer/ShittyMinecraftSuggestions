package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ExplodingArrowEntity extends ProjectileEntity {

    public ExplodingArrowEntity(LivingEntity owner, World world) {
        super(ModEntities.EXPLODING_ARROW, owner, world);
    }

    ExplodingArrowEntity(EntityType<ExplodingArrowEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        remove();
        explode();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            remove();
            explode();
        }
    }

    private void explode() {
        world.createExplosion(this, getX(), getY() + getHeight() / 2, getZ(), 5, Explosion.DestructionType.BREAK);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.ARROW);
    }
}
