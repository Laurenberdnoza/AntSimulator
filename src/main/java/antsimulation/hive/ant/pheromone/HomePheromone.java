package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import processing.core.PVector;

public class HomePheromone extends Pheromone {

    private static final float LIFE_TIME = 8f;

    public HomePheromone(PVector pos) {
        super(pos.copy(), LIFE_TIME, Type.HOME);
    }

    @Override
    public void display() {
        Main.getApp().fill(40, 0, 120, 255 * lifeTime / LIFE_TIME);
        Main.getApp().circle(pos.x, pos.y, radius);
    }

    @Override
    protected void onUpdate() {
    }
}
