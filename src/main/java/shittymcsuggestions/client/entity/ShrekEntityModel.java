package shittymcsuggestions.client.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import shittymcsuggestions.entity.ShrekEntity;

public class ShrekEntityModel extends BipedEntityModel<ShrekEntity> {

    public ShrekEntityModel() {
        super(0, -60, 128, 128);

        leftLeg = new ModelPart(this);
        // left leg
        leftLeg.setTextureOffset(0, 57).addCuboid(-9.0F, -22.0F, -1.0F, 6, 22, 6, 0.0F);

        torso = new ModelPart(this);
        // main torso
        torso.setTextureOffset(0, 0).addCuboid(-10.0F, -44.0F, -5.0F, 20, 22, 13, 0.0F);
        // belly
        torso.setTextureOffset(64, 33).addCuboid(-8.0F, -36.0F, -7.0F, 16, 12, 2, 0.0F);

        head = new ModelPart(this);
        head.setPivot(0, -60, 0);
        // main head
        head.setTextureOffset(0, 35).addCuboid(-6.0F, -55.0F, -4.0F, 12, 11, 11, 0.0F);
        // nose
        head.setTextureOffset(0, 3).addCuboid(-1.0F, -50.0F, -5.0F, 2, 2, 2, 0.0F);

        rightLeg = new ModelPart(this);
        // right leg
        rightLeg.setTextureOffset(46, 46).addCuboid(3.0F, -22.0F, -1.0F, 6, 22, 6, 0.0F);

        rightArm = new ModelPart(this);
        // right arm
        rightArm.setTextureOffset(66, 0).addCuboid(12.0F, -43.0F, -1.0F, 5, 22, 5, 0.0F);
        // right armpit
        rightArm.setTextureOffset(44, 74).addCuboid(10.0F, -43.0F, -1.0F, 2, 5, 5, 0.0F);

        leftArm = new ModelPart(this);
        // left arm
        leftArm.setTextureOffset(24, 59).addCuboid(-17.0F, -43.0F, -1.0F, 5, 22, 5, 0.0F);
        // left armpit
        leftArm.setTextureOffset(58, 75).addCuboid(-12.0F, -43.0F, -1.0F, 2, 5, 5, 0.0F);

        helmet = new ModelPart(this);
        // left inner ear
        helmet.setTextureOffset(0, 5).addCuboid(-8.0F, -53.0F, 1.0F, 2, 3, 2, 0.0F);
        // right inner ear
        helmet.setTextureOffset(0, 5).addCuboid(6.0F, -53.0F, 1.0F, 2, 3, 2, 0.0F);
        // left outer ear
        helmet.setTextureOffset(70, 55).addCuboid(-12.0F, -56.0F, 0.0F, 4, 4, 4, 0.0F);
        // right outer ear
        helmet.setTextureOffset(70, 47).addCuboid(8.0F, -56.0F, 0.0F, 4, 4, 4, 0.0F);
    }

    @Override
    public void setAngles(ShrekEntity entity, float breathingProgress, float armPitchScale, float armRollScale, float yaw, float pitch) {
        super.setAngles(entity, breathingProgress, armPitchScale, armRollScale, yaw, pitch);
    }

}
