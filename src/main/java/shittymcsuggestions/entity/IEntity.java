package shittymcsuggestions.entity;

import shittymcsuggestions.block.ICustomPortal;

import java.util.Map;

public interface IEntity {

    Map<ICustomPortal, PortalCooldownHelper> sms_getPortalCooldownHelpers();

    boolean sms_isInHoney();

    void sms_setInHoney();

}
