package shittymcsuggestions.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shittymcsuggestions.ModSounds;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity extends LockableContainerBlockEntity {

    @Shadow protected DefaultedList<ItemStack> inventory;

    @Shadow protected abstract boolean isBurning();

    protected MixinAbstractFurnaceBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (world != null && !world.isClient) {
            if (isBurning() && world.random.nextDouble() < 0.1) {
                SoundEvent sound = inventory.get(0).getItem() == Items.MUTTON ? ModSounds.LAMBSAUCE : SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE;
                world.playSound(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, sound, SoundCategory.BLOCKS, 1, 1);
            }
        }
    }

}
