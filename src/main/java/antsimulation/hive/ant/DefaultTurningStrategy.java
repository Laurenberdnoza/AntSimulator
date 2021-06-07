package antsimulation.hive.ant;

import org.mini2Dx.gdx.math.Vector2;

class DefaultTurningStrategy implements TurningStrategy {

    private Ant ant;
    private FoodCarryingStrategy foodCarryingStrategy;
    private WanderingStrategy wanderingStrategy;

    DefaultTurningStrategy(Ant ant, FoodCarryingStrategy foodCarryingStrategy, WanderingStrategy wanderingStrategy) {
        this.ant = ant;
        this.foodCarryingStrategy = foodCarryingStrategy;
        this.wanderingStrategy = wanderingStrategy;
    }

    @Override
    public Vector2 getDesiredDirection() {
        if (ant.carryingFood()) return foodCarryingStrategy.getDesiredDirection();
        else return wanderingStrategy.getDesiredDirection();
    }
}
