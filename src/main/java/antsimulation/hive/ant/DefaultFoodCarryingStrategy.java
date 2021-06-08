package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.world.grid.Node;
import org.mini2Dx.gdx.math.Vector2;

import java.util.Collections;
import java.util.List;

class DefaultFoodCarryingStrategy implements FoodCarryingStrategy {

    private static final float RANDOMNESS_COEFFICIENT = 0.15f;

    private final Ant ant;

    DefaultFoodCarryingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public Vector2 getDesiredDirection() {
        if (!ant.carryingFood()) throw new RuntimeException("Called carrying food strategy without ant carrying any food.");
        return getDirection();
    }

    private Vector2 getDirection() {
        List<Node> neighbours = (Main.getWorld().getGrid().getNodesInSquare(ant.getNode(), ant.getPheromoneSensingRadius()));
        Collections.shuffle(neighbours);

        Node attractiveNode = getAttractiveNode(neighbours);
        return attractiveNode.getLocation().sub(ant.getLocation());
    }

    private Node getAttractiveNode(List<Node> neighbours) {
        float maxStrength = 0;
        Node bestNode = neighbours.get(0);

        for (Node node : neighbours) {
            maxStrength = Math.max(maxStrength, node.getPheromoneStrength(Pheromone.Type.HOME));
            if (node.getPheromoneStrength(Pheromone.Type.FOOD) == maxStrength) bestNode = node;
            if (Main.getApp().random(1) <= RANDOMNESS_COEFFICIENT) break;
        }

        return bestNode;
    }
}
