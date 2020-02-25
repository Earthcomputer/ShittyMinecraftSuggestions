package shittymcsuggestions.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.entity.ISheep;

@Mixin(SheepEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class ClientMixinSheepEntityRenderer {

    @Unique private static final Identifier ANGRY_SKIN = new Identifier(ShittyMinecraftSuggestions.MODID, "textures/entity/sheep_angry.png");

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void onGetTexture(SheepEntity sheep, CallbackInfoReturnable<Identifier> ci) {
        if (((ISheep) sheep).sms_isAngry())
            ci.setReturnValue(ANGRY_SKIN);
    }

}
