package shittymcsuggestions.mixin.entity;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.entity.IBee;

@Mixin(BeeEntity.class)
public abstract class MixinBeeEntity extends AnimalEntity implements IBee {

    @Unique private static final TrackedData<Byte> SIZE = DataTracker.registerData(BeeEntity.class, TrackedDataHandlerRegistry.BYTE);
    @Unique private boolean setSize;

    protected MixinBeeEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void onInitDataTracker(CallbackInfo ci) {
        dataTracker.startTracking(SIZE, (byte) 1);
    }

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    private void onWriteTag(CompoundTag tag, CallbackInfo ci) {
        if (setSize)
            tag.putByte(ShittyMinecraftSuggestions.MODID + ":size", (byte) sms_getSize());
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    private void onReadTag(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(ShittyMinecraftSuggestions.MODID + ":size", NbtType.BYTE)) {
            dataTracker.set(SIZE, tag.getByte(ShittyMinecraftSuggestions.MODID + ":size"));
            setSize = true;
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (!setSize && !world.isClient) {
            dataTracker.set(SIZE, (byte) (1 + random.nextInt(4)));
            setSize = true;
        }
    }

    @Override
    public int sms_getSize() {
        return dataTracker.get(SIZE);
    }
}
