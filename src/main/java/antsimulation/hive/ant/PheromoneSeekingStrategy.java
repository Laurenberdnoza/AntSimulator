package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.world.grid.Node;

import java.util.Collections;
import java.util.List;

abstract class PheromoneSeekingStrategy {

    private static final float RANDOMNESS_COEFFICIENT = 0.01f;

    protected static Node getMostAttractiveNodeByPheromoneType(Ant ant, int queryAreaWidth, Pheromone.Type pheromoneType) {
        List<Node> neighbours = (Main.getWorld().getGrid().getNodesInSquare(ant.getNode(), queryAreaWidth));
        Collections.shuffle(neighbours);

        float maxStrength = 0;
        Node bestNode = neighbours.get(0);

        for (Node node : neighbours) {
            maxStrength = Math.max(maxStrength, node.getPheromoneStrength(pheromoneType));
            if (node.getPheromoneStrength(pheromoneType) == maxStrength) bestNode = node;
            if (Main.getApp().random(0, 1) <= RANDOMNESS_COEFFICIENT) break;
        }

        return bestNode;
    }
}
