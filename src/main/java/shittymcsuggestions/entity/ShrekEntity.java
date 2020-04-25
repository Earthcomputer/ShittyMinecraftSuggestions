package shittymcsuggestions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.world.World;

public class ShrekEntity extends MobEntityWithAi {
    protected ShrekEntity(EntityType<? extends ShrekEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(7, new WanderAroundGoal(this, 1));
        goalSelector.add(8, new LookAroundGoal(this));
    }
}
