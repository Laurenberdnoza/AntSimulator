package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import org.mini2Dx.gdx.math.Vector2;

public class FoodPheromone extends Pheromone {

    public static final float LIFE_TIME = 10f;

    public FoodPheromone(Vector2 pos) {
        super(pos.cpy(), LIFE_TIME, Type.FOOD);
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    public void display() {
        Main.getApp().fill(20, 50, 20, lifeTime / LIFE_TIME);
        Main.getApp().circle(pos.x, pos.y, radius);
    }
}
