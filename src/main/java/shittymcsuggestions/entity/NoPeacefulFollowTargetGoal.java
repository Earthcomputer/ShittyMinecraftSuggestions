package shittymcsuggestions.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Difficulty;

public class NoPeacefulFollowTargetGoal<T extends LivingEntity> extends FollowTargetGoal<T> {

    public NoPeacefulFollowTargetGoal(MobEntity attacker, Class<T> attacked, boolean checkVisibility) {
        super(attacker, attacked, checkVisibility);
    }

    @Override
    public boolean canStart() {
        return mob.world.getDifficulty() != Difficulty.PEACEFUL && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return mob.world.getDifficulty() != Difficulty.PEACEFUL && super.shouldContinue();
    }
}
