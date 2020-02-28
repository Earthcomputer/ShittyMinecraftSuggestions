package shittymcsuggestions.entity;

import net.minecraft.block.Block;
import shittymcsuggestions.block.ICustomPortal;

import java.util.Map;

public interface IEntity {

    Map<ICustomPortal, PortalCooldownHelper<?>> sms_getPortalCooldownHelpers();

    Block sms_getCustomPortalOverlay();

    void sms_setCustomPortalOverlay(Block portalBlock);

    boolean sms_isInHoney();

    void sms_setInHoney();

}
