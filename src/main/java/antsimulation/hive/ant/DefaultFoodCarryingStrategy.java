package antsimulation.hive.ant;

import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.world.grid.Node;
import org.mini2Dx.gdx.math.Vector2;

class DefaultFoodCarryingStrategy extends PheromoneSeekingStrategy implements FoodCarryingStrategy {

    private static final float DECISION_COOLDOWN = 0.25f;
    private static final int QUERY_AREA_WIDTH = 7;

    private final Ant ant;

    private float cooldown = 0f;

    DefaultFoodCarryingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public Vector2 getDesiredDirection(float dt) {
        if (!ant.carryingFood()) throw new RuntimeException("Called carrying food strategy without ant carrying any food.");
        return getDirection(dt);
    }

    private Vector2 getDirection(float dt) {
        cooldown = Math.max(0, cooldown - dt);

        if (cooldown == 0) {
            cooldown = DECISION_COOLDOWN;

            Node attractiveNode = getMostAttractiveNodeByPheromoneType(ant, QUERY_AREA_WIDTH, Pheromone.Type.HOME);
            return attractiveNode.getLocation().sub(ant.getLocation());
        }
        return ant.getDesiredDirection();
    }
}
