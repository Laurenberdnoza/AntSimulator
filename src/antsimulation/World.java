package antsimulation;

import antsimulation.ant.Ant;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class World {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private final List<Ant> ants = new ArrayList<>();

    public void spawnAnts(int amount) {
        float spawnX = Main.getApp().random(0, WIDTH);
        float spawnY = Main.getApp().random(0, HEIGHT);
        for (int i = 0; i < amount; i++) ants.add(new Ant(spawnX, spawnY));
    }

    public void update() {
        updateAnts();
    }

    private void updateAnts() {
        for (Ant ant : ants) ant.update();
    }

    public void draw() {
        displayGround();
        displayAnts();
    }

    private void displayGround() {
        Main.getApp().background(80, 40, 15);
    }

    private void displayAnts() {
        for (Ant ant : ants) ant.display();
    }

    public boolean inBounds(PVector position) {
        return (
            position.x > 0 && position.x < WIDTH
            && position.y > 0 && position.y < HEIGHT
        );
    }

    public int getWidth() {
       return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
