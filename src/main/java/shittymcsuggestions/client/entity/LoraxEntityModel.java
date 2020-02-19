package shittymcsuggestions.client.entity;

//Made with Blockbench
//Paste this code into your mod.

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import shittymcsuggestions.entity.LoraxEntity;

public class LoraxEntityModel extends EntityModel<LoraxEntity> {
    private final ModelPart bb_main;

    public LoraxEntityModel() {
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new ModelPart(this, 0, 0);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid(-4.0F, -15.0F, -3.0F, 8, 10, 7, 0.0F);
        bb_main.setTextureOffset(0, 17).addCuboid(-3.0F, -21.0F, -2.0F, 6, 6, 5, 0.0F);
        bb_main.setTextureOffset(30, 0).addCuboid(-3.0F, -14.0F, -4.0F, 6, 8, 1, 0.0F);
        bb_main.setTextureOffset(12, 30).addCuboid(-3.0F, -14.0F, 4.0F, 6, 8, 1, 0.0F);
        bb_main.setTextureOffset(28, 28).addCuboid(4.0F, -14.0F, -2.0F, 1, 8, 5, 0.0F);
        bb_main.setTextureOffset(0, 28).addCuboid(-5.0F, -14.0F, -2.0F, 1, 8, 5, 0.0F);
        bb_main.setTextureOffset(23, 0).addCuboid(2.0F, -5.0F, 0.0F, 1, 5, 1, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid(-3.0F, -5.0F, 0.0F, 1, 5, 1, 0.0F);
        bb_main.setTextureOffset(17, 19).addCuboid(-5.0F, -17.0F, -3.0F, 10, 1, 1, 0.0F);
        bb_main.setTextureOffset(26, 26).addCuboid(-6.0F, -16.0F, -3.0F, 2, 1, 1, 0.0F);
        bb_main.setTextureOffset(17, 17).addCuboid(4.0F, -16.0F, -3.0F, 2, 1, 1, 0.0F);
        bb_main.setTextureOffset(23, 10).addCuboid(5.0F, -11.0F, -5.0F, 2, 2, 7, 0.0F);
        bb_main.setTextureOffset(15, 21).addCuboid(-7.0F, -11.0F, -5.0F, 2, 2, 7, 0.0F);
        bb_main.setTextureOffset(31, 22).addCuboid(2.0F, -1.0F, -3.0F, 1, 1, 3, 0.0F);
        bb_main.setTextureOffset(26, 21).addCuboid(-3.0F, -1.0F, -3.0F, 1, 1, 3, 0.0F);
    }

    @Override
    public void setAngles(LoraxEntity entity, float breathingProgress, float armPitchScale, float armRollScale, float yaw, float pitch) {
        bb_main.yaw = yaw * (float)Math.PI / 180;
        //bb_main.pitch = pitch * (float)Math.PI / 180;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        bb_main.render(matrixStack, vertexConsumer, i, j, f, g, h, k);
    }
}
