package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.world.grid.Node;

import java.util.List;

class DefaultFoodCarryingStrategy implements FoodCarryingStrategy {

    private final Ant ant;

    DefaultFoodCarryingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public float getRotation() {
        if (!ant.carryingFood()) throw new RuntimeException("Called carrying food strategy without ant carrying any food.");

        List<Node> surroundingNodes = Main.getWorld().getGrid().getNodesInSquare(ant.getNode(), ant.getPheromoneSensingRadius());
        return 0;
    }
}
