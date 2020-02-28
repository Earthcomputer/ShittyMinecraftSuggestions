package shittymcsuggestions.client.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import shittymcsuggestions.entity.ShrekEntity;

public class ShrekEntityModel extends EntityModel<ShrekEntity> {

    private final ModelPart bb_main;

    public ShrekEntityModel() {
        textureWidth = 128;
        textureHeight = 128;

        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 57).addCuboid(-9.0F, -22.0F, -1.0F, 6, 22, 6, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid(-10.0F, -44.0F, -5.0F, 20, 22, 13, 0.0F);
        bb_main.setTextureOffset(64, 33).addCuboid(-8.0F, -36.0F, -7.0F, 16, 12, 2, 0.0F);
        bb_main.setTextureOffset(0, 35).addCuboid(-6.0F, -55.0F, -4.0F, 12, 11, 11, 0.0F);
        bb_main.setTextureOffset(46, 46).addCuboid(3.0F, -22.0F, -1.0F, 6, 22, 6, 0.0F);
        bb_main.setTextureOffset(66, 0).addCuboid(12.0F, -43.0F, -1.0F, 5, 22, 5, 0.0F);
        bb_main.setTextureOffset(24, 59).addCuboid(-17.0F, -43.0F, -1.0F, 5, 22, 5, 0.0F);
        bb_main.setTextureOffset(58, 75).addCuboid(-12.0F, -43.0F, -1.0F, 2, 5, 5, 0.0F);
        bb_main.setTextureOffset(0, 5).addCuboid(-8.0F, -53.0F, 1.0F, 2, 3, 2, 0.0F);
        bb_main.setTextureOffset(0, 5).addCuboid(6.0F, -53.0F, 1.0F, 2, 3, 2, 0.0F);
        bb_main.setTextureOffset(70, 55).addCuboid(-12.0F, -56.0F, 0.0F, 4, 4, 4, 0.0F);
        bb_main.setTextureOffset(70, 47).addCuboid(8.0F, -56.0F, 0.0F, 4, 4, 4, 0.0F);
        bb_main.setTextureOffset(44, 74).addCuboid(10.0F, -43.0F, -1.0F, 2, 5, 5, 0.0F);
        bb_main.setTextureOffset(0, 3).addCuboid(-1.0F, -50.0F, -5.0F, 2, 2, 2, 0.0F);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(ShrekEntity entity, float breathingProgress, float armPitchScale, float armRollScale, float yaw, float pitch) {
        bb_main.yaw = yaw * (float)Math.PI / 180;
    }

}
