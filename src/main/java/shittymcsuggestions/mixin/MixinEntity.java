package shittymcsuggestions.mixin;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.block.ICustomPortal;
import shittymcsuggestions.entity.IEntity;
import shittymcsuggestions.entity.PortalCooldownHelper;

import java.util.HashMap;
import java.util.Map;

@Mixin(Entity.class)
public class MixinEntity implements IEntity {

    @Shadow public float fallDistance;

    @Unique private Map<ICustomPortal, PortalCooldownHelper> cooldownHelpers = new HashMap<>();
    @Unique private boolean inHoney;

    @Override
    public Map<ICustomPortal, PortalCooldownHelper> sms_getPortalCooldownHelpers() {
        return cooldownHelpers;
    }

    @Inject(method = "tickNetherPortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tickNetherPortalCooldown()V"))
    private void onTickNetherPortal(CallbackInfo ci) {
        cooldownHelpers.values().forEach(PortalCooldownHelper::tick);
    }

    @Inject(method = "fromTag", at = @At("TAIL"))
    private void onFromTag(CompoundTag tag, CallbackInfo ci) {
        cooldownHelpers.clear();
        if (tag.contains(ShittyMinecraftSuggestions.MODID + ":portalCooldowns", NbtType.COMPOUND)) {
            CompoundTag portalCooldowns = tag.getCompound(ShittyMinecraftSuggestions.MODID + ":portalCooldowns");
            for (String key : portalCooldowns.getKeys()) {
                Block block = Registry.BLOCK.get(Identifier.tryParse(key));
                if (block instanceof ICustomPortal) {
                    cooldownHelpers.put((ICustomPortal) block,
                            PortalCooldownHelper.fromNbt((Entity) (Object) this, (ICustomPortal) block, portalCooldowns.getCompound(key)));
                }
            }
        }
    }

    @Inject(method = "toTag", at = @At("TAIL"))
    private void onToTag(CompoundTag tag, CallbackInfoReturnable<CompoundTag> ci) {
        CompoundTag portalCooldowns = new CompoundTag();
        tag.put(ShittyMinecraftSuggestions.MODID + ":portalCooldowns", portalCooldowns);
        cooldownHelpers.forEach((block, portalHelper) -> portalCooldowns.put(Registry.BLOCK.getId((Block) block).toString(), portalHelper.toNbt()));
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isInLava()Z"))
    private void onTickFluid(CallbackInfo ci) {
        if (inHoney) {
            fallDistance = 0;
        }
    }

    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;checkBlockCollision()V"))
    private void preCheckBlockCollision(CallbackInfo ci) {
        inHoney = false;
    }

    @Override
    public boolean sms_isInHoney() {
        return inHoney;
    }

    @Override
    public void sms_setInHoney() {
        inHoney = true;
    }
}
