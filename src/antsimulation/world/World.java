package antsimulation.world;

import antsimulation.Main;
import antsimulation.world.food.Food;
import antsimulation.world.spawner.Spawner;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

public class World implements Updatable, Displayable {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private final Spawner spawner = new Spawner(this);

    private final Set<Displayable> displayables = new HashSet<>();
    private final Set<Updatable> updatables = new HashSet<>();

    private final Set<Food> food = new HashSet<>();

    public void update() {
        for (Updatable updatable : updatables) updatable.update();
    }

    public void display() {
        displayGround();
        for (Displayable displayable : displayables) displayable.display();
    }

    private void displayGround() {
        Main.getApp().background(80, 40, 15);  // Brown.
    }

    public boolean inBounds(PVector position) {
        return (
            position.x > 0
            && position.x < WIDTH
            && position.y > 0
            && position.y < HEIGHT
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

    public void addEntity(Object entity) {
        if (entity instanceof Displayable) displayables.add((Displayable) entity);
        if (entity instanceof Updatable) updatables.add((Updatable) entity);
        if (entity instanceof Food) food.add((Food) entity);
    }

    public Spawner getSpawner() {
        return spawner;
    }

    public Set<Food> getFood() {
        return food;
    }
}
