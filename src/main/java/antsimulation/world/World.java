package antsimulation.world;

import antsimulation.Main;
import antsimulation.hive.Hive;
import antsimulation.hive.ant.Ant;
import antsimulation.world.grid.Grid;
import antsimulation.world.spawner.Spawner;
import processing.core.PVector;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class World implements Updatable, Displayable {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int GRID_DOWNSCALE_FACTOR = 4;

    private final Grid grid = new Grid(this, WIDTH / GRID_DOWNSCALE_FACTOR, HEIGHT / GRID_DOWNSCALE_FACTOR);
    private final Spawner spawner = new Spawner(this);

    private final Set<Ant> ants = ConcurrentHashMap.newKeySet();
    private final Set<Hive> hives = ConcurrentHashMap.newKeySet();

    public void update() {
        for (Ant ant : ants) ant.update();
        grid.update();
    }

    public void display() {
        displayGround();
        for (Ant ant : ants) ant.display();
        for (Hive hive : hives) hive.display();
        grid.display();
    }

    private void displayGround() {
        Main.getApp().background(80, 40, 15);  // Brown.
    }

    public boolean inBounds(PVector position) {
        return (
            position.x >= 0
            && position.x <= WIDTH
            && position.y >= 0
            && position.y <= HEIGHT
        );
    }

    public PVector getRandomLocation() {
        float x = Main.getApp().random(0, WIDTH);
        float y = Main.getApp().random(0, HEIGHT);
        return new PVector(x, y);
    }

    public PVector getRandomLocationAwayFromEdgeBy(float distance) {
        float x = Main.getApp().random(distance, WIDTH - distance);
        float y = Main.getApp().random(distance, HEIGHT - distance);
        return new PVector(x, y);
    }

    public int getWidth() {
       return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public Grid getGrid() {
        return grid;
    }

    public void addEntity(Object entity) {
        if (entity instanceof Ant) ants.add((Ant) entity);
        if (entity instanceof Hive) hives.add((Hive) entity);
        if (entity instanceof GridEntity) grid.add((GridEntity) entity);
    }

    public Spawner getSpawner() {
        return spawner;
    }
}
