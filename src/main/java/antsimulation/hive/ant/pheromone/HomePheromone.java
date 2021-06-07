package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import org.mini2Dx.gdx.math.Vector2;

public class HomePheromone extends Pheromone {

    private static final float LIFE_TIME = 5f;

    public HomePheromone(Vector2 pos) {
        super(pos.cpy(), LIFE_TIME, Type.HOME);
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
