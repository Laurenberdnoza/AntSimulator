package antsimulation.hive.ant;

import org.mini2Dx.gdx.math.Vector2;

class DefaultFoodCarryingStrategy implements FoodCarryingStrategy {

    private final Ant ant;

    DefaultFoodCarryingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public Vector2 getDesiredDirection() {
        if (!ant.carryingFood()) throw new RuntimeException("Called carrying food strategy without ant carrying any food.");
        return new Vector2(1, 1);
    }
}
