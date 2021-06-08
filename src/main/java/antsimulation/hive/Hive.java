package antsimulation.hive;

import antsimulation.Main;
import antsimulation.hive.ant.Ant;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.objects.food.FoodChunk;
import org.mini2Dx.gdx.math.Vector2;

public class Hive implements Displayable, Locatable {

    private static final float RADIUS = 16;
    private static final float RADIUS_SQUARED = RADIUS * RADIUS;

    private final Vector2 location;

    public Hive(Vector2 location, int initialAntAmount) {
        this.location = location;
        spawnAnts(initialAntAmount);
    }

    public void spawnAnts(int amount) {
        for (int i = 0; i < amount; i++) spawnAnt();
    }

    private void spawnAnt() {
        Vector2 spawnLocation = location.cpy().add(new Vector2().setToRandomDirection().setLength(Main.getApp().random(0, RADIUS)));
        Ant newAnt = new Ant(spawnLocation, this);

        Main.getWorld().addEntity(newAnt);
    }

    @Override
    public void display() {
        Main.getApp().strokeWeight(2);
        Main.getApp().stroke(35, 25, 3);
        Main.getApp().fill(40, 30, 5);
        Main.getApp().circle(location.x, location.y, 2 * RADIUS);
    }

    public float getRadiusSquared() {
        return RADIUS_SQUARED;
    }

    @Override
    public Vector2 getLocation() {
        return location;
    }

    public void receiveFoodChunk(FoodChunk foodChunk) {
        // No op.
    }
}
