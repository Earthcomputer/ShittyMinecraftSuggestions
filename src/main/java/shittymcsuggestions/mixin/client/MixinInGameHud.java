package shittymcsuggestions.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import shittymcsuggestions.entity.IEntity;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class MixinInGameHud {

    @Shadow @Final private MinecraftClient client;

    @ModifyVariable(method = "renderPortalOverlay", at = @At(value = "STORE", ordinal = 0))
    private Sprite modifyPortalSprite(Sprite oldSprite) {
        assert client.player != null;
        Block customPortalBlock = ((IEntity) client.player).sms_getCustomPortalOverlay();
        if (customPortalBlock != null) {
            return client.getBlockRenderManager().getModels().getSprite(customPortalBlock.getDefaultState());
        }
        return oldSprite;
    }

}
