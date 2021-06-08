package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import antsimulation.world.grid.Node;

public class FoodPheromone extends Pheromone {

    public static final float MAX_LIFE_TIME = 10f;

    public FoodPheromone(Node parent) {
        super(parent, MAX_LIFE_TIME, Type.FOOD);
    }

    @Override
    protected void onUpdate() {
    }

    protected void onDisplay() {
        Main.getApp().fill(0, 240, 0, 255 * (lifeTime / MAX_LIFE_TIME));
        Main.getApp().circle(parent.getLocation().x, parent.getLocation().y, radius);
    }
}
