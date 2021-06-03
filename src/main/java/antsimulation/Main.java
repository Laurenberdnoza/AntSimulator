package antsimulation;

import antsimulation.controller.Controller;
import antsimulation.world.World;
import processing.core.PApplet;

public class Main extends PApplet {

    private static PApplet app;
    private static final World world = new World();

    private final Controller controller = new Controller(world);

    public static void main(String[] args) {
        PApplet.main("antsimulation.Main");
    }

    public void setup() {
        app = this;
        world.getSpawner().spawnHive(100);
    }

    public void settings() {
        size(world.getWidth(), world.getHeight());
    }

    public void draw() {
        world.update();
        world.display();
    }

    public void mouseDragged() {
       controller.handleMouseDrag();
    }

    public static PApplet getApp() {
        return app;
    }

    public static World getWorld() {
        return world;
    }
}
