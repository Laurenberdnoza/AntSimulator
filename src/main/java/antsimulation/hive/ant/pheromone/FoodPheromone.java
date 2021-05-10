package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import processing.core.PVector;

public class FoodPheromone extends Pheromone {

    public static final float LIFE_TIME = 10f;

    public FoodPheromone(PVector pos) {
        super(pos.copy(), LIFE_TIME);
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    public void display() {
        Main.getApp().fill(20, 120, 30);
        Main.getApp().circle(pos.x, pos.y, radius);
    }
}
