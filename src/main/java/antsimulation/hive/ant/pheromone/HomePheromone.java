package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import antsimulation.world.grid.Node;
import processing.core.PConstants;

public class HomePheromone extends Pheromone {

    private static final float MAX_LIFE_TIME = 20f;

    public HomePheromone(Node parent) {
        super(parent, MAX_LIFE_TIME, Type.HOME);
    }

    protected void onDisplay() {
        Main.getApp().fill(40, 0, 120, 255 * (lifeTime / MAX_LIFE_TIME));
        Main.getApp().rectMode(PConstants.CENTER);
        Main.getApp().square(parent.getLocation().x, parent.getLocation().y, radius);
    }

    @Override
    protected void onUpdate() {
    }
}
