package antsimulation.hive.ant;

import antsimulation.world.grid.Node;

class DefaultFoodCarryingStrategy implements FoodCarryingStrategy {

    private final Ant ant;

    DefaultFoodCarryingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public float getRotation() {
        if (!ant.carryingFood()) throw new RuntimeException("Called carrying food strategy without ant carrying any food.");
        return 0;
    }
}
