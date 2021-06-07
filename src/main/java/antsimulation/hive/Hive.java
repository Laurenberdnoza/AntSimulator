package antsimulation.hive;

import antsimulation.Main;
import antsimulation.hive.ant.Ant;
import antsimulation.world.Displayable;
import org.mini2Dx.gdx.math.Vector2;

public class Hive implements Displayable {

    private static final float WIDTH = 32f;

    private final Vector2 location;

    public Hive(Vector2 location, int initialAntAmount) {
        this.location = location;
        spawnAnts(initialAntAmount);
    }

    public void spawnAnts(int amount) {
        for (int i = 0; i < amount; i++) spawnAnt();
    }

    private void spawnAnt() {
        Ant newAnt = new Ant(location.cpy().add(new Vector2().setToRandomDirection().setLength(Main.getApp().random(0, WIDTH))));
        Main.getWorld().addEntity(newAnt);
    }

    @Override
    public void display() {
        Main.getApp().fill(20, 10, 2);
        Main.getApp().circle(location.x, location.y, WIDTH);
    }
}
