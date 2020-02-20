package shittymcsuggestions.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import shittymcsuggestions.block.ICustomPortal;

public class PortalCooldownHelper {

    private final Entity entity;
    private final ICustomPortal portalBlock;

    private PortalCooldownHelper(Entity entity, ICustomPortal portalBlock) {
        this.entity = entity;
        this.portalBlock = portalBlock;
    }

    public static PortalCooldownHelper getInstance(Entity entity, ICustomPortal portalBlock) {
        return ((IEntity) entity).sms_getPortalCooldownHelpers().computeIfAbsent(portalBlock, k -> new PortalCooldownHelper(entity, portalBlock));
    }

    private boolean inPortal;
    private int ticksInPortal;
    private int cooldownTicks;

    public void onEntityInPortal() {
        if (cooldownTicks > 0) {
            cooldownTicks = portalBlock.getPortalCooldown(entity);
        } else {
            inPortal = true;
        }
    }

    public void tick() {
        if (inPortal) {
            int maxPortalTime = portalBlock.getMaxPortalTime(entity);
            if (ticksInPortal++ >= portalBlock.getMaxPortalTime(entity)) {
                ticksInPortal = maxPortalTime;
                cooldownTicks = portalBlock.getPortalCooldown(entity);
                portalBlock.teleportEntity(entity);
            }
            inPortal = false;
        } else {
            if (ticksInPortal > 0)
                ticksInPortal -= 4;
            if (ticksInPortal < 0)
                ticksInPortal = 0;
        }
        if (cooldownTicks > 0)
            cooldownTicks--;
    }

    public static PortalCooldownHelper fromNbt(Entity entity, ICustomPortal portalBlock, CompoundTag nbt) {
        PortalCooldownHelper helper = new PortalCooldownHelper(entity, portalBlock);
        helper.cooldownTicks = nbt.getInt("CooldownTicks");
        return helper;
    }

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("CooldownTicks", cooldownTicks);
        return nbt;
    }

}
