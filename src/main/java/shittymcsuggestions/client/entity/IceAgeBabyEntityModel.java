package shittymcsuggestions.client.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import shittymcsuggestions.entity.IceAgeBabyEntity;
import shittymcsuggestions.entity.LoraxEntity;

public class IceAgeBabyEntityModel extends EntityModel<IceAgeBabyEntity> {
    private final ModelPart bb_main;

    public IceAgeBabyEntityModel() {
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 41).addCuboid( -6.0F, -3.0F, -2.0F, 4, 3, 4, 0.0F);
        bb_main.setTextureOffset(43, 1).addCuboid( 2.0F, -3.0F, -2.0F, 4, 3, 4, 0.0F );
        bb_main.setTextureOffset(0, 0).addCuboid( -5.0F, -13.0F, -5.0F, 10, 10, 9, 0.0F);
        bb_main.setTextureOffset(0, 19).addCuboid( -6.0F, -21.0F, -3.0F, 12, 8, 6, 0.0F);
        bb_main.setTextureOffset(28, 42).addCuboid( 5.0F, -12.0F, -2.0F, 3, 6, 3, 0.0F);
        bb_main.setTextureOffset(17, 41).addCuboid( -8.0F, -12.0F, -2.0F, 3, 6, 3, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid( -1.0F, -16.0F, -4.0F, 2, 1, 1, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid( 6.0F, -16.0F, -1.0F, 1, 2, 1, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid( -7.0F, -16.0F, -1.0F, 1, 2, 1, 0.0F);
    }

    @Override
    public void setAngles(IceAgeBabyEntity entity, float limbAngle, float limbDistance, float customAngle, float yaw, float headPitch) {
        bb_main.yaw = yaw * (float)Math.PI / 180;
        //bb_main.pitch = pitch * (float)Math.PI / 180;
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        bb_main.render(matrixStack, vertexConsumer, i, j, f, g, h, k);
    }
}