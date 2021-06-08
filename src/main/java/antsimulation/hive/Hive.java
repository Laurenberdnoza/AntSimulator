package antsimulation.hive;

import antsimulation.Main;
import antsimulation.hive.ant.Ant;
import antsimulation.world.Displayable;
import org.mini2Dx.gdx.math.Vector2;
import processing.core.PImage;

public class Hive implements Displayable {

    private static final PImage HIVE_TEXTURE = Main.getApp().loadImage("anthill.png");
    private static final float RADIUS = 16;

    private final Vector2 location;

    public Hive(Vector2 location, int initialAntAmount) {
        this.location = location;
        spawnAnts(initialAntAmount);
    }

    public void spawnAnts(int amount) {
        for (int i = 0; i < amount; i++) spawnAnt();
    }

    private void spawnAnt() {
        Ant newAnt = new Ant(location.cpy().add(new Vector2().setToRandomDirection().setLength(Main.getApp().random(0, RADIUS))));
        Main.getWorld().addEntity(newAnt);
    }

    @Override
    public void display() {
        Main.getApp().fill(40, 30, 5);
        Main.getApp().circle(location.x, location.y, 2 * RADIUS);
    }
}
