package shittymcsuggestions.client.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import shittymcsuggestions.entity.ChickenSheepEntity;

public class ChickenSheepEntityModel<T extends ChickenSheepEntity> extends QuadrupedEntityModel<T> {
    public ChickenSheepEntityModel() {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F, 0.0F);
        this.head.setPivot(0.0F, 6.0F, -8.0F);
        this.torso = new ModelPart(this, 28, 8);
        this.torso.addCuboid(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 0.0F);
        this.torso.setPivot(0.0F, 5.0F, 2.0F);
    }
}
