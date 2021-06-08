package antsimulation.hive.ant;

import org.mini2Dx.gdx.math.Vector2;

class DefaultTurningStrategy implements TurningStrategy {

    private static final float DECISION_COOLDOWN = 0.5f;

    private final Ant ant;
    private final FoodCarryingStrategy foodCarryingStrategy;
    private final WanderingStrategy wanderingStrategy;
    private float cooldown = DECISION_COOLDOWN;

    DefaultTurningStrategy(Ant ant, FoodCarryingStrategy foodCarryingStrategy, WanderingStrategy wanderingStrategy) {
        this.ant = ant;
        this.foodCarryingStrategy = foodCarryingStrategy;
        this.wanderingStrategy = wanderingStrategy;
    }

    @Override
    public Vector2 getDesiredDirection(float dt) {
        cooldown = Math.max(0, cooldown - dt);

        if (cooldown == 0) {
            if (ant.carryingFood()) return foodCarryingStrategy.getDesiredDirection();
            else return wanderingStrategy.getDesiredDirection();
        } else {
            return ant.getDesiredDirection();
        }
    }
}
