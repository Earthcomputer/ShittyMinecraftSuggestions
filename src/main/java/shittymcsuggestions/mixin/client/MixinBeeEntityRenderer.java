package shittymcsuggestions.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

/*
 * This mixin is weird because of generics. You would think you could just override the scale
 * method with a BeeEntity parameter, but the Java compiler creates a synthetic bridge method
 * to actually override the method, and Mixin doesn't copy synthetic methods, hence the method
 * doesn't actually get overridden. We cannot simply change BeeEntity to LivingEntity either,
 * even with removing @Override, because the compiler complains that our method conflicts
 * with a method in the superclass. However, we can override the method directly if we make
 * our superclass a raw type, rather than a generic type.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(BeeEntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinBeeEntityRenderer extends MobEntityRenderer {

    public MixinBeeEntityRenderer(EntityRenderDispatcher renderManager, EntityModel model, float f) {
        super(renderManager, model, f);
    }

    @Override
    protected void scale(LivingEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(4, 4, 4);
    }
}
