package shittymcsuggestions.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import shittymcsuggestions.block.ICustomPortal;

public class PortalCooldownHelper<T extends Block & ICustomPortal> {

    private final Entity entity;
    private final T portalBlock;

    private PortalCooldownHelper(Entity entity, T portalBlock) {
        this.entity = entity;
        this.portalBlock = portalBlock;
    }

    public static <T extends Block & ICustomPortal> PortalCooldownHelper getInstance(Entity entity, T portalBlock) {
        return ((IEntity) entity).sms_getPortalCooldownHelpers().computeIfAbsent(portalBlock, k -> new PortalCooldownHelper<>(entity, portalBlock));
    }

    private boolean inPortalWarmingUp;
    private int ticksInPortal;
    private int cooldownTicks;

    public void onEntityInPortal(BlockPos portalPos) {
        ((IEntity) entity).sms_setCustomPortalOverlay(portalBlock);
        if (cooldownTicks > 0) {
            cooldownTicks = portalBlock.getPortalCooldown(entity);
        } else {
            portalBlock.tickPortalWarmup(entity, portalPos);
            inPortalWarmingUp = true;
        }
    }

    public void tick() {
        if (entity.world.isClient)
            return;

        if (inPortalWarmingUp) {
            int maxPortalTime = portalBlock.getMaxPortalTime(entity);
            if (ticksInPortal++ >= portalBlock.getMaxPortalTime(entity)) {
                ticksInPortal = maxPortalTime;
                cooldownTicks = portalBlock.getPortalCooldown(entity);
                portalBlock.teleportEntity(entity);
            }
            inPortalWarmingUp = false;
        } else {
            if (ticksInPortal > 0)
                ticksInPortal -= 4;
            if (ticksInPortal < 0)
                ticksInPortal = 0;
        }
        if (cooldownTicks > 0)
            cooldownTicks--;
    }

    public static <T extends Block & ICustomPortal> PortalCooldownHelper<T> fromNbt(Entity entity, T portalBlock, CompoundTag nbt) {
        PortalCooldownHelper<T> helper = new PortalCooldownHelper<>(entity, portalBlock);
        helper.cooldownTicks = nbt.getInt("CooldownTicks");
        return helper;
    }

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("CooldownTicks", cooldownTicks);
        return nbt;
    }

    public boolean isInPortalWarmingUp() {
        return inPortalWarmingUp;
    }

    public void setInPortalWarmingUp(boolean inPortalWarmingUp) {
        this.inPortalWarmingUp = inPortalWarmingUp;
    }

    public T getPortalBlock() {
        return portalBlock;
    }

}
