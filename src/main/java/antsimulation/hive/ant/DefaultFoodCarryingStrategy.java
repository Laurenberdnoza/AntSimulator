package antsimulation.hive.ant;

import antsimulation.Main;

class DefaultFoodCarryingStrategy implements FoodCarryingStrategy {

    private final Ant ant;

    DefaultFoodCarryingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public float getRotation() {
        if (!ant.carryingFood()) throw new RuntimeException("Called carrying food strategy without ant carrying any food.");
        System.out.println(Main.getWorld().getGrid().getNodesInSquare(ant.getNode(), 3).size());
        return 0;
    }
}
