package antsimulation.hive.ant;

import processing.core.PVector;

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
    public PVector getDesiredDirection() {
        if (ant.carryingFood()) return foodCarryingStrategy.getDesiredDirection();
        else return wanderingStrategy.getDesiredDirection();
    }
}
