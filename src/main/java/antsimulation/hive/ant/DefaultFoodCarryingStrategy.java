package antsimulation.hive.ant;

import processing.core.PVector;

class DefaultFoodCarryingStrategy implements FoodCarryingStrategy {

    private final Ant ant;

    DefaultFoodCarryingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public PVector getDesiredDirection() {
        if (!ant.carryingFood()) throw new RuntimeException("Called carrying food strategy without ant carrying any food.");
        return new PVector(1, 1);
    }
}
